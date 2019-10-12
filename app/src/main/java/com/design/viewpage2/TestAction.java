package com.design.viewpage2;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.design.viewpage2.banner.Action;

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
        Log.i("MDY", "updateUI="+data);
        Log.i("MDY", "updateUI1="+mImageView);
        Glide.with(mContext).load(data).into(mImageView);
    }
}
