package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.bmob.v3.update.UpdateStatus;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * 设置界面
 * Created by monsterLin on 2016/4/14.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(R.id.toolbar)
     Toolbar toolbar;

    private Button btn_exit,btn_sms,btn_trash,btn_update,btn_help,btn_about;

    private MaterialDialog helpDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        intiView();
        initToolBar(toolbar,"设置与帮助",true);
        initEvent();
    }

    private void initEvent() {
        btn_exit.setOnClickListener(this);
        btn_sms.setOnClickListener(this);
        btn_trash.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_help.setOnClickListener(this);
        btn_about.setOnClickListener(this);
    }



    private void intiView() {
        btn_exit= (Button) findViewById(R.id.btn_exit);
        btn_sms= (Button) findViewById(R.id.btn_sms);
        btn_trash= (Button) findViewById(R.id.btn_trash);
        btn_update= (Button) findViewById(R.id.btn_update);
        btn_help= (Button) findViewById(R.id.btn_help);
        btn_about= (Button) findViewById(R.id.btn_about);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                BmobUser.logOut(this);   //清除缓存用户对象
                finish();
                break;
            case R.id.btn_sms:
                showToast("消息推送将在下一版本推出，敬请期待");
                break;
            case R.id.btn_trash:
                //TODO 需优化，此缓存非彼缓存
                BmobQuery.clearAllCachedResults(this);  //清除缓存
                showToast("清除缓存成功");
                break;
            case R.id.btn_update:
                BmobUpdateAgent.forceUpdate(this);  //手动更新
                BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                    @Override
                    public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                        switch (updateStatus){
                            case UpdateStatus.Yes:
                                //版本有更新
                                break;
                            case UpdateStatus.No:
                                showToast("已是最新版本");
                                break;
                            case UpdateStatus.ErrorSizeFormat:
                                showToast("请检查target_size填写的格式，请使用file.length()方法获取apk大小");
                                break;
                            case UpdateStatus.TimeOut:
                                showToast("查询出错或超时");
                                break;
                        }
                    }
                });
                BmobUpdateAgent.update(this);
                break;
            case R.id.btn_help:
                   helpDialog = new MaterialDialog(this)
                        .setTitle("帮助")
                        .setMessage("相信亲一定一看就懂，啊哈，对吧^_^")
                        .setNegativeButton("是的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                    helpDialog.dismiss();
                            }
                        });

                helpDialog.show();


                break;
            case R.id.btn_about:
                nextActivity(AboutActivity.class);
                break;
        }
    }
}
