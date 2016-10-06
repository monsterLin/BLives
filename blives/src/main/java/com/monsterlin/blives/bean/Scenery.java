package com.monsterlin.blives.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 校园风光
 * Created by monsterLin on 2016/4/7.
 */
public class Scenery extends BmobObject {
    private String sceneryintro;  //描述
    private BmobFile sceneryimg;   //图片

    public String getSceneryintro() {
        return sceneryintro;
    }

    public void setSceneryintro(String sceneryintro) {
        this.sceneryintro = sceneryintro;
    }

    public BmobFile getSceneryimg() {
        return sceneryimg;
    }

    public void setSceneryimg(BmobFile sceneryimg) {
        this.sceneryimg = sceneryimg;
    }
}
