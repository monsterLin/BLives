package com.monsterlin.blives.activity.user;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 显示用户资料
 * Created by monsterLin on 2016/4/14.
 */
public class UserInfoActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.iv_userphoto)
    CircleImageView iv_userphoto;

    private String objectId;
    private TextView tv_nick, tv_depart, tv_email, tv_name, tv_tel;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.inject(this);

        objectId = BmobUser.getCurrentUser(this).getObjectId();

        initView();
        initToolBar(toolbar, "个人资料", true);
        initData();
    }

    private void initData() {
        if (null != objectId) {
            BmobQuery<BUser> query = new BmobQuery<>();
            query.getObject(this, objectId, new GetListener<BUser>() {
                @Override
                public void onSuccess(BUser bUser) {
                    if (bUser != null) {

                        bundle=new Bundle();
                        bundle.putSerializable("bUser",bUser);


                        if (TextUtils.isEmpty(bUser.getFigureurl())) {
                            ImageLoader.getInstance().displayImage(bUser.getUserPhoto().getFileUrl(UserInfoActivity.this), iv_userphoto);
                        } else {
                            ImageLoader.getInstance().displayImage(bUser.getFigureurl(), iv_userphoto);
                        }

                        tv_nick.setText(bUser.getNick());
                        tv_nick.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
                        tv_depart.setText(bUser.getDepart());
                        tv_depart.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));


                        if (!TextUtils.isEmpty(bUser.getEmail())) {
                            tv_email.setText(bUser.getEmail());
                        } else {
                            tv_email.setText("请完善邮箱信息");

                        }

                        if (!TextUtils.isEmpty(bUser.getMobilePhoneNumber())) {
                            tv_tel.setText(bUser.getMobilePhoneNumber());
                        } else {
                            tv_tel.setText("请完善手机号信息");
                        }
                        tv_name.setText(bUser.getUsername());

                    }
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
    }


    private void initView() {
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        tv_depart = (TextView) findViewById(R.id.tv_depart);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inform, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_edit) {


            nextActivity(UpdateUseInfoActivity.class,bundle);

            finish();
            return true;
        }

        if (id==R.id.item_changepass){
            nextActivity(ChangePassActivity.class);
        }

        return super.onOptionsItemSelected(item);
    }
}
