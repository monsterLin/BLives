package com.monsterlin.blives.entity;

/**
 * 主题
 * Created by monsterLin on 2016/4/18.
 */
public class Theme {
    private int color ;
    private String colorname ;

    public Theme(int color, String colorname) {
        this.color = color;
        this.colorname = colorname;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getColorname() {
        return colorname;
    }

    public void setColorname(String colorname) {
        this.colorname = colorname;
    }
}
