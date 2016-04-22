package com.monsterlin.blives.entity;

/**
 * 生活
 * Created by monsterLin on 2016/4/22.
 */
public class Life {
    private int lifeicon;
    private String lifename ;
    private String app_url;


    public Life(int lifeicon, String lifename ,String app_url) {
        this.lifeicon = lifeicon;
        this.lifename = lifename;
        this.app_url=app_url;
    }


    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public int getLifeicon() {
        return lifeicon;
    }

    public void setLifeicon(int lifeicon) {
        this.lifeicon = lifeicon;
    }

    public String getLifename() {
        return lifename;
    }

    public void setLifename(String lifename) {
        this.lifename = lifename;
    }
}
