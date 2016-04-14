package com.monsterlin.blives.entity;

import cn.bmob.v3.BmobObject;

/**
 * 用户反馈
 * Created by monsterLin on 2016/4/14.
 */
public class FeedBack extends BmobObject {
    private String device ;//设备类型
    private String IMEI ; //设备的IMEI
    private String feedContent ; //反馈内容
    private String email ;//邮箱


    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getFeedContent() {
        return feedContent;
    }

    public void setFeedContent(String feedContent) {
        this.feedContent = feedContent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
