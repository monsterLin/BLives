package com.monsterlin.blives.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 学校新闻实体类
 * Created by monsterLin on 2016/2/24.
 */
public class SchoolNews implements Serializable{
    private String newsTitle ;
    private String newsDate ;
    private String newsContent ;
    private String newsCurrentURL; //当前新闻链接
    private List<String> newsImgURLList ;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public List<String> getNewsImgURLList() {
        return newsImgURLList;
    }

    public String getNewsCurrentURL() {
        return newsCurrentURL;
    }

    public void setNewsCurrentURL(String newsCurrentURL) {
        this.newsCurrentURL = newsCurrentURL;
    }

    public void setNewsImgURLList(List<String> newsImgURLList) {
        this.newsImgURLList = newsImgURLList;


    }

}

