package com.carlos.bbox.util;

/**
 * Created by caochang on 2017/8/27.
 */

public class TimeUtil {

    public void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            LogUtil.e("sleep error:",e);
        }
    }
}
