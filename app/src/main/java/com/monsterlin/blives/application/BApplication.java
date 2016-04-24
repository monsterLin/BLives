package com.monsterlin.blives.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * 应用的Application
 * Created by monsterLin on 2016/4/12.
 */
public class BApplication  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
