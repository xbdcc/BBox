package com.carlos.bbox.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caochang on 2017/8/15.
 */

public class ActivityCollector {

    private static List<Activity> sActivities=new ArrayList<>();

    public static void addActivity(Activity activity){
        sActivities.add(activity);
    }

    public static void removeActivity(Activity activity){
        sActivities.remove(activity);
    }

    public static void  finishAll(){
        for (Activity activity : sActivities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }

}