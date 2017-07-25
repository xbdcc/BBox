package com.carlos.bbox.zhihu.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by caochang on 2017/7/14.
 */

public class ZhihuDailyItemVO {
    @SerializedName("id")
    private String id;
    @SerializedName("images")
    private String[] images;
    @SerializedName("type")
    private int type;
    @SerializedName("title")
    private String title;
    private String date;
    public boolean hasFadedIn = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isHasFadedIn() {
        return hasFadedIn;
    }

    public void setHasFadedIn(boolean hasFadedIn) {
        this.hasFadedIn = hasFadedIn;
    }
}