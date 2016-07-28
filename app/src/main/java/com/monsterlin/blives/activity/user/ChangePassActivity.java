package com.monsterlin.blives.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by monsterLin on 2016/7/28.
 */
public class ChangePassActivity extends BaseActivity {

    @InjectView(R.id.toolbar)  Toolbar toolbar;

    @InjectView(R.id.edt_oldPass)  EditText edt_oldPass;
    @InjectView(R.id.edt_newPass)  EditText edt_newPass;
    @InjectView(R.id.edt_reNewPass)  EditText edt_reNewPass;


    private String oldPassString , newPassString , reNewPassString ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        ButterKnife.inject(this);
        initToolBar(toolbar,"修改密码",true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_ok:
                doChangePass();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doChangePass() {
        oldPassString=edt_oldPass.getText().toString();
        newPassString=edt_newPass.getText().toString();
        reNewPassString=edt_reNewPass.getText().toString();

        if (TextUtils.isEmpty(oldPassString)&&TextUtils.isEmpty(newPassString)&&TextUtils.isEmpty(reNewPassString)){
            showToast("请完整填写信息");
        }else {
            if (newPassString.equals(reNewPassString)){
                BmobUser.updateCurrentUserPassword(this, oldPassString, newPassString, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        showToast("密码修改成功，下次登录生效");
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("修改密码发生异常："+s);
                    }
                });
            }else {
                showToast("新密码不一致，请检查新密码");
            }
        }
    }
}
