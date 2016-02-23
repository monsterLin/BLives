package com.monsterlin.blives.entity;

/**
 * 学校新闻实体类
 * Created by monsterLin on 2016/2/23.
 */
public class SchoolNews {
    private String newsTitle ; //新闻标题
    private String newsDate ; //新闻时间
    private String newsUrl ; //新闻链接
    private String newsContent ; //新闻内容

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

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }
}
