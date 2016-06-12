package com.monsterlin.blives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.BUser;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 登陆界面
 * Created by monsterLin on 2016/4/4.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.edt_mail)
    EditText edt_mail;

    @InjectView(R.id.edt_pass)
    EditText  edt_pass ;

    @InjectView(R.id.btn_regist)
     Button btn_regist ;

    @InjectView(R.id. btn_login)
    Button  btn_login;


    private String mailString , passString ;

    @InjectView(R.id.progress_wheel)
    ProgressWheel progressWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initActivityButterKnife(this);
        initToolBar(toolbar,"登陆",true);
        initEvent();
    }


    private void initEvent() {
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_regist:
                Intent registIntent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(registIntent);
                break;
        }
    }

    /**
     * 用户登录
     */
    private void login() {
        progressWheel.setVisibility(View.VISIBLE);
        mailString=edt_mail.getText().toString();
        passString=edt_pass.getText().toString();

        //TODO 必须要求用户进行邮箱验证，否则无法登陆

        if(TextUtils.isEmpty(mailString)&&TextUtils.isEmpty(passString)){
            progressWheel.setVisibility(View.GONE);
            showToast("邮箱或密码未填写");
        }else {
            //邮箱进行正则表达式匹配
            if (mailString.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")){

                BmobUser.loginByAccount(LoginActivity.this, mailString, passString, new LogInListener<BUser>() {
                    @Override
                    public void done(BUser bUser, BmobException e) {

                            if (bUser!=null){

                                showToast("登录成功");
                                progressWheel.setVisibility(View.GONE);
                                finish();

                        }else {
                            showToast("邮箱或者密码不正确");
                                progressWheel.setVisibility(View.GONE);
                        }
                    }
                });

            }else {
                showToast("邮箱格式错误");
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_forget) {
            Intent resetIntent = new Intent(LoginActivity.this,ResetActivity.class);
            startActivity(resetIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
