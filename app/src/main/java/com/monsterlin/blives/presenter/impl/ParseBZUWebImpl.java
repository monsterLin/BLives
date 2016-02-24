package com.monsterlin.blives.presenter.impl;

import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.presenter.ParseBZUWeb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析新闻实现类，测试通过学校新闻和学术新闻
 * Created by monsterLin on 2016/2/24.
 */
public class ParseBZUWebImpl implements ParseBZUWeb {

    /**
     * 得到新闻集合
     * @param mainNewsUrl
     * @return
     */
    @Override
    public List<SchoolNews> getSchoolNews(String mainNewsUrl)throws IOException {
        List<SchoolNews> mList = new ArrayList<SchoolNews>();
        Document newsDoc = null;

        newsDoc = Jsoup.connect(mainNewsUrl).get();

        /**
         * 这是一组标题信息，注意's'
         */
        Elements news =  newsDoc.select("table.columnStyle");

        for (Element schoolNews :news){

            String url = schoolNews.select("[target*=_blank]").attr("href");
            String newsUrl = "http://www.bzu.edu.cn"+url; //详情链接

            SchoolNews s =   parseDetail(newsUrl);
            mList.add(s);

        }
        return  mList;
    }


    /**
     * 解析新闻详情
     * @param newsUrl
     * @return
     */
    @Override
    public SchoolNews parseDetail(String newsUrl) {
        String newsTitle , newsContent , newsDate ;
        List<String> newsImgURLList = new ArrayList<String>();
        try {
            Document newsDetail  = Jsoup.connect(newsUrl).get();
            newsTitle = newsDetail.select(".biaoti3").text();
            newsContent =newsDetail.select(".content").text().substring(7);  //去除内容开头的空格
            newsDate = newsDetail.select(".STYLE2").text().substring(5,15);

//            System.out.println("标题："+newsTitle);
//            System.out.println("时间："+newsDate);
//            System.out.println("新闻内容："+newsContent);


            /**
             * 在处理图片的时候要进行判空操作，如果没有图片则不解析图片，新闻中可能含有多图
             */
            Elements newsImgsElement = newsDetail.select("[alt=\"\"]");

            if (newsImgsElement!=null){
                for (Element img  :newsImgsElement){
                    String imgURL  = img.attr("src");
                    newsImgURLList.add("http://www.bzu.edu.cn"+imgURL);  //图片封装到List中
                    // System.out.println("图片的URL："+"http://www.bzu.edu.cn"+imgURL);
                }
            }

            /**
             * 封装
             */

            SchoolNews schoolnews = new SchoolNews();
            schoolnews.setNewsTitle(newsTitle);
            schoolnews.setNewsContent(newsContent);
            schoolnews.setNewsDate(newsDate);
            schoolnews.setNewsImgURLList(newsImgURLList);

            return schoolnews;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
