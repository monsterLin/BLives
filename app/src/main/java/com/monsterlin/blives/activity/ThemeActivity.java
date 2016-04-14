package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

/**
 * 主题选择界面
 * Created by monsterLin on 2016/4/14.
 */
public class ThemeActivity extends BaseActivity{
   private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        intiView();
        initToolBar();
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
    }
}
