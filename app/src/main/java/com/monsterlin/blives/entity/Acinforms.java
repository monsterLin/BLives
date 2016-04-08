package com.monsterlin.blives.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 学术通知实体类
 * Created by monsterLin on 2016/4/8.
 */
public class Acinforms extends BmobObject{

    private String title ;
    private String content ;
    private BmobFile informimg;
    private BmobDate informdate ;


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

    public BmobFile getInformimg() {
        return informimg;
    }

    public void setInformimg(BmobFile informimg) {
        this.informimg = informimg;
    }

    public BmobDate getInformdate() {
        return informdate;
    }

    public void setInformdate(BmobDate informdate) {
        this.informdate = informdate;
    }
}
