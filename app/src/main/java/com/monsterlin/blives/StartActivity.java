package com.monsterlin.blives;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 启动页
 * Created by monsterLin on 2016/4/26.
 */
public class StartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Thread(){
            @Override
            public void run() {

                try {
                    sleep(1500);
                    Intent startIntent =new Intent(StartActivity.this,MainActivity.class);
                    startActivity(startIntent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }.start();
    }
}
