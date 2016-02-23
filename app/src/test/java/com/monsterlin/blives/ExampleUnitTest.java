package com.monsterlin.blives;

import com.monsterlin.blives.presenter.JParse;
import com.monsterlin.blives.presenter.impl.JParseWeb;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 单元测试
 * @author  monsterLin
 */
public class ExampleUnitTest {
    @Test
    public void testJParseWeb() throws Exception {
        JParse jParseWeb = new JParseWeb();
        jParseWeb.getSchoolNews();
    }
}