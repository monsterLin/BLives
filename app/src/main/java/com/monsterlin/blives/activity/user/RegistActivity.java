package com.monsterlin.blives.activity.user;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.monsterlin.blives.utils.CountDownButtonHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

/**
 *
 * TODO 待测试
 * 短信验证模块
 * Created by monsterLin on 7/18/2016.
 */
public class RegistActivity extends BaseActivity {

    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.telWrapper) TextInputLayout telWrapper;
    @InjectView(R.id.smsCodeWrapper) TextInputLayout smsCodeWrapper;
    @InjectView(R.id.edt_tel) EditText edt_tel ;
    @InjectView(R.id.edt_smsCode) EditText edt_smsCode ;
    @InjectView(R.id.btn_getSmsCode)  Button btn_getSmsCode ;
    @InjectView(R.id.passWrapper)  TextInputLayout passWrapper ;
    @InjectView(R.id.edt_pass) EditText edt_pass;
    private String telPhone ;
    private String pass;
    private String smsCode ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.inject(this);
        initView();
        initToolBar();


        btn_getSmsCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telPhone = edt_tel.getText().toString();
                if (!TextUtils.isEmpty(telPhone)){
                    countTime();
                    BmobSMS.requestSMSCode(RegistActivity.this, telPhone, "mysms", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            if(ex==null){
                                showToast("验证短信已发送"+smsId);
                            }else {
                                showToast(""+ex.getMessage());
                            }
                        }
                    });
                }else {
                    showToast("手机号不能为空");
                }
            }
        });
    }

    private void initToolBar() {
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextActivity(LoginActivity.class);
                }
            });

    }

    /**
     * 倒计时
     */
    private void countTime() {
        CountDownButtonHelper helper = new CountDownButtonHelper(btn_getSmsCode,"倒计时",60,1);

        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                btn_getSmsCode.setText("再次获取");
            }
        });
        helper.start();
    }

    private void initView() {
        telWrapper.setHint("请输入电话号码");
        smsCodeWrapper.setHint("请输入验证码");
        passWrapper.setHint("请输入登陆密码");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_ok,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.item_ok){

             pass = edt_pass.getText().toString();
            smsCode=edt_smsCode.getText().toString();

            if (TextUtils.isEmpty(telPhone)&&TextUtils.isEmpty(pass)&&TextUtils.isEmpty(smsCode)){

                    showToast("请检查信息是否完整");

            }else {
                BUser bUser = new BUser();
                bUser.setMobilePhoneNumber(telPhone);
                bUser.setUsername("用户："+telPhone);
                bUser.setPassword(pass);
                bUser.setNick("进阶的BLives"+telPhone);
                bUser.setDepart("滨州学院某系院");
                bUser.signOrLogin(RegistActivity.this, smsCode, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        showToast("注册成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("注册失败："+s);
                    }
                });
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
