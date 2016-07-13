package com.monsterlin.blives.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 就业信息
 * Created by monsterLin on 2016/4/8.
 */
public class Jobnews extends BmobObject {
    private String title ;
    private String content ;
    private BmobFile newsimg ;
    private BmobDate newsdate;

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

    public BmobFile getNewsimg() {
        return newsimg;
    }

    public void setNewsimg(BmobFile newsimg) {
        this.newsimg = newsimg;
    }

    public BmobDate getNewsdate() {
        return newsdate;
    }

    public void setNewsdate(BmobDate newsdate) {
        this.newsdate = newsdate;
    }
}
