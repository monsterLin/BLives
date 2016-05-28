package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.widget.ProgressWebView;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.InjectView;

/**
 * Created by monsterLin on 2016/4/22.
 */
public class LifeDetailActivity extends BaseActivity {

    @InjectView(R.id.wv_detail)
    ProgressWebView webView ;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifedetail);
        initActivityButterKnife(this);
        String life_name = getIntent().getStringExtra("life_name");
        initToolBar(toolbar,life_name,true);
        initWeb();
    }

    private void initWeb() {
        String appUrl = getIntent().getStringExtra("app_url");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(appUrl);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
