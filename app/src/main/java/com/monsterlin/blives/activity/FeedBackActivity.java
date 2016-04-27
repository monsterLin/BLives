package com.monsterlin.blives.activity;

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

import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 用户反馈
 * Created by monsterLin on 2016/4/12.
 */
public class FeedBackActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
     Toolbar toolbar;

    @InjectView(R.id.edt_email)
     MaterialEditText edt_email ;

    @InjectView(R.id.edt_feedback)
    MaterialEditText edt_feedback;

    @InjectView(R.id.tv_author)
     TextView tv_author ;

    @InjectView(R.id.tv_qq)
    TextView tv_qq;

    @InjectView(R.id.iv_send)
    ImageView iv_send;

    @InjectView(R.id.iv_img)
    ImageView iv_img;

    private BmobUser bmobUser;

    private String email ; //邮箱

    public Build bd;

    public TelephonyManager tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initActivityButterKnife(this);
        bd = new Build();
        tm = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        initToolBar(toolbar,"用户反馈",true);
        initData();
        initEvent();
    }

    /**
     * 初始化数据源
     * 如果用户已经登录了，则从服务端获得邮箱数据
     * 如果用户没有登录，则需要手动输入邮箱
     */
    private void initData() {

        tv_author.setText("monster");
        tv_qq.setText("876948462");


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
            if (email.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")){
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
            }else {
                showToast("邮箱格式错误");
            }

        }
    }

}

