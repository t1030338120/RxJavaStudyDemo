package com.app.rxjava.rxjava.entity;

import android.graphics.drawable.Drawable;

/**
 * 手机上的app基本信息
 */
public class AppInfo implements Comparable<Object> {
 
    long mLastUpdateTime;
    public String appName;
    public Drawable appIcon;

    public AppInfo(String nName,  Drawable icon) {
        appName = nName;
        appIcon = icon;
//        mLastUpdateTime = lastUpdateTime;
    }
 
    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
        return appName.compareTo(f.appName);
    }
}
 