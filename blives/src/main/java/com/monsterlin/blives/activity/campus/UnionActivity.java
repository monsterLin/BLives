package com.monsterlin.blives.activity.campus;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.Union;
import com.monsterlin.blives.utils.PhoneUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import dmax.dialog.SpotsDialog;

/**
 * Created by monsterLin on 6/30/2016.
 */
public class UnionActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tv_director)
    TextView tv_director ;

    @InjectView(R.id.tv_from)
    TextView tv_from;

    @InjectView(R.id.tv_type)
    TextView tv_type;

    @InjectView(R.id.tv_introbuce)
    TextView tv_introbuce ;

    @InjectView(R.id.tv_join)
    TextView tv_join;

    private String objectId ;

    private BmobQuery<Union> query;

    private AlertDialog alertDialog;

    private String tel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_union);
        ButterKnife.inject(this);
        objectId=getIntent().getStringExtra("objectId");
        alertDialog=new SpotsDialog(this);
        initData();
    }

    private void initData() {
        alertDialog.show();
        query= new BmobQuery<>();
        query.getObject(UnionActivity.this, objectId, new GetListener<Union>() {
            @Override
            public void onSuccess(Union union) {
                init(union);
            }

            @Override
            public void onFailure(int i, String s) {
                alertDialog.dismiss();
                showToast("发生异常："+s);
            }
        });
    }

    private void init(Union union) {
        initToolBar(toolbar,true);
        toolbar.setTitle(union.getCommunity());
        tv_director.setText("理事长："+union.getDirector());
        tv_from.setText("挂靠单位："+union.getFrom());
        tv_type.setText(union.getType());
        tv_introbuce.setText(union.getIntrobuce());
        tv_join.setText(union.getJoin());
        tel=union.getDirectortel();
        alertDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_union, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_tel) {
            PhoneUtil.TelCALL(this,tel);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
