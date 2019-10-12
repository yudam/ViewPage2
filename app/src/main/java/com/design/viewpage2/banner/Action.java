package com.design.viewpage2.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * User: maodayu
 * Date: 2019/10/12
 * Time: 14:48
 */
public interface Action<T> {

    View createView(Context context);

    void updateUI(T data);
}
