package com.carlos.bbox;

import android.app.Application;

import com.carlos.bbox.util.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        LogUtil.d("---");
        new AppInit().init();

        initLocal();

//        // 栈视图等功能，建议在Application里初始化
//        Fragmentation.builder()
//                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG)
//                .install();
    }


    private void initLocal() {
        try {
            Class myInitClass = Class.forName("com.carlos.bbox.local.LocalInit");
            Method initBuglyMethod = myInitClass.getMethod("init");
            Object myInitObject = myInitClass.newInstance();
            initBuglyMethod.invoke(myInitObject);
        } catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            LogUtil.e("local init error:",e);
        }
    }

}
