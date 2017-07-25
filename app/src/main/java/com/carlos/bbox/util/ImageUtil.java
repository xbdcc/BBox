package com.carlos.bbox.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carlos.bbox.R;

/**
 * 图片加载工具类
 * Created by caochang on 2017/7/13.
 */

public class ImageUtil {

    public static void load(Context context, String url, ImageView imageView) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).placeholder(R.mipmap.xiaobudian).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

}
