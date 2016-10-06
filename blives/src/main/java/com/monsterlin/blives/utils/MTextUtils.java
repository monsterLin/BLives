package com.monsterlin.blives.utils;

/**
 * 字符串格式化
 * Created by monsterLin on 6/24/2016.
 */
public class MTextUtils {

    /**
     * 去掉文本开头和结尾的空格
     * @param text
     * @return
     */
    public static String textFormat(String text){
            return text.trim();
    }


    /**
     * 格式化时间
     * @param date
     * @return
     */
    public static String dateFormat(String date){
        return date.substring(0,10);
    }
}
