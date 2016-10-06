package com.monsterlin.blives.activity.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.monsterlin.blives.MainActivity;
import com.monsterlin.blives.R;

/**
 * 启动页面
 * Created by monsterLin on 2015/11/14.
 */
public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences=null;
    private static final String PREFS_NAME="IsFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        new Thread(){
            public void run() {
                try {
                    sleep(1500);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (sharedPreferences.getBoolean("isFirst",true)) {
                        editor.putBoolean("isFirst", false);
                        editor.commit();
                        Intent mainIntent = new Intent(WelcomeActivity.this, OurViewPager.class);
                        startActivity(mainIntent);
                        finish();
                    }else{
                        Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        }.start();
    }
}
