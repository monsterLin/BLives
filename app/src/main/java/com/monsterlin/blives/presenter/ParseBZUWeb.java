package com.monsterlin.blives.presenter;

import com.monsterlin.blives.entity.SchoolNews;

import java.io.IOException;
import java.util.List;

/**
 * 解析学校网站数据接口，测试通过学校新闻 、学术新闻
 * Created by monsterLin on 2016/2/23.
 */
public interface ParseBZUWeb {
    /**
     * 得到学校新闻
     * @return list
     */
    List<SchoolNews> getSchoolNews(String mainNewsUrl,int count) throws IOException;

    /**
     * 解析新闻详情
     * @return SchoolNews
     */
     SchoolNews  parseDetail(String newsUrl);


}
