package com.carlos.bbox.zhihu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by caochang on 2017/6/27.
 */

public class ZhihuDailyTodayVO {

    @SerializedName("date")
    private String date;
    @SerializedName("top_stories")
    private List<ZhihuDailyItemVO> mTopStories;
    @SerializedName("stories")
    private List<ZhihuDailyItemVO> mStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ZhihuDailyItemVO> getTopStories() {
        return mTopStories;
    }

    public void setTopStories(List<ZhihuDailyItemVO> topStories) {
        mTopStories = topStories;
    }

    public List<ZhihuDailyItemVO> getStories() {
        return mStories;
    }

    public void setStories(List<ZhihuDailyItemVO> stories) {
        mStories = stories;
    }

}
