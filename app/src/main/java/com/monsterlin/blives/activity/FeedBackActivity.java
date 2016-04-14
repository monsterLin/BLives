package com.monsterlin.blives.activity;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.BUser;
import com.monsterlin.blives.entity.FeedBack;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 用户反馈
 * Created by monsterLin on 2016/4/12.
 */
public class FeedBackActivity extends BaseActivity {

    private Toolbar toolbar;
    private MaterialEditText edt_email ,edt_feedback;

    private TextView tv_author , tv_qq;

    private ImageView  iv_send ,iv_img ;

    private BmobUser bmobUser;

    private String email ; //邮箱

    public Build bd;
    public TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        bd = new Build();
        tm = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        initView();
        initToolBar();
        initData();
        initEvent();
    }

    /**
     * 初始化数据源
     * 如果用户已经登录了，则从服务端获得邮箱数据
     * 如果用户没有登录，则需要手动输入邮箱
     */
    private void initData() {
        bmobUser =  BmobUser.getCurrentUser(this);
        if (bmobUser!=null){
            BmobQuery<BUser> query = new BmobQuery<>();
            String objectId = bmobUser.getObjectId();
            query.getObject(FeedBackActivity.this, objectId, new GetListener<BUser>() {
                @Override
                public void onSuccess(BUser bUser) {
                    email=bUser.getEmail();
                    edt_email.setText(email);
                    edt_email.setEnabled(false);
                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("获取邮箱异常："+s);
                }
            });
        }
    }

    private void initEvent() {
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   sendFeedBack();
            }
        });

        iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("点什么点，说你的~╰(￣▽￣)╭");
            }
        });
    }

    /**
     * 发送反馈信息
     */
    private void sendFeedBack() {

         String deviceType = bd.MODEL;
         String IMEI = tm.getDeviceId();
         String   email = edt_email.getText().toString();
         String feedContent=edt_feedback.getText().toString();


        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(feedContent)){
            showToast("请正确填写信息");
        }else {
            FeedBack feedBack = new FeedBack();
            feedBack.setDevice(deviceType);
            feedBack.setIMEI(IMEI);
            feedBack.setEmail(email);
            feedBack.setFeedContent(feedContent);

            feedBack.save(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    showToast("谢谢你的反馈");
                    finish();
                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("提交反馈发生异常："+s);
                }
            });
        }
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        edt_email= (MaterialEditText) findViewById(R.id.edt_email);
        edt_feedback= (MaterialEditText) findViewById(R.id.edt_feedback);
        tv_author= (TextView) findViewById(R.id.tv_author);
        tv_qq= (TextView) findViewById(R.id.tv_qq);
        iv_send= (ImageView) findViewById(R.id.iv_send);
        iv_img= (ImageView) findViewById(R.id.iv_img);

        tv_author.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));
        tv_qq.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));

        tv_author.setText("monster");
        tv_qq.setText("876948462");

    }

    private void initToolBar() {
        toolbar.setTitle("用户反馈");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //出现返回箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

