package com.monsterlin.blives.activity.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.MainActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.monsterlin.blives.utils.CountDownButtonHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import dmax.dialog.SpotsDialog;

/**
 * Created by monsterLin on 2016/7/27.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)  Toolbar toolbar;
    @InjectView(R.id.btn_getSmsCode)  Button btn_getSmsCode;
    @InjectView(R.id.edt_tel)  EditText edt_tel ;
    @InjectView(R.id.edt_SmsCode) EditText edt_SmsCode ;
    @InjectView(R.id.btn_regist) Button btn_regist ;
    @InjectView(R.id.edt_pass) EditText edt_pass;

    private String telString;
    private String codeString ;
    private String passString;
   private  Dialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.inject(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        btn_getSmsCode.setOnClickListener(this);
        btn_regist.setOnClickListener(this);
    }

    private void initView() {
        dialog = new SpotsDialog(RegistActivity.this);

        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_getSmsCode:
                telString=edt_tel.getText().toString();
                if (!TextUtils.isEmpty(telString)){
                    loadSmsCode();
                    BmobSMS.requestSMSCode(this, telString, "mysms", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer smsId, BmobException ex) {
                            if(ex==null){
                                Log.i("SmsCode", "短信id："+smsId);
                            }
                        }
                    });
                }

                break;
            case R.id.btn_regist:
                codeString=edt_SmsCode.getText().toString();
                telString=edt_tel.getText().toString();
                passString=edt_pass.getText().toString();

                if (TextUtils.isEmpty(codeString)&&TextUtils.isEmpty(telString)&&TextUtils.isEmpty(passString)){
                    showToast("请完整填写信息");
                }else {
                    dialog.show();
                    BUser bUser = new BUser();
                    bUser.setMobilePhoneNumber(telString);
                    bUser.setUsername(telString);
                    bUser.setPassword(passString);
                    bUser.signOrLogin(this, codeString, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            finish();
                            nextActivity(MainActivity.class);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            dialog.dismiss();
                            showToast("注册失败："+s);
                        }
                    });
                }

                break;
        }
    }

    /**
     * 控制获取验证码的按钮
     */
    private void loadSmsCode() {
        CountDownButtonHelper helper = new CountDownButtonHelper(btn_getSmsCode,"倒计时",60,1);
        helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {
            @Override
            public void finish() {
                btn_getSmsCode.setText("再次获取");
            }
        });
        helper.start();
    }
}
