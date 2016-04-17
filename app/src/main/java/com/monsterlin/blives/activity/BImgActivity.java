package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.utils.ImageLoader;

/**
 * 图片详情
 * Created by monsterLin on 2016/4/17.
 */
public class BImgActivity extends BaseActivity {

    private ImageView iv_bimg;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bimg);
        initView();
        initToolBar();

        String imgUrl = getIntent().getStringExtra("imgUrl");
        iv_bimg.setTag(imgUrl);
        new ImageLoader().showImageByAsyncTask(iv_bimg,imgUrl);

    }

    private void initToolBar() {
        toolbar.setTitle("浏览");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        iv_bimg= (ImageView) findViewById(R.id.iv_bimg);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
    }
}
