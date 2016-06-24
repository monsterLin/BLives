package com.monsterlin.blives.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.BUser;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import monster.org.validator.validator.Form;
import monster.org.validator.validator.Validate;
import monster.org.validator.validator.validator.EmailValidator;
import monster.org.validator.validator.validator.NotEmptyValidator;
import monster.org.validator.validator.validator.PhoneValidator;

/**
 * 注册
 * Created by monsterLin on 2016/4/4.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener{

    /**
     * TODO 用户交互需要加强，进度条需要使用
     */

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.iv_userphoto)
    CircleImageView iv_userphoto;

    private EditText edt_mail ,edt_pass ,edt_name ,edt_depart , edt_tel,edt_nick;

    @InjectView(R.id.btn_regist)
    Button btn_regist;

    private static final int PICK_CODE = 1; //请求码
    private String mCurrentPhotoStr;  //当前图片的路径  --->上传图片所用到的数据
    private Bitmap mPhotoImg;

    Form form = new Form();//用于实现表单的验证


    private String mailString , passString ,nameString ,departString,telString ,nickString ;


    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        dialog=new SpotsDialog(this);
        ButterKnife.inject(this);
        initView();
        initToolBar(toolbar,"注册",true);
        initEvent();
        initForm();
    }

    /**
     * 注册信息验证
     */
    private void initForm() {

        NotEmptyValidator notEmptyValidator=new NotEmptyValidator(this);  //非空验证
        EmailValidator emailValidator=new EmailValidator(this);  //邮箱验证
        PhoneValidator phoneValidator = new PhoneValidator(this);  //手机号验证

        Validate mail=new Validate(edt_mail);
        Validate pass=new Validate(edt_pass);
        Validate name=new Validate(edt_name);
        Validate depart=new Validate(edt_depart);
        Validate tel=new Validate(edt_tel);
        Validate nick = new Validate(edt_nick);


        mail.addValidator(notEmptyValidator);
        mail.addValidator(emailValidator);
        pass.addValidator(notEmptyValidator);
        name.addValidator(notEmptyValidator);
        depart.addValidator(notEmptyValidator);
        tel.addValidator(notEmptyValidator);
        tel.addValidator(phoneValidator);
        nick.addValidator(notEmptyValidator);

        form.addValidates(mail);
        form.addValidates(pass);
        form.addValidates(name);
        form.addValidates(depart);
        form.addValidates(tel);
        form.addValidates(nick);


    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        btn_regist.setOnClickListener(this);
        iv_userphoto.setOnClickListener(this);
    }



    private void initView() {

        edt_mail= (EditText) findViewById(R.id.edt_mail);
        edt_pass= (EditText) findViewById(R.id.edt_pass);
        edt_name= (EditText) findViewById(R.id.edt_name);

        //TODO  系院为选取，不应该输入
        edt_nick= (EditText) findViewById(R.id.edt_nick);
        edt_depart= (EditText) findViewById(R.id.edt_depart);
        edt_tel= (EditText) findViewById(R.id.edt_tel);
        btn_regist= (Button) findViewById(R.id.btn_regist);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regist:
                Boolean isOK=form.validate(); //得到验证结果
                if(isOK){
                    //验证成功
                    dialog.show();
                    mailString =edt_mail.getText().toString();
                    passString =edt_pass.getText().toString();
                    nameString =edt_name.getText().toString();
                    nickString =edt_nick.getText().toString();
                    departString =edt_depart.getText().toString();
                    telString =edt_tel.getText().toString();

                    if(mCurrentPhotoStr==null){
                        dialog.dismiss();
                        showToast("头像未选取");
                    }else {
                        if (!containsChinese(passString)&&passString.length()>6){
                            final BmobFile file=new BmobFile(new File(mCurrentPhotoStr));//将图片路径转为BmobFile
                            file.upload(RegistActivity.this, new UploadFileListener() {
                                @Override
                                public void onSuccess() {
                                    upData(file);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    dialog.dismiss();
                                    showToast("头像上传失败："+s);
                                }
                            });
                        }else {
                            dialog.dismiss();
                            showToast("密码不规范");
                        }


                    }

                }else {
                    //验证失败
                    showToast("请正确填写信息");
                }
                break;
            case R.id.iv_userphoto:
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");  //设置数据类型
                startActivityForResult(intent, PICK_CODE);
                break;
        }
    }

    public static boolean containsChinese(String s){
        if (null == s || "".equals(s.trim())) return false;
        for (int i = 0; i < s.length(); i++) {
            if (isChinese(s.charAt(i)))
                return true;
        }
        return false;
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    private void upData(BmobFile file) {
        final BUser bUser =new BUser();
        bUser.setEmail(mailString);
        bUser.setPassword(passString);
        bUser.setUsername(nameString);
        bUser.setNick(nickString);
        bUser.setDepart(departString);
        bUser.setMobilePhoneNumber(telString);
        bUser.setUserPhoto(file);


        bUser.signUp(RegistActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
               dialog.dismiss();
                showToast("注册成功，验证邮件已发送到你的邮箱，请注意查收");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
              dialog.dismiss();
                showToast("发生异常："+s);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode==PICK_CODE){
            if(intent!=null){
                Uri uri=intent.getData();
                Cursor cursor= getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst(); //将游标移到First

                int idx=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                mCurrentPhotoStr=cursor.getString(idx); //得到图片的路径

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
        BitmapFactory.Options options=new BitmapFactory.Options();
        //如果设置为true，解码器将返回null（没有位图），但出…领域仍将设置，允许调用者查询位图不用其像素分配内存。
        options.inJustDecodeBounds=true;

        BitmapFactory.decodeFile(mCurrentPhotoStr,options); //将解码文件转换成位图
        double radio=Math.max(options.outWidth * 1.0d/1024f,options.outHeight *1.0d/1024f);
        //如果设置的值大于1，要求解码器子样的原始图像，返回一个较小的图像保存记忆。 -->>来自官网帮助文档的解析
        options.inSampleSize= (int) Math.ceil(radio);
        options.inJustDecodeBounds=false;
        mPhotoImg=BitmapFactory.decodeFile(mCurrentPhotoStr,options); //压缩过后的位图
    }



}
