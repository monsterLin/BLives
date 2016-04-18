package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.ThemeAdapter;
import com.monsterlin.blives.entity.Theme;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题选择界面
 * Created by monsterLin on 2016/4/14.
 */
public class ThemeActivity extends BaseActivity{
   private Toolbar toolbar;

    private RecyclerView rv_theme ;
    private ThemeAdapter adapter;

    private List<Theme> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        intiView();
        initToolBar();
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

    private void initToolBar() {
        toolbar.setTitle("主题选择");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void intiView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        rv_theme= (RecyclerView) findViewById(R.id.rv_theme);
    }
}
