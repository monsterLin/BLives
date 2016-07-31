package com.monsterlin.blives.activity.campus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BUser;
import com.monsterlin.blives.bean.Campus;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import monster.org.validator.validator.Form;
import monster.org.validator.validator.Validate;
import monster.org.validator.validator.validator.NotEmptyValidator;

/**
 * 发布活动
 * Created by monsterLin on 2016/4/25.
 */
public class NCampusActivity extends BaseActivity {

  @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private MaterialEditText edt_title ,edt_content ,edt_date ,edt_place , edt_object,edt_type;

    @InjectView(R.id.btn_public)
    Button btn_public;

    Form form = new Form();//用于实现表单的验证

    private String titleString , contentString ,dateString ,placeString,objectString ,typeString ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ncampus);
        ButterKnife.inject(this);
        initToolBar(toolbar,"发布新活动",true);
        initView();
        initForm();
        initEvent();
    }

    private void initEvent() {
        btn_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isOK=form.validate(); //得到验证结果
                if (isOK){
                    //成功
                    titleString=edt_title.getText().toString();
                    contentString=edt_content.getText().toString();
                    dateString=edt_date.getText().toString();
                    placeString=edt_place.getText().toString();
                    objectString=edt_object.getText().toString();
                    typeString=edt_type.getText().toString();


                    final Campus campus=new Campus();
                    campus.setTitle(titleString);
                    campus.setContent(contentString);
                    campus.setCampusDate(dateString);
                    campus.setCampusPlace(placeString);
                    campus.setCampusType(typeString);
                    campus.setCampusObject(objectString);

                    BUser bUser= BmobUser.getCurrentUser(NCampusActivity.this,BUser.class);
                    campus.setbUser(bUser);

                    campus.save(NCampusActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            showToast("发布成功");

                            /**发送广播**/
                            Intent intent = new Intent();
                            intent.setAction("com.monster.broadcast");
                            intent.putExtra("newcampus",campus);
                            sendBroadcast(intent);

                            finish();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("发布失败："+s);
                        }
                    });


                }else {
                    //失败
                    showToast("请完整填写活动信息");
                }
            }
        });
    }

    private void initForm() {
        NotEmptyValidator notEmptyValidator=new NotEmptyValidator(this);  //非空验证

        Validate title=new Validate(edt_title);
        Validate content=new Validate(edt_content);
        Validate date=new Validate(edt_date);
        Validate place=new Validate(edt_place);
        Validate object=new Validate(edt_object);
        Validate type=new Validate(edt_type);


        title.addValidator(notEmptyValidator);
        content.addValidator(notEmptyValidator);
        date.addValidator(notEmptyValidator);
        place.addValidator(notEmptyValidator);
        object.addValidator(notEmptyValidator);
        type.addValidator(notEmptyValidator);

        form.addValidates(title);
        form.addValidates(content);
        form.addValidates(date);
        form.addValidates(place);
        form.addValidates(object);
        form.addValidates(type);
    }

    private void initView() {
        edt_title= (MaterialEditText) findViewById(R.id.edt_title);
        edt_content= (MaterialEditText) findViewById(R.id.edt_content);
        edt_date= (MaterialEditText) findViewById(R.id.edt_date);
        edt_place= (MaterialEditText) findViewById(R.id.edt_place);
        edt_object= (MaterialEditText) findViewById(R.id.edt_object);
        edt_type= (MaterialEditText) findViewById(R.id.edt_type);
    }
}
