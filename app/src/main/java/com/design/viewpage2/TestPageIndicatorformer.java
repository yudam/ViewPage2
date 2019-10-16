package com.design.viewpage2;

import android.util.Log;
import android.view.View;

import com.banner.pbanner.PageIndicatorformer;

import androidx.annotation.NonNull;

/**
 * User: maodayu
 * Date: 2019/10/14
 * Time: 18:26
 */
public class TestPageIndicatorformer implements PageIndicatorformer {

    @Override
    public void transIndicator(@NonNull View indicator, int totalPostion, int position) {

        Log.i("MDY", "totalPostion="+totalPostion+"  position="+position);
    }
}
