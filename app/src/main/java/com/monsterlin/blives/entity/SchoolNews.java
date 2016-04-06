package com.monsterlin.blives.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 学校新闻实体类
 * Created by monsterLin on 2016/2/24.
 */
public class SchoolNews extends BmobObject{

    private String title ; //标题
    private String content ; //内容
    private BmobDate newsdate ; // 时间  //-->此处必须使用BmobDate ，否则会出现 Expected a string but was BEGIN_OBJECT at line 1 column 482
    private BmobFile newsimg ; //图片

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

    public BmobDate getNewsdate() {
        return newsdate;
    }

    public void setNewsdate(BmobDate newsdate) {
        this.newsdate = newsdate;
    }

    public BmobFile getNewsimg() {
        return newsimg;
    }

    public void setNewsimg(BmobFile newsimg) {
        this.newsimg = newsimg;
    }
}

