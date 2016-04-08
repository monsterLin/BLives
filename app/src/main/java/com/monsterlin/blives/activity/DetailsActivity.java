package com.monsterlin.blives.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.constants.DetailType;
import com.monsterlin.blives.entity.Acinforms;
import com.monsterlin.blives.entity.Jobnews;
import com.monsterlin.blives.entity.Offnews;
import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.utils.ImageLoader;

/**
 * 新闻详情
 * Created by monsterLin on 2016/4/2.
 */
public class DetailsActivity extends BaseActivity{

    private Toolbar mToolBar ;
    private CollapsingToolbarLayout collapsingToolbarLayout ;
    private FloatingActionButton fab;

    private TextView tv_date ,tv_content;
    private ImageView iv_img;


    private SchoolNews schoolNews; //学校新闻
    private Acinforms acinforms ; //学术新闻
    private Offnews offnews; //教务信息
    private Jobnews jobnews; //就业新闻


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
      //  StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));  //沉浸式状态栏

        initView();
        initToolBar();
        initData();

    }


    /**
     * 初始化数据源
     */
    private void initData() {
        int type = getIntent().getBundleExtra("dataExtra").getInt("type");
        switch (type){
            case DetailType.SchoolNews:
                 schoolNews = (SchoolNews) getIntent().getBundleExtra("dataExtra").getSerializable("detail");  //得到上一级传来的数据
                initSchoolNews(schoolNews);
                break;
            case DetailType.Acinforms:
                acinforms= (Acinforms) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
                initAcinforms(acinforms);
                break;
            case DetailType.Offnews:
                //offnews= (Offnews) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
              //  initOffnews(offnews);
                break;
            case DetailType.Jobnews:
                //jobnews= (Jobnews) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
                //initJobnews(jobnews);
                break;
        }
    }

    /**TODO 下面内容需要优化 **/
    /**
     * 初始化学术活动信息
     * @param acinforms
     */
    private void initAcinforms(Acinforms acinforms) {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle(acinforms.getTitle()); //设置标题
        //设置还没有收缩状态下的字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置收缩后Toolbar上的字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);


        tv_content.setText(acinforms.getContent());
        tv_date.setText(StringFormate(acinforms.getInformdate().getDate()));

        if (acinforms.getInformimg()!=null){
            iv_img.setTag(acinforms.getInformimg().getFileUrl(this));

            new ImageLoader().showImageByAsyncTask(iv_img,acinforms.getInformimg().getFileUrl(this));
        }else {
            iv_img.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * 初始化校园新闻内容
     * @param schoolNews
     */
    private void initSchoolNews(SchoolNews schoolNews) {
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        collapsingToolbarLayout.setTitle(schoolNews.getTitle()); //设置标题
        //设置还没有收缩状态下的字体颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        //设置收缩后Toolbar上的字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);


        tv_content.setText(schoolNews.getContent());
        tv_date.setText(StringFormate(schoolNews.getNewsdate().getDate()));

        if (schoolNews.getNewsimg()!=null){
            iv_img.setTag(schoolNews.getNewsimg().getFileUrl(this));

            new ImageLoader().showImageByAsyncTask(iv_img,schoolNews.getNewsimg().getFileUrl(this));
        }else {
            iv_img.setVisibility(View.INVISIBLE);
        }

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



    /**
     * 格式化时间
     * @param date
     * @return
     */
    private String StringFormate (String date){
        String dateString;
        dateString = date.substring(0,10);
        return dateString ;
    }

}
