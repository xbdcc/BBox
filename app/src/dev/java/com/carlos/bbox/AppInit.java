package com.carlos.bbox;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by caochang on 2017/8/1.
 */


public class AppInit {

    public void init() {

        configLeakCanary();

    }

    private void configLeakCanary() {

        if (LeakCanary.isInAnalyzerProcess(MyApplication.getInstance())) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(MyApplication.getInstance());
    }

}
