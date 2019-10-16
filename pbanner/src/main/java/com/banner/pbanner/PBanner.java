package com.banner.pbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.viewpager2.widget.ViewPager2;

/**
 * author
 */
public class PBanner<T> extends FrameLayout {

    private boolean    canLoop;
    private long       delayMillis;
    private boolean    isTurning;
    private int        mCurrentItem;
    private List<T>    mDatas;
    private ViewPager2 viewPager2;
    private DelayTask  delayTask;

    private PageIndicatorformer        mPageIndicatorformer;
    private PageIndicatorformerAdapter mIndicatorformerAdapter;


    public PBanner(Context context) {
        this(context, null);
    }

    public PBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN && isTurning) {
            stopTurning();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            startTurning(delayMillis);
        }

        return super.dispatchTouchEvent(ev);
    }

    private void initialize(Context context, AttributeSet attrs) {
        viewPager2 = new ViewPager2(context, attrs);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        viewPager2.setLayoutParams(params);
        delayTask = new DelayTask(this);
        setCanLoop(true);
        RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
        mIndicatorformerAdapter = new PageIndicatorformerAdapter(recyclerView.getLayoutManager());
        viewPager2.registerOnPageChangeCallback(mIndicatorformerAdapter);
        attachViewToParent(viewPager2, 0, viewPager2.getLayoutParams());
    }

    public PBanner setAdapter(PageHelperListener listener, List<T> datas) {
        this.mDatas = datas;
        PBAdapter pbAdapter = new PBAdapter(listener);
        viewPager2.setAdapter(pbAdapter);
        viewPager2.setCurrentItem(getStartPos(), false);
        mCurrentItem = getStartPos();
        return this;
    }

    public void startTurning(long delay) {
        delayMillis = delay;
        isTurning = true;
        postDelayed(delayTask, delayMillis);
    }

    public void setCanLoop(boolean isLoop) {
        canLoop = isLoop;
    }

    public void stopTurning() {
        isTurning = false;
        if (delayTask != null) {
            removeCallbacks(delayTask);
        }
    }

    public boolean getIsTurning() {
        return isTurning;
    }

    public ViewPager2 getViewPager2() {
        return viewPager2;
    }

    public void setPageIndicator(PageIndicatorformer indicatorformer) {
        if (indicatorformer == null || indicatorformer == mIndicatorformerAdapter.getPageIndicatorformer())
            return;
        mPageIndicatorformer = indicatorformer;
        mIndicatorformerAdapter.setPageIndicatorformer(indicatorformer, this);
        mIndicatorformerAdapter.onPageSelected(mCurrentItem);
    }

    private int getStartPos() {
        if (getRealCount() == 0 || !canLoop)
            return 0;

        int currPosItem = Integer.MAX_VALUE / 2;

        while (currPosItem % getRealCount() != 0) {
            currPosItem--;
        }
        return currPosItem;
    }

    protected int getRealCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    protected int getRealPos(int position) {
        int real = getRealCount();
        if (real == 0) return 0;

        if (!canLoop) return position;
        return position % real;
    }

    static class DelayTask implements Runnable {

        private WeakReference<PBanner> bannerWeakReference;

        private DelayTask(PBanner pBanner) {
            bannerWeakReference = new WeakReference<>(pBanner);
        }

        @Override
        public void run() {
            PBanner pBanner = bannerWeakReference.get();
            if (pBanner != null && pBanner.isTurning) {
                if (pBanner.viewPager2.getCurrentItem() != pBanner.mCurrentItem - 1) {
                    pBanner.mCurrentItem = pBanner.viewPager2.getCurrentItem();
                }
                pBanner.viewPager2.setCurrentItem(++pBanner.mCurrentItem);
                pBanner.postDelayed(pBanner.delayTask, pBanner.delayMillis);
            }
        }
    }

    static class PBHolder extends ViewHolder {

        private ImageView imageView;

        private Action mAction;

        public PBHolder(Action action, @NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
            mAction = action;
        }
    }

    class PBAdapter extends RecyclerView.Adapter<PBHolder> {

        private PageHelperListener listener;

        private PBAdapter(PageHelperListener listener) {
            this.listener = listener;
        }


        @NonNull
        @Override
        public PBHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Action action = listener.createAction();
            View itemView = action.createView(parent.getContext());
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new PBHolder(action, itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull PBHolder holder, int position) {
            holder.mAction.updateUI(mDatas.get(getRealPos(position)));
        }

        @Override
        public int getItemCount() {
            return canLoop ? Integer.MAX_VALUE : getRealCount();
        }
    }
}
