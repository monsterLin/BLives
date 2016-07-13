package com.monsterlin.blives.biz;

import android.view.View;

/**
 * Created by monsterLin on 6/28/2016.
 */
public interface NewsBiz {

    /**
     * 初始化视图
     */
    public void initView(View view);
    /**
     * 初始化数据
     */
    public void initData();

    /**
     * 分页查询数据
     * @param page 当前页
     */
    public void getData(int page);

    /**
     * 得到最新的数据
     */
    public void getNewestData(String topDate);
}
