package com.monsterlin.blives.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 用户反馈
 * Created by monsterLin on 2016/4/12.
 */
public class FeedBackActivity extends BaseActivity {

    private Toolbar toolbar;
    private MaterialEditText edt_email ,edt_feedback;

    private TextView tv_author , tv_qq;

    private ImageView  iv_send ,iv_img ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initData();
        initView();
        initToolBar();
        initEvent();
    }

    /**
     * //TODO 检测用户否登录
     * 初始化数据源
     * 如果用户已经登录了，则从服务端获得邮箱数据
     * 如果用户没有登录，则需要手动输入邮箱
     */
    private void initData() {

    }

    private void initEvent() {
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("AA");
            }
        });

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点什么点，说你的~╰(￣▽￣)╭");
            }
        });
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        edt_email= (MaterialEditText) findViewById(R.id.edt_email);
        edt_feedback= (MaterialEditText) findViewById(R.id.edt_feedback);
        tv_author= (TextView) findViewById(R.id.tv_author);
        tv_qq= (TextView) findViewById(R.id.tv_qq);
        iv_send= (ImageView) findViewById(R.id.iv_send);
        iv_img= (ImageView) findViewById(R.id.iv_img);

        tv_author.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
        tv_qq.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));

        tv_author.setText("monster");
        tv_qq.setText("876948462");

    }

    private void initToolBar() {
        toolbar.setTitle("用户反馈");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

