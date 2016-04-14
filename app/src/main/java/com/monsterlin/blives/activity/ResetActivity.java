package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * 找回密码
 * Created by monsterLin on 2016/4/14.
 */
public class ResetActivity extends BaseActivity{

    private Toolbar toolbar;
    private EditText edt_mail;
    private Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        initView();
        initToolBar();
        initEvent();
    }

    private void initToolBar() {
        toolbar.setTitle("找回密码");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initEvent() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_mail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    showToast("请输入邮箱");
                }else {
                    if (email .matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")){
                        BmobUser.resetPasswordByEmail(ResetActivity.this, email, new ResetPasswordByEmailListener() {
                            @Override
                            public void onSuccess() {
                                showToast("重置密码请求成功，请到："+email+"邮箱进行密码重置操作");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("重置密码请求失败："+s);
                            }
                        });
                    }else {
                        showToast("邮箱格式错误");
                    }


                }
            }
        });
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        edt_mail= (EditText) findViewById(R.id.edt_mail);
        btn_reset= (Button) findViewById(R.id.btn_reset);
    }
}
