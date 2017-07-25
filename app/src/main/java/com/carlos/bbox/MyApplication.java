package com.carlos.bbox;

import android.app.Application;

/**
 * Created by caochang on 2017/6/26.
 */

public class MyApplication extends Application{

    private static MyApplication instance;

    public static synchronized MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

//        // 栈视图等功能，建议在Application里初始化
//        Fragmentation.builder()
//                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG)
//                .install();
    }

}
