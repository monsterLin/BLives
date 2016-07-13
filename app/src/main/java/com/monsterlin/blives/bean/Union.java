package com.monsterlin.blives.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 社团联盟
 * Created by monsterLin on 6/30/2016.
 */
public class Union extends BmobObject {

    private String number ; //社团编号
    private String community ; //社团名称
    private String introbuce ; //社团介绍
    private String type ; //社团类型
    private String from ; //挂靠单位
    private String director ; //社团理事长
    private String directortel ;//理事长电话
    private String join ; //社团加入方式
    private BmobFile logo  ; //社团logo

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getIntrobuce() {
        return introbuce;
    }

    public void setIntrobuce(String introbuce) {
        this.introbuce = introbuce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectortel() {
        return directortel;
    }

    public void setDirectortel(String directortel) {
        this.directortel = directortel;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public BmobFile getLogo() {
        return logo;
    }

    public void setLogo(BmobFile logo) {
        this.logo = logo;
    }
}
