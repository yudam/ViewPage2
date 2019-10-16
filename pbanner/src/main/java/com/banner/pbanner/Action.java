package com.banner.pbanner;

import android.content.Context;
import android.view.View;

/**
 * author
 */
public interface Action<T> {

    View createView(Context context);

    void updateUI(T data);
}
