package com.monsterlin.blives.application;

import android.app.Application;
import android.graphics.Bitmap;

import com.baidu.mapapi.SDKInitializer;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import im.fir.sdk.FIR;

/**
 * 应用的Application
 * Created by monsterLin on 2016/4/12.
 */
public class BApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         *  创建默认的ImageLoader配置参数
         */
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);

        /**
         * 百度地图相关配置
         */
        SDKInitializer.initialize(getApplicationContext());

        /**
         * BugHD
         */
        FIR.init(this);

    }
}
