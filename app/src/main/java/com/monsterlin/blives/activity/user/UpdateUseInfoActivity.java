package com.monsterlin.blives.activity.user;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * Created by monsterLin on 2016/7/28.
 */
public class UpdateUseInfoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.edt_username)
    EditText edt_username;
    @InjectView(R.id.edt_nick)
    EditText edt_nick;
    @InjectView(R.id.edt_depart)
    EditText edt_depart;
    @InjectView(R.id.edt_tel)
    EditText edt_tel;
    @InjectView(R.id.edt_email)
    EditText edt_email;
    @InjectView(R.id.iv_userphoto)
    CircleImageView iv_userphoto;

    private BUser bUser;

    private String nickString , userNameString , departString , telString ,emailString;

    private AlertDialog dialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinfo);
        ButterKnife.inject(this);
        initView();
        initData();

    }

    private void initData() {

        bUser = (BUser) getIntent().getSerializableExtra("bUser");

        if (bUser != null) {


            edt_username.setText(bUser.getUsername());
            edt_nick.setText(bUser.getNick());
            edt_depart.setText(bUser.getDepart());
            edt_tel.setText(bUser.getMobilePhoneNumber());
          //  edt_tel.setEnabled(false);
            edt_email.setText(bUser.getEmail());
          //  edt_email.setEnabled(false);

            if (TextUtils.isEmpty(bUser.getFigureurl())) {
                if (TextUtils.isEmpty(bUser.getUserPhoto().getFileUrl(UpdateUseInfoActivity.this))) {
                    iv_userphoto.setImageResource(R.drawable.ic_bzu);
                } else {
                    ImageLoader.getInstance().displayImage(bUser.getUserPhoto().getFileUrl(UpdateUseInfoActivity.this), iv_userphoto);
                }
            } else {
                ImageLoader.getInstance().displayImage(bUser.getFigureurl(), iv_userphoto);
            }
        } else {
            showToast("数据获取异常");
        }
    }

    private void initView() {
        toolbar.setTitle("更新资料");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity(UserInfoActivity.class);
                finish();
            }
        });

        dialog=new SpotsDialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ok, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_ok:
                dialog.show();


                nickString=edt_nick.getText().toString();
                userNameString=edt_username.getText().toString();
                departString=edt_depart.getText().toString();
                telString=edt_tel.getText().toString();
                emailString=edt_email.getText().toString();

                if (TextUtils.isEmpty(nickString)
                        &&TextUtils.isEmpty(userNameString)
                        && TextUtils.isEmpty(departString)
                        &&TextUtils.isEmpty(telString)
                        &&TextUtils.isEmpty(emailString)){
                    showToast("请完整填写信息");
                    dialog.dismiss();
                }else {
                    BUser bUser = new BUser() ;
                    bUser.setUsername(userNameString);
                    bUser.setNick(nickString);
                    bUser.setDepart(departString);
                    bUser.setMobilePhoneNumber(telString);
                    bUser.setEmail(emailString);

                    bUser.update(UpdateUseInfoActivity.this, BmobUser.getCurrentUser(UpdateUseInfoActivity.this).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            showToast("更新资料完成");
                            finish();
                            nextActivity(UserInfoActivity.class);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            dialog.dismiss();
                            showToast("更新资料发生异常："+s);
                        }
                    });
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
