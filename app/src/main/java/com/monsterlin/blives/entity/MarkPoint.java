package com.monsterlin.blives.entity;

import cn.bmob.v3.BmobObject;

/**
 * 地图mark点
 * Created by monsterLin on 2016/4/17.
 */
public class MarkPoint extends BmobObject {

    private String latitude ; //纬度
    private String longitude ;//经度
    private String place ; //地名
    private String introbuce ; //描述

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIntrobuce() {
        return introbuce;
    }

    public void setIntrobuce(String introbuce) {
        this.introbuce = introbuce;
    }
}
