package com.monsterlin.blives.activity.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.monsterlin.blives.constants.APPID;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import monster.org.validator.validator.Form;
import monster.org.validator.validator.Validate;
import monster.org.validator.validator.validator.EmailValidator;
import monster.org.validator.validator.validator.NotEmptyValidator;
import monster.org.validator.validator.validator.PhoneValidator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by monsterLin on 7/6/2016.
 */
public class QQRegistActivity extends BaseActivity implements View.OnClickListener {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.iv_userphoto)
    CircleImageView iv_userphoto;

    private EditText edt_mail, edt_name, edt_depart, edt_tel, edt_nick;

    @InjectView(R.id.btn_regist)
    Button btn_regist;

    private Form form = new Form();//用于实现表单的验证

    private String access_token;
    private String openid;


    private String userInfoUrl;

    private String nickname, figureurl;

    private static final int PICK_CODE = 1; //请求码
    private String mCurrentPhotoStr;  //当前图片的路径
    private Bitmap mPhotoImg;

    private static final int NICKNAME = 0;
    private static final int FIGUREURL = 1;

    private String currentUserObjectId;
    private AlertDialog dialog ;


    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {


            if (msg.what == NICKNAME) {

                //TODO 无log打印，可能是虚拟机问题
                //TODO 测试
                Log.e("NICKNAME",""+(String) msg.obj);
                edt_nick.setText((String) msg.obj);
            }
            if (msg.what == FIGUREURL) {
                mCurrentPhotoStr = figureurl;
                Log.e("mCurrentPhotoStr",""+(String) msg.obj);
                ImageLoader.getInstance().displayImage((String) msg.obj, iv_userphoto);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_regist);
        ButterKnife.inject(this);
        initView();
        initData();
        initToolBar(toolbar, "资料完善", true);
        initEvent();
        initForm();
    }

    /**
     * 初始化用户数据
     */
    private void initData() {
        access_token = getIntent().getStringExtra("access_token");
        openid = getIntent().getStringExtra("openid");

        userInfoUrl = "http://119.147.19.43/v3/user/get_info?openid=" + openid + "&openkey=" + access_token + "&pf=qzone&appid=" + APPID.QQ_APPID + "&format=json&userip=10.0.0.1&sig=v7jJNKrJFMX%2Flh6%2BevElT%2BRME3c%3D";

        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(userInfoUrl)
                .build();

        Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Exception",""+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                Log.e("JsonString",jsonString);
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    nickname = jsonObject.getString("nickname");
                    figureurl = jsonObject.getString("figureurl");

                    mHandle.obtainMessage(NICKNAME, nickname).sendToTarget();
                    mHandle.obtainMessage(FIGUREURL, figureurl).sendToTarget();

                    //TODO 这个地方为子线程，我需要去主线程去更新资料

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }


    private void initEvent() {
        btn_regist.setOnClickListener(this);
        iv_userphoto.setOnClickListener(this);
    }

    private void initForm() {
        NotEmptyValidator notEmptyValidator = new NotEmptyValidator(this);  //非空验证
        EmailValidator emailValidator = new EmailValidator(this);  //邮箱验证
        PhoneValidator phoneValidator = new PhoneValidator(this);  //手机号验证

        Validate mail = new Validate(edt_mail);
        Validate name = new Validate(edt_name);
        Validate depart = new Validate(edt_depart);
        Validate tel = new Validate(edt_tel);
        Validate nick = new Validate(edt_nick);


        mail.addValidator(notEmptyValidator);
        mail.addValidator(emailValidator);
        name.addValidator(notEmptyValidator);
        depart.addValidator(notEmptyValidator);
        tel.addValidator(notEmptyValidator);
        tel.addValidator(phoneValidator);
        nick.addValidator(notEmptyValidator);

        form.addValidates(mail);
        form.addValidates(name);
        form.addValidates(depart);
        form.addValidates(tel);
        form.addValidates(nick);
    }

    private void initView() {
        edt_mail = (EditText) findViewById(R.id.edt_mail);
        edt_name = (EditText) findViewById(R.id.edt_name);

        edt_nick = (EditText) findViewById(R.id.edt_nick);
        edt_depart = (EditText) findViewById(R.id.edt_depart);
        edt_tel = (EditText) findViewById(R.id.edt_tel);
        btn_regist = (Button) findViewById(R.id.btn_regist);

        dialog=new SpotsDialog(QQRegistActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_regist:
                dialog.show();
             //   Log.e("mCurrentPhotoStr",""+mCurrentPhotoStr);
                Boolean isOK = form.validate(); //得到验证结果
                if (isOK) {
                    //如果数据都有值的话
                    final String mailString = edt_mail.getText().toString();
                    final String nameString = edt_name.getText().toString();
                    final String nickString = edt_nick.getText().toString();
                    final String departString = edt_depart.getText().toString();
                    final String telString = edt_tel.getText().toString();


                    if (!TextUtils.isEmpty(mCurrentPhotoStr)) {
                        //图片不为空
                        final BmobFile bmobFile = new BmobFile(new File(mCurrentPhotoStr));

                        bmobFile.upload(QQRegistActivity.this, new UploadFileListener() {
                            @Override
                            public void onSuccess() {
                                //完善资料，在这个方法中头像已经上传完毕，需要更新数据
                                currentUserObjectId = BmobUser.getCurrentUser(QQRegistActivity.this).getObjectId();
                                BUser bUser = new BUser();
                                bUser.setNick(nickString);
                                bUser.setUserPhoto(bmobFile);
                                bUser.setDepart(departString);
                                bUser.setEmail(mailString);
                                bUser.setUsername(nameString);
                                bUser.setMobilePhoneNumber(telString);

                                bUser.update(QQRegistActivity.this, currentUserObjectId, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        dialog.dismiss();
                                        showToast("更新成功");
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        dialog.dismiss();
                                        showToast("更新资料失败：" + s);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                dialog.dismiss();
                                showToast("上传图片失败"+s);
                            }
                        });
                    } else {
                        //图片为空
                        dialog.dismiss();
                        showToast("读取图片路径异常");
                    }
                } else {
                    dialog.dismiss();
                    showToast("请完整输入信息");
                }
                break;
            case R.id.iv_userphoto:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");  //设置数据类型
                startActivityForResult(intent, PICK_CODE);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PICK_CODE) {
            if (intent != null) {
                Uri uri = intent.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst(); //将游标移到First

                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                mCurrentPhotoStr = cursor.getString(idx); //得到图片的路径

                cursor.close(); //游标关闭

                residePhoto();  //压缩照片

                iv_userphoto.setImageBitmap(mPhotoImg);

            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }


    /**
     * 此方法用于压缩图片
     */
    private void residePhoto() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果设置为true，解码器将返回null（没有位图），但出…领域仍将设置，允许调用者查询位图不用其像素分配内存。
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(mCurrentPhotoStr, options); //将解码文件转换成位图
        double radio = Math.max(options.outWidth * 1.0d / 1024f, options.outHeight * 1.0d / 1024f);
        //如果设置的值大于1，要求解码器子样的原始图像，返回一个较小的图像保存记忆。 -->>来自官网帮助文档的解析
        options.inSampleSize = (int) Math.ceil(radio);
        options.inJustDecodeBounds = false;
        mPhotoImg = BitmapFactory.decodeFile(mCurrentPhotoStr, options); //压缩过后的位图
    }

}