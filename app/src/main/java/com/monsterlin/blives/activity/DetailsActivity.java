package com.monsterlin.blives.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.blives.R;
import com.monsterlin.blives.utils.StatusBarCompat;

/**
 * 新闻详情
 * Created by monsterLin on 2016/4/2.
 */
public class DetailsActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));  //沉浸式状态栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle("标题");
        //设置还没有收缩状态下的字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置收缩后Toolbar上的字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(R.color.white);
    }



}
