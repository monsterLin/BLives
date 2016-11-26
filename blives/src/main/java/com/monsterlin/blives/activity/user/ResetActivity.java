package com.monsterlin.blives.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * 找回密码
 * Created by monsterLin on 2016/4/14.
 */
public class ResetActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.edt_mail)
    EditText edt_mail;

    @InjectView(R.id.btn_reset)
    Button btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        ButterKnife.inject(this);
        initToolBar(toolbar,true);
        toolbar.setTitle("找回密码");
        initEvent();
    }


    private void initEvent() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_mail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    showToast("请输入邮箱");
                }else {
                    if (email .matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")){
                        BmobUser.resetPasswordByEmail(ResetActivity.this, email, new ResetPasswordByEmailListener() {
                            @Override
                            public void onSuccess() {
                                showToast("重置密码请求成功，请到："+email+"邮箱进行密码重置操作");
                                finish();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("重置密码请求失败："+s);
                            }
                        });
                    }else {
                        showToast("邮箱格式错误");
                    }

                }
            }
        });
    }

}
