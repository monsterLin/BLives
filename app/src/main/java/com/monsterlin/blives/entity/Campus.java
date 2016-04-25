package com.monsterlin.blives.entity;

import cn.bmob.v3.BmobObject;

/**
 * 校园活动
 * Created by monsterLin on 2016/4/25.
 */
public class Campus  extends BmobObject{
    private String title ;
    private String content ;
    private BUser bUser ;
    private String campusDate ; //活动时间
    private String campusType; //活动类型
    private String campusPlace ; //活动地点
    private String campusObject ; //活动对象


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BUser getbUser() {
        return bUser;
    }

    public void setbUser(BUser bUser) {
        this.bUser = bUser;
    }

    public String getCampusDate() {
        return campusDate;
    }

    public void setCampusDate(String campusDate) {
        this.campusDate = campusDate;
    }

    public String getCampusType() {
        return campusType;
    }

    public void setCampusType(String campusType) {
        this.campusType = campusType;
    }

    public String getCampusPlace() {
        return campusPlace;
    }

    public void setCampusPlace(String campusPlace) {
        this.campusPlace = campusPlace;
    }

    public String getCampusObject() {
        return campusObject;
    }

    public void setCampusObject(String campusObject) {
        this.campusObject = campusObject;
    }
}
