package com.design.viewpage2;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.banner.pbanner.Action;

/**
 * User: maodayu
 * Date: 2019/10/12
 * Time: 15:16
 */
public class TestAction implements Action<String> {

    private Context mContext;
    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        ImageView imageView=new ImageView(context);
        mContext=context;
        mImageView=imageView;
        return imageView;
    }

    @Override
    public void updateUI(String data) {
        Glide.with(mContext).load(data).into(mImageView);
    }
}
