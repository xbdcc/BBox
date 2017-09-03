package com.carlos.bbox.redenvelope.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by caochang on 2017/8/27.
 */

@Entity
public class WechatRedEnvelope {

    @Id(autoincrement = true)
    private Long id;
    private String time;
    private String type;
    private String who_send;
    private String count;
    @Generated(hash = 912997087)
    public WechatRedEnvelope(Long id, String time, String type, String who_send,
            String count) {
        this.id = id;
        this.time = time;
        this.type = type;
        this.who_send = who_send;
        this.count = count;
    }
    @Generated(hash = 1019243491)
    public WechatRedEnvelope() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getWho_send() {
        return this.who_send;
    }
    public void setWho_send(String who_send) {
        this.who_send = who_send;
    }
    public String getCount() {
        return this.count;
    }
    public void setCount(String count) {
        this.count = count;
    }


}
