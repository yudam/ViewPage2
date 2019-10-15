package com.design.viewpage2.banner;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;

/**
 * author
 */
public class PageIndicatorformerAdapter extends OnPageChangeCallback {

    private PageIndicatorformer mIndicatorformer;
    private LayoutManager       mLayoutManager;
    private PBanner             mPBanner;

    protected PageIndicatorformerAdapter(LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public PageIndicatorformer getPageIndicatorformer() {
        return mIndicatorformer;
    }

    public void setPageIndicatorformer(PageIndicatorformer indicatorformer, PBanner pBanner) {
        this.mIndicatorformer = indicatorformer;
        this.mPBanner = pBanner;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);

        if (mIndicatorformer == null) return;
        View child = mLayoutManager.getChildAt(position);

        mIndicatorformer.transIndicator(child, mPBanner.getRealCount(), mPBanner.getRealPos(position));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }
}
