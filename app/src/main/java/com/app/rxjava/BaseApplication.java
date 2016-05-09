package com.app.rxjava;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * 描述：
 * 作者：tyc
 */
public class BaseApplication extends Application{

    String TAG = "mvp_demo";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG);
    }

}
