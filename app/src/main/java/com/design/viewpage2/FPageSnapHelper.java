package com.design.viewpage2;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.SnapHelper;

/**
 * User: maodayu
 * Date: 2019/10/12
 * Time: 10:32
 */
public class FPageSnapHelper extends SnapHelper {

    private static final float             INVALID_DISTANCE = 1f;
    RecyclerView mRecyclerView;
    @Nullable
    private              OrientationHelper mVerticalHelper;
    @Nullable
    private              OrientationHelper mHorizontalHelper;

    public FPageSnapHelper(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Nullable
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull LayoutManager layoutManager, @NonNull View targetView) {
        Log.i("MDY", "calculateDistanceToFinalSnap");
        int[] distance = new int[2];
        //根据是否可以水平滚动，计算出targetView需要滚动的x距离
        if (layoutManager.canScrollHorizontally()) {
            distance[0] = distanceStart(layoutManager, targetView, getHorizontalHelper(layoutManager));
        } else {
            distance[0] = 0;
        }
        //根据是否可以数值滚动，计算出targetView需要滚动的y距离
        if (layoutManager.canScrollVertically()) {
            distance[1] = distanceStart(layoutManager, targetView, getVerticalHelper(layoutManager));
        } else {
            distance[1] = 0;
        }

        return distance;
    }

    @Nullable
    @Override
    public View findSnapView(LayoutManager layoutManager) {
        if (layoutManager.canScrollHorizontally()) {
            return findStartView(layoutManager, getHorizontalHelper(layoutManager));
        } else {
            return findStartView(layoutManager, getVerticalHelper(layoutManager));
        }
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX,
                                      int velocityY) {
        Log.i("MDY", "findTargetSnapPosition");
        //获取滑动需要滚动的Pos,最终计算Pos位置交给RecycleView滚动
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }

        final View currentView = findSnapView(layoutManager);
        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = layoutManager.getPosition(currentView);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            // cannot get a vector for the given position.
            return RecyclerView.NO_POSITION;
        }

        int threshold = 0;


        int vDeltaJump, hDeltaJump;
        if (layoutManager.canScrollHorizontally()) {
            threshold = layoutManager.getWidth() / getHorizontalHelper(layoutManager).getDecoratedMeasurement(findSnapView(layoutManager));

            hDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getHorizontalHelper(layoutManager), velocityX, 0);
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }
        if (layoutManager.canScrollVertically()) {
            threshold = layoutManager.getHeight() / getHorizontalHelper(layoutManager).getDecoratedMeasurement(findSnapView(layoutManager));
            vDeltaJump = estimateNextPositionDiffForFling(layoutManager,
                    getVerticalHelper(layoutManager), 0, velocityY);
            if (vectorForEnd.y < 0) {
                vDeltaJump = -vDeltaJump;
            }
        } else {
            vDeltaJump = 0;
        }
        if(threshold>2){
            threshold=threshold-2;
        }
        if (hDeltaJump > 0 && hDeltaJump > threshold) {
            hDeltaJump = threshold;
        }else if(hDeltaJump < 0 && Math.abs(hDeltaJump) > threshold){
            hDeltaJump=-threshold;
        }

        if (vDeltaJump > 0 && vDeltaJump > threshold) {
            vDeltaJump = threshold;
        }else if(vDeltaJump < 0 && Math.abs(vDeltaJump) > threshold){
            vDeltaJump=-threshold;
        }

        int deltaJump = layoutManager.canScrollVertically() ? vDeltaJump : hDeltaJump;
        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION;
        }

        int targetPos = currentPosition + deltaJump;
        Log.i("MDY", "currentPosition="+currentPosition+"  targetPos="+targetPos);
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1;
        }
        return targetPos;
    }


    @Nullable
    @Override
    protected LinearSmoothScroller createSnapScroller(LayoutManager layoutManager) {
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }

        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                if (mRecyclerView == null) {
                    // The associated RecyclerView has been removed so there is no action to take.
                    return;
                }
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(),
                        targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 20f / displayMetrics.densityDpi;
            }
        };
    }

    private int distanceStart(@NonNull LayoutManager layoutManager, @NonNull View targetView, OrientationHelper orientationHelper) {
        //这里的targetView 就是我们findSnapView获取的View
        int start = orientationHelper.getStartAfterPadding();
        int viewDistance = orientationHelper.getDecoratedStart(targetView);
        //targetView的start-滚动视图的start。
        return viewDistance - start;
    }

    private View findStartView(LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int count = layoutManager.getChildCount();
        if (count == 0)
            return null;
        View firstView = null;
        int minDistance = Integer.MAX_VALUE;
        //若当前展示的最后一个可见View是最后一个Pos，则不做操作
        View lastChild = layoutManager.getChildAt(count - 1);
        int lastPos = layoutManager.getPosition(lastChild);
        if (lastPos == layoutManager.getItemCount() - 1)
            return null;
        //查找出距离Start最近的View
        for (int i = 0; i < count; i++) {
            View child = layoutManager.getChildAt(i);
            int distance = orientationHelper.getDecoratedStart(child);
            if (distance < minDistance) {
                minDistance = distance;
                firstView = child;
            }
        }
        //根据当前View的中心位置，判断是否移动到下一个View
        int postion = layoutManager.getPosition(firstView);
        if (orientationHelper.getDecoratedEnd(firstView) > orientationHelper.getDecoratedMeasurement(firstView) / 2) {
            return firstView;
        } else {
            return layoutManager.findViewByPosition(postion + 1);
        }
    }

    private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager layoutManager,
                                                 OrientationHelper helper, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = computeDistancePerChild(layoutManager, helper);
        if (distancePerChild <= 0) {
            return 0;
        }
        int distance =
                Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
        return (int) Math.round(distance / distancePerChild);
    }

    private float computeDistancePerChild(RecyclerView.LayoutManager layoutManager,
                                          OrientationHelper helper) {
        View minPosView = null;
        View maxPosView = null;
        int minPos = Integer.MAX_VALUE;
        int maxPos = Integer.MIN_VALUE;
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return INVALID_DISTANCE;
        }

        for (int i = 0; i < childCount; i++) {
            View child = layoutManager.getChildAt(i);
            final int pos = layoutManager.getPosition(child);
            if (pos == RecyclerView.NO_POSITION) {
                continue;
            }
            if (pos < minPos) {
                minPos = pos;
                minPosView = child;
            }
            if (pos > maxPos) {
                maxPos = pos;
                maxPosView = child;
            }
        }
        if (minPosView == null || maxPosView == null) {
            return INVALID_DISTANCE;
        }
        int start = Math.min(helper.getDecoratedStart(minPosView),
                helper.getDecoratedStart(maxPosView));
        int end = Math.max(helper.getDecoratedEnd(minPosView),
                helper.getDecoratedEnd(maxPosView));
        int distance = end - start;
        if (distance == 0) {
            return INVALID_DISTANCE;
        }
        return 1f * distance / ((maxPos - minPos) + 1);
    }

    @NonNull
    private OrientationHelper getVerticalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null || mVerticalHelper.getLayoutManager() != layoutManager) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    @NonNull
    private OrientationHelper getHorizontalHelper(
            @NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null || mHorizontalHelper.getLayoutManager() != layoutManager) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}
