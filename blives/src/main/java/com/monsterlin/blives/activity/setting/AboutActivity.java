package com.monsterlin.blives.activity.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 关于页面
 * Created by monsterLin on 2016/4/24.
 */
public class AboutActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tv_versionName)
    TextView tv_versionName;

    private String versionName; //版本号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        initToolBar(toolbar,true);
        toolbar.setTitle("关于");
        versionName=getAppInfo();
        tv_versionName.setText(versionName);
    }

    /**
     * 得到当前的版本号
     * @return 版本号
     */
    public String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }
}
