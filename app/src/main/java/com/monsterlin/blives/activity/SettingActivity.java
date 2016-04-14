package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import cn.bmob.v3.BmobUser;

/**
 * 设置界面
 * Created by monsterLin on 2016/4/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private Toolbar toolbar;

    private Button btn_exit,btn_sms,btn_trash,btn_update,btn_help,btn_about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        intiView();
        initToolBar();
        initEvent();
    }

    private void initEvent() {
        btn_exit.setOnClickListener(this);
        btn_sms.setOnClickListener(this);
        btn_trash.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_about.setOnClickListener(this);
    }

    private void initToolBar() {
        toolbar.setTitle("设置与帮助");
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
        btn_exit= (Button) findViewById(R.id.btn_exit);
        btn_sms= (Button) findViewById(R.id.btn_sms);
        btn_trash= (Button) findViewById(R.id.btn_trash);
        btn_update= (Button) findViewById(R.id.btn_update);
        btn_help= (Button) findViewById(R.id.btn_help);
        btn_about= (Button) findViewById(R.id.btn_about);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                BmobUser.logOut(this);   //清除缓存用户对象
                finish();
                break;
            case R.id.btn_sms:
                showToast("消息推送");
                break;
            case R.id.btn_trash:
                showToast("清除缓存");
                break;
            case R.id.btn_update:
                showToast("检测新版本");
                break;
            case R.id.btn_help:
                showToast("我要帮助");
                break;
            case R.id.btn_about:
                showToast("关于");
                break;
        }
    }
}
