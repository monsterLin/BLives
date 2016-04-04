package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

/**
 * 注册
 * Created by monsterLin on 2016/4/4.
 */
public class RegistActivity extends BaseActivity {

    private Toolbar toolbar;
    private ImageView iv_userphoto;
    private EditText edt_email ,edt_pass ,edt_name ,edt_depart , edt_tel;
    private Button btn_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        initView();
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle("注册");
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
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        iv_userphoto= (ImageView) findViewById(R.id.iv_userphoto);
        edt_email= (EditText) findViewById(R.id.edt_email);
        edt_pass= (EditText) findViewById(R.id.edt_pass);
        edt_name= (EditText) findViewById(R.id.edt_name);
        edt_depart= (EditText) findViewById(R.id.edt_depart);
        edt_tel= (EditText) findViewById(R.id.edt_tel);
        btn_regist= (Button) findViewById(R.id.btn_regist);
    }
}
