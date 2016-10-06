package com.monsterlin.blives.bean;

/**
 * 生活
 * Created by monsterLin on 2016/4/22.
 */
public class App {
    private int appicon;
    private String appname ;
    private String appurl;


    public App(int appicon, String appname, String appurl) {
        this.appicon = appicon;
        this.appname = appname;
        this.appurl = appurl;
    }

    public int getAppicon() {
        return appicon;
    }

    public void setAppicon(int appicon) {
        this.appicon = appicon;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }
}
