package com.banner.pbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author
 */
public class DefaultPageIndicatorformer extends LinearLayout implements PageIndicatorformer {

    private ArrayList<ImageView> dotsViews = new ArrayList<>();

    public DefaultPageIndicatorformer(Context context) {
        this(context, null);
    }

    public DefaultPageIndicatorformer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultPageIndicatorformer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void transIndicator(@NonNull View indicator, int totalPostion, int position) {
        if (dotsViews.isEmpty())
            initIndicator(totalPostion);
        for (int i = 0; i < totalPostion; i++) {
            ImageView child = dotsViews.get(i);
            if (child == null) return;
            child.setImageResource(R.drawable.icon_default_unselect_dot);
            if (i == position)
                child.setImageResource(R.drawable.icon_default_dot);
        }
    }

    private void initIndicator(int totalPostion) {
        for (int i = 0; i < totalPostion; i++) {
            ImageView child = createImageDots();
            dotsViews.add(child);
            addView(child);
        }
    }


    private ImageView createImageDots() {
        ImageView imageView = new ImageView(getContext());
        imageView.setPadding(5, 0, 5, 0);
        imageView.setImageResource(R.drawable.icon_default_unselect_dot);
        return imageView;
    }

    public void destroyView() {
        dotsViews.clear();
    }

}
