package com.monsterlin.blives.activity.campus;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.Campus;
import com.monsterlin.blives.utils.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 活动页面详情
 * Created by monsterLin on 2016/4/25.
 */
public class CampusDetailActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.iv_userphoto)
    CircleImageView iv_userphoto;

    @InjectView(R.id.tv_nick)
    TextView tv_nick;

    @InjectView(R.id.tv_date)
    TextView tv_date;

    @InjectView(R.id.tv_content)
    TextView tv_content ;

    @InjectView(R.id.tv_campus_date)
    TextView tv_campus_date;

    @InjectView(R.id.tv_campus_place)
    TextView tv_campus_place;

    @InjectView(R.id.tv_campus_type)
    TextView tv_campus_type;

    @InjectView(R.id.tv_campus_object)
    TextView tv_campus_object;

    private String objectId;  //活动的id

    private String userObjectId;

    private BmobQuery<Campus> query ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcampus);
       ButterKnife.inject(this);
        objectId = getIntent().getStringExtra("objectId");
        userObjectId=getIntent().getStringExtra("userObjectId");
        query=new BmobQuery<>();
        query.include("bUser");
        query.getObject(this, objectId, new GetListener<Campus>() {
            @Override
            public void onSuccess(Campus campus) {
                initToolBar(toolbar,true);
                toolbar.setTitle(campus.getTitle());

                iv_userphoto.setTag(campus.getbUser().getUserPhoto().getFileUrl(CampusDetailActivity.this));
                new ImageLoader().showImageByAsyncTask(iv_userphoto,campus.getbUser().getUserPhoto().getFileUrl(CampusDetailActivity.this));

                tv_nick.setText(campus.getbUser().getNick());
                tv_date.setText(campus.getUpdatedAt());

                tv_content.setText(campus.getContent());

                tv_campus_date.setText(campus.getCampusDate());
                tv_campus_object.setText(campus.getCampusObject());
                tv_campus_place.setText(campus.getCampusPlace());
                tv_campus_type.setText(campus.getCampusType());

            }

            @Override
            public void onFailure(int i, String s) {
                showToast("读取数据异常："+s);
            }
        });


        iv_userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent showIntent = new Intent(CampusDetailActivity.this,ShowUserActivity.class);
//                showIntent.putExtra("objectId",userObjectId);
//                startActivity(showIntent);
            }
        });
    }
}
