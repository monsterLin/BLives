package com.monsterlin.blives.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.BUser;
import com.monsterlin.blives.utils.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 显示用户资料
 * Created by monsterLin on 2016/4/14.
 */
public class ShowUserActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
     Toolbar toolbar;

//    private BmobUser bmobUser ;
    private String objectId;

    @InjectView(R.id.iv_userphoto)
     CircleImageView iv_userphoto;
    private TextView tv_nick ,tv_depart ,tv_email,tv_name,tv_tel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showuser_activity);
        ButterKnife.inject(this);
      //  bmobUser=BmobUser.getCurrentUser(this);
        objectId=getIntent().getStringExtra("objectId");
        initView();
       initToolBar(toolbar,"个人资料",true);
        initData();
    }

    private void initData() {
        if (null!=objectId){
            // objectId =bmobUser.getObjectId();

            BmobQuery<BUser> query= new BmobQuery<>();
            query.getObject(this, objectId, new GetListener<BUser>() {
                @Override
                public void onSuccess(BUser bUser) {
                    if (bUser!=null){
                        iv_userphoto.setTag(bUser.getUserPhoto().getFileUrl(ShowUserActivity.this));
                        new ImageLoader().showImageByThread(iv_userphoto,bUser.getUserPhoto().getFileUrl(ShowUserActivity.this));

                        tv_nick.setText(bUser.getNick());
                        tv_nick.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
                        tv_depart.setText(bUser.getDepart());
                        tv_depart.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
                        tv_email.setText(bUser.getEmail());
                        tv_name.setText(bUser.getUsername());
                        tv_tel.setText(bUser.getMobilePhoneNumber());
                    }
                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }
    }




    private void initView() {
        tv_nick= (TextView) findViewById(R.id.tv_nick);
        tv_depart= (TextView) findViewById(R.id.tv_depart);
        tv_email= (TextView) findViewById(R.id.tv_email);
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_tel= (TextView) findViewById(R.id.tv_tel);
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
            showToast("更新资料暂未开放.");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
