package com.monsterlin.blives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

/**
 * 登陆界面
 * Created by monsterLin on 2016/4/4.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText edt_user , edt_pass ;
    private Button btn_regist , btn_login ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initToolBar();
        initEvent();
    }

    private void initToolBar() {
        toolbar.setTitle("登陆");
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
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        edt_user= (EditText) findViewById(R.id.edt_user);
        edt_pass= (EditText) findViewById(R.id.edt_pass);

        btn_login= (Button) findViewById(R.id.btn_login);
        btn_regist= (Button) findViewById(R.id.btn_regist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                break;
            case R.id.btn_regist:
                Intent registIntent = new Intent(LoginActivity.this,RegistActivity.class);
                startActivity(registIntent);
                break;
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
            showToast("忘记密码？");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
