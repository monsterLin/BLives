package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.utils.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 图片详情
 * Created by monsterLin on 2016/4/17.
 */
public class BImgActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
     Toolbar toolbar;

    @InjectView(R.id.iv_bimg)
    ImageView iv_bimg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bimg);
        ButterKnife.inject(this);
        initToolBar(toolbar,"浏览",true);

        String imgUrl = getIntent().getStringExtra("imgUrl");
        iv_bimg.setTag(imgUrl);
        new ImageLoader().showImageByAsyncTask(iv_bimg,imgUrl);

    }


}
