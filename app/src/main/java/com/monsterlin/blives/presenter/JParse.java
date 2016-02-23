package com.monsterlin.blives.presenter;

import com.monsterlin.blives.entity.SchoolNews;

import java.util.List;

/**
 * 解析学校网站数据接口
 * Created by monsterLin on 2016/2/23.
 */
public interface JParse {
    /**
     * 得到学校新闻
     * @return list
     */
    List<SchoolNews> getSchoolNews();

    /**
     * 得到学术新闻
     * @return list
     */
     List<SchoolNews> getAcadeMicNews();


}
