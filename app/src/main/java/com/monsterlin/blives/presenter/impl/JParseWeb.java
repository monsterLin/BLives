package com.monsterlin.blives.presenter.impl;

import com.monsterlin.blives.constants.SchoolURL;
import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.presenter.JParse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * @ TODO 需完善优化
 * 解析学校网站数据接口实现类
 * 为了方便数据的提取：
 * 1.拿到标题，时间，以及对应的链接
 * 2.通过拿到对应的链接在对其内容进行数据解析
 * 3.对其详情界面中的图片进行解析
 * 4.这些数据都存放到item中
 * Created by monsterLin on 2016/2/23.
 */
public class JParseWeb implements JParse {

    @Override
    public List<SchoolNews> getSchoolNews() {
        try {
            Document schoolNewsDoc =   Jsoup.connect(SchoolURL.schoolNewsURL).get();
            Elements schoolNewsContent =  schoolNewsDoc.select("[target*=_blank]");
            Elements schoolNewsDate = schoolNewsDoc.select("[width=14%]"); //学校新闻的时间，在这个地方需要单个遍历
            System.out.println(schoolNewsDate.text());
            for (Element schoolNews :schoolNewsContent){
                String news = schoolNews.text();
                String newsUrl = schoolNews.attr("href");
                System.out.println("学校新闻："+news+"       "+"绝对链接："+"http://www.bzu.edu.cn"+newsUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SchoolNews> getAcadeMicNews() {
        try {
            Document academicNewsDoc = Jsoup.connect(SchoolURL.academicNewsURL).get();
            Elements academicNewsContent = academicNewsDoc.select("[target*=_blank]");
            for (Element academicNews : academicNewsContent){
                String news =academicNews.text();
                String newsUrl = academicNews.attr("href");
                System.out.println("学术新闻："+news+"       "+"绝对链接："+"http://www.bzu.edu.cn"+newsUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
