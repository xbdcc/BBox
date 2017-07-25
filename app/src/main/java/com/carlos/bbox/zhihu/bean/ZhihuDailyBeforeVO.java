package com.carlos.bbox.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by caochang on 2017/6/27.
 */

public class ZhihuDailyBeforeVO {

    @SerializedName("date")
    private String date;
    @SerializedName("stories")
    private List<ZhihuDailyItemVO> mStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDailyItemVO> getStories() {
        return mStories;
    }

    public void setStories(List<ZhihuDailyItemVO> stories) {
        mStories = stories;
    }

  
}
