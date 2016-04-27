package com.monsterlin.blives;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 启动页面
 * Created by Administrator on 2015/11/14.
 */
public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences=null;//引导页
    private static final String PREFS_NAME="IsFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //TODO
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //线程操作
        new Thread(){
            public void run() {
                try {
                    sleep(1500);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (sharedPreferences.getBoolean("isFirst",true)) {
//						Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isFirst", false);
                        editor.commit();
                        //引导页面
                        Intent mainIntent = new Intent(WelcomeActivity.this, OurViewPager.class);
                        startActivity(mainIntent);
                        finish();
                    }else{
                        //主程序
                        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    finish();
                } catch (InterruptedException e) {
                }
            };
        }.start();
    }
}
