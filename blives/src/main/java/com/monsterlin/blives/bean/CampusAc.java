package com.monsterlin.blives.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 社团活动
 * Created by monsterLin on 2016/7/28.
 */
public class CampusAc extends BmobObject {
    private String acTitle ;  //标题
    private Union acUnion ;  //发布单位
    private String acContent ; //活动内容
    private List<String> acDevList ; //活动进展

    public String getAcTitle() {
        return acTitle;
    }

    public void setAcTitle(String acTitle) {
        this.acTitle = acTitle;
    }

    public Union getAcUnion() {
        return acUnion;
    }

    public void setAcUnion(Union acUnion) {
        this.acUnion = acUnion;
    }

    public String getAcContent() {
        return acContent;
    }

    public void setAcContent(String acContent) {
        this.acContent = acContent;
    }

    public List<String> getAcDevList() {
        return acDevList;
    }

    public void setAcDevList(List<String> acDevList) {
        this.acDevList = acDevList;
    }
}
