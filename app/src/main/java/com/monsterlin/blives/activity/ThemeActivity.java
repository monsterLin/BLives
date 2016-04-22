package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.ThemeAdapter;
import com.monsterlin.blives.entity.Theme;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 主题选择界面
 * Created by monsterLin on 2016/4/14.
 */
public class ThemeActivity extends BaseActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rv_theme)
     RecyclerView rv_theme ;

    private ThemeAdapter adapter;

    private List<Theme> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        initActivityButterKnife(this);
       initToolBar(toolbar,"主题选择",true);
        initData();
        init();
    }

    private void init() {
        adapter=new ThemeAdapter(mList,this);
        rv_theme.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_theme.setLayoutManager(layoutManager);
        rv_theme.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData() {
        mList.add(new Theme(R.color.theme_blcak,"炫酷黑"));
        mList.add(new Theme(R.color.theme_blue,"太空蓝"));
        mList.add(new Theme(R.color.theme_orange,"柠檬橙"));
        mList.add(new Theme(R.color.theme_red,"靓丽红"));
    }




}
