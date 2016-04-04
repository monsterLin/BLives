package com.monsterlin.blives.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.utils.ImageLoader;

/**
 * 新闻详情
 * Created by monsterLin on 2016/4/2.
 */
public class DetailsActivity extends AppCompatActivity{

    private Toolbar mToolBar ;
    private CollapsingToolbarLayout collapsingToolbarLayout ;
    private FloatingActionButton fab;

    private TextView tv_date ,tv_content;
    private ImageView iv_img;

   private SchoolNews schoolNews;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
      //  StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));  //沉浸式状态栏
        initData();
        initView();
        initToolBar();
        initCollasping();
        initDataControlller();
    }


    /**
     * 初始化数据源
     */
    private void initData() {
       schoolNews = (SchoolNews) getIntent().getBundleExtra("newsBundle").getSerializable("newsBundle");  //得到上一级传来的数据

    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        fab= (FloatingActionButton) findViewById(R.id.fab);

        tv_date= (TextView) findViewById(R.id.tv_date);
        tv_content= (TextView) findViewById(R.id.tv_content);
        iv_img= (ImageView) findViewById(R.id.iv_img);

    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initCollasping() {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle(schoolNews.getNewsTitle()); //设置标题
        //设置还没有收缩状态下的字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置收缩后Toolbar上的字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
    }

    /**
     * 初始化控件数据
     */
    private void initDataControlller() {
        tv_content.setText(schoolNews.getNewsContent());
        tv_date.setText(schoolNews.getNewsDate());

       if (schoolNews.getNewsImgURLList().size()!=0){
           iv_img.setTag(schoolNews.getNewsImgURLList().get(0));
           new ImageLoader().showImageByAsyncTask(iv_img,schoolNews.getNewsImgURLList().get(0));
       }else {
         //  iv_img.setBackgroundResource(R.drawable.ic_nopic);
           iv_img.setVisibility(View.INVISIBLE);
       }


    }



}
