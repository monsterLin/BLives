package com.monsterlin.blives;

import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.presenter.ParseBZUWeb;
import com.monsterlin.blives.presenter.impl.ParseBZUWebImpl;

import org.junit.Test;

import java.util.List;

/**
 * 单元测试
 * @author  monsterLin
 */
public class ExampleUnitTest {
    @Test
    public void testJParseWeb() throws Exception {
        final String schoolNewsURL = "http://www.bzu.edu.cn/s/1/t/84/p/12/list.htm" ;  //学校新闻

        final String academicNewsURL = "http://www.bzu.edu.cn/s/1/t/84/p/13/list.htm"; //学术活动

        List<SchoolNews> mList;

        ParseBZUWeb p = new ParseBZUWebImpl();
        mList =   p.getSchoolNews(schoolNewsURL);

        for (SchoolNews s :mList){
            System.out.println(s.getNewsTitle());
            System.out.println(s.getNewsDate());
            System.out.println(s.getNewsContent());

            for (String url : s.getNewsImgURLList())
            {
                System.out.println(url);
            }

            System.out.println(">>-----------------------------------分割线---------------------------------------<<");
        }
    }
}