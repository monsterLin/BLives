package com.monsterlin.blives;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.monsterlin.blives.constants.BmobKey;
import com.monsterlin.blives.utils.ToastUtils;

import cn.bmob.v3.Bmob;

/**
 * 在这里我们可以把基本的公共方法写入，如Toast,Intent等方法
 * Created by monsterLin on 2016/2/16.
 * @email：linfanrong235@outlook.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, BmobKey.APPKEY); //Bmob的初始化
    }




    /**
     * 显示Toast
     * @param msg
     */
    protected void showToast(String msg) {
        ToastUtils.showToast(this, msg, Toast.LENGTH_SHORT);
    }

}
