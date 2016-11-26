package com.monsterlin.blives.activity.app;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.widget.ProgressWebView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by monsterLin on 2016/4/22.
 */
public class AppDetailActivity extends BaseActivity {

    @InjectView(R.id.wv_detail)
    ProgressWebView webView ;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appdetail);
        ButterKnife.inject(this);
        String appName = getIntent().getStringExtra("appName");
        initToolBar(toolbar,true);
        toolbar.setTitle(appName);
        initWeb();
    }

    private void initWeb() {
        String appUrl =getIntent().getStringExtra("appUrl");
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
