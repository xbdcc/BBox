package com.carlos.bbox.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by caochang on 2017/8/26.
 */

public class ToastUtil {

    public static void showToast(Context context,String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
