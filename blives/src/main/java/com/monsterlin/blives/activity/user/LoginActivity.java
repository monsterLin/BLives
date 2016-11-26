package com.monsterlin.blives.activity.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.MainActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.monsterlin.blives.biz.BaseUiListener;
import com.monsterlin.blives.constants.APPID;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.OtherLoginListener;
import cn.bmob.v3.listener.UpdateListener;
import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 登陆界面
 * Created by monsterLin on 2016/4/4.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.edt_mail)
    EditText edt_mail;

    @InjectView(R.id.edt_pass)
    EditText edt_pass;

    @InjectView(R.id.btn_regist)
    Button btn_regist;

    @InjectView(R.id.btn_login)
    Button btn_login;


    @InjectView(R.id.iv_qq_login)
    ImageView iv_qq_login;

    @InjectView(R.id.iv_wx_login)
    ImageView iv_wx_login;

    @InjectView(R.id.iv_sina_login)
    ImageView iv_sina_login;

    private String mailString, passString;

    private AlertDialog dialog;

    private Tencent mTencent;

    private IUiListener listener;

    private String openid, access_token, expires;

    private String userInfoUrl;
    private static final int SUCCEED = 0x0001;

    private String nickname, figureurl;


    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            switch (msg.what) {
                case SUCCEED:

                    //这时候由于使用了BmobAuth方法，所有现在在表中已经存在了用户的一行数据
                    //但是这时候的数据仅仅有objectId ， AuthData ，createDate updateDate信息
                    //我们需要进行完善，这个时候还有一些数据没有，我们现在人为添加些随机数据，头像和昵称除外
                    //用户在点击个人资料的时候可以进行修改资料

                    final String objectId = BmobUser.getCurrentUser(LoginActivity.this).getObjectId();

                    BmobQuery<BUser> query = new BmobQuery<>();
                    query.getObject(LoginActivity.this, objectId, new GetListener<BUser>() {
                        @Override
                        public void onSuccess(BUser bUser) {
                            if (!TextUtils.isEmpty(bUser.getFigureurl())) {
                                //如果figureurl不为空，则表示用户信息已经注册好
                                finish();
                                nextActivity(MainActivity.class);

                            } else {
                                final Bundle bundle = (Bundle) msg.obj;

                                BUser bUser1 = new BUser();
                                bUser1.setNick(bundle.getString("nickname"));
                                bUser1.setDepart("滨州学院某系院");
                                bUser1.setFigureurl(bundle.getString("figureurl"));
                           //     bUser1.setMobilePhoneNumber("15762180001"); //TODO
                             //   bUser1.setEmail("xxxxx@xxx.com"); //TODO
                                bUser1.update(LoginActivity.this, objectId, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        finish();
                                        nextActivity(MainActivity.class);
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        Log.e("Update_Failure", "Failure..." + s);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Log.e("FAILURE", "获取数据异常：" + s);
                        }
                    });


                    break;
            }

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new SpotsDialog(this);
        ButterKnife.inject(this);
        toolbar.setTitle("登陆");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity(MainActivity.class);
                finish();
            }
        });

        initEvent();
    }


    private void initEvent() {
        btn_login.setOnClickListener(this);
        btn_regist.setOnClickListener(this);

        iv_qq_login.setOnClickListener(this);
        iv_wx_login.setOnClickListener(this);
        iv_sina_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_regist:
                nextActivity(RegistActivity.class);
                finish();
                break;

            case R.id.iv_qq_login:
                qqLogin();
                break;
            case R.id.iv_wx_login:
                showToast("攻城狮们正在研发中...");
                break;
            case R.id.iv_sina_login:
                showToast("攻城狮们正在研发中...");
                break;
        }
    }

    /**
     * QQ登陆
     */
    private void qqLogin() {
        mTencent = Tencent.createInstance(APPID.QQ_APPID, this.getApplicationContext()); //实例化对象
        if (!mTencent.isSessionValid()) {
            listener = new BaseUiListener() {
                @Override
                public void onCancel() {
                    Log.e("OnCalcel", "OnCancel");
                }

                @Override
                public void onError(UiError error) {
                    Log.e("ERROR", "" + error.errorMessage);
                }

                @Override
                public void onComplete(Object object) {
                    JSONObject json = (JSONObject) object;

                    try {
                        openid = json.getString("openid");
                        access_token = json.getString("access_token");
                        expires = json.getString("expires_in");


                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, access_token, expires, openid);
                        loginWithAuth(authInfo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            mTencent.login(this, "all", listener);

        }
    }

    private void loginWithAuth(BmobUser.BmobThirdUserAuth authInfo) {
        BmobUser.loginWithAuthData(LoginActivity.this, authInfo, new OtherLoginListener() {
            @Override
            public void onSuccess(JSONObject userAuth) {
                //TODO   用户进行QQ登陆后，登陆结束后并且与bmob进行绑定，那么用户的头像和用户的昵称被自己补充，其他数据
                //TODO    系统自己书写测试数据，在用户查看资料的时候，用户可以得到更改资料的权限，这个时候为用户手动更改

                //TODO 执行getDATA
                userInfoUrl = "http://119.147.19.43/v3/user/get_info?openid=" + openid + "&openkey=" + access_token + "&pf=qzone&appid=" + APPID.QQ_APPID + "&format=json&userip=10.0.0.1&sig=v7jJNKrJFMX%2Flh6%2BevElT%2BRME3c%3D";


                OkHttpClient mOkHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(userInfoUrl)
                        .build();

                Call call = mOkHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Exception", "" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String jsonString = response.body().string();

                        Log.e("JsonString", jsonString);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonString);
                            nickname = jsonObject.getString("nickname");
                            figureurl = jsonObject.getString("figureurl");


                            Bundle bundle = new Bundle();

                            bundle.putString("nickname", nickname);
                            bundle.putString("figureurl", figureurl);

                            mHandle.obtainMessage(SUCCEED, bundle).sendToTarget();

                            //TODO 这个地方为子线程，我需要去主线程去更新资料

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


//                Intent intent = new Intent(LoginActivity.this, QQRegistActivity.class);
//                intent.putExtra("openid", openid);
//                intent.putExtra("access_token", access_token);
//                startActivity(intent);
//                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("第三方登陆失败：" + s);
            }
        });
    }


    /**
     * 必须加入onActivityResult否则程序将不会出现回调信息
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
    }

    /**
     * 用户登录
     */
    private void login() {
        dialog.show();
        mailString = edt_mail.getText().toString();
        passString = edt_pass.getText().toString();


        if (TextUtils.isEmpty(mailString) && TextUtils.isEmpty(passString)) {
            dialog.dismiss();
            showToast("邮箱或密码未填写");
        } else {
            //邮箱进行正则表达式匹配
            if (mailString.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?")) {

                BmobUser.loginByAccount(LoginActivity.this, mailString, passString, new LogInListener<BUser>() {
                    @Override
                    public void done(BUser bUser, BmobException e) {
                        if (bUser != null) {
                            showToast("登录成功");
                            dialog.dismiss();
                            finish();
                            nextActivity(MainActivity.class);
                        } else {
                            showToast("邮箱或者密码不正确");
                            dialog.dismiss();
                        }
                    }
                });

            } else {
                showToast("邮箱格式错误");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_forget) {
            Intent resetIntent = new Intent(LoginActivity.this, ResetActivity.class);
            startActivity(resetIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


}
