package com.monsterlin.blives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.monsterlin.blives.constants.BmobKey;
import com.monsterlin.blives.utils.ToastUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;


/**
 * 封装公共方法
 * Created by monsterLin on 2016/2/16.
 * @email：linfanrong235@outlook.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobInstallation.getCurrentInstallation(this).save();
        Bmob.initialize(this, BmobKey.APPKEY); //Bmob的初始化
    }


    /**
     * 显示Toast
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 跳到另一个页面
     * @param cls
     */
    public void nextActivity(Class cls) {
        nextActivity(cls, null);
    }

    /**
     * 带数据包的跳转
     * @param cls
     * @param bundle
     */
    public void nextActivity(Class cls, Bundle bundle) {
        Intent i = new Intent(this, cls);
        if (bundle != null)
            i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * 初始化ToolBar
     * @param toolbar
     * @param title
     */
    public void initToolBar(Toolbar toolbar ,String title,boolean isBack){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (isBack){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

}
