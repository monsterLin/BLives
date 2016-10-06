package com.monsterlin.blives.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户信息实体类
 * @author monster
 * @date:2016-04-13
 *  说明：本实体类继承的BmobUser，由于这张表作为Bmob的系统表，所以内部的字段
 * 已经定义好，所以不需要重复定义，如果重复定义，将导致程序出错
 * 附录：已含字段：
 *   		objectId,username,password,email,authData........
 *   所以：用户可以在添加实体类的时候只需要添加除了这几个字段就ok啦
 */
public class BUser extends BmobUser {
    private String nick ; //昵称
    private String depart ;  //系院
    private BmobFile userPhoto ; //用户头像
    private String figureurl ; //QQ头像的url地址

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public BmobFile getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(BmobFile userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getFigureurl() {
        return figureurl;
    }

    public void setFigureurl(String figureurl) {
        this.figureurl = figureurl;
    }
}
