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
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.InjectView;

/**
 * Created by monsterLin on 2016/4/22.
 */
public class LifeDetailActivity extends BaseActivity {

    @InjectView(R.id.wv_detail)
    WebView webView ;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.progress_wheel)
    ProgressWheel progressWheel;

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

        webView.loadUrl(appUrl);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //优先使用缓存

//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                progressWheel.setVisibility(View.GONE);
                } else {
                    // 加载中
                progressWheel.setVisibility(View.VISIBLE);
                }

            }
        });

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
