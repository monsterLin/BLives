package com.monsterlin.blives.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import butterknife.InjectView;

/**
 * Created by  monsterLin on 2016/12/20.
 */

public class LoginNwActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.edt_mail)
    EditText edt_mail;

    @InjectView(R.id.edt_pass)
    EditText edt_pass;

    @InjectView(R.id.btn_regist)
    Button btn_regist;

    @InjectView(R.id.btn_login)
    Button btn_login;


    @InjectView(R.id.iv_qq_login)
    ImageView iv_qq_login;

    @InjectView(R.id.iv_wx_login)
    ImageView iv_wx_login;

    @InjectView(R.id.iv_sina_login)
    ImageView iv_sina_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_nw);
    }

    @Override
    public void onClick(View view) {

    }
}
