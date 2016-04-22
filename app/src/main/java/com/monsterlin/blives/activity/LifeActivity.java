package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.LifeAdapter;
import com.monsterlin.blives.entity.Life;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 主题选择界面
 * Created by monsterLin on 2016/4/14.
 */
public class LifeActivity extends BaseActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rv_life)
     RecyclerView rv_life ;


    private LifeAdapter adapter;
    private List<Life> mList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        initActivityButterKnife(this);
        initToolBar(toolbar,"生活应用",true);
        initData();
        initAdapter();
    }

    private void initAdapter() {
        adapter=new LifeAdapter(this,mList);
        rv_life.setAdapter(adapter);
        rv_life.setLayoutManager(new GridLayoutManager(this,3));

        adapter.setOnItemClickListener(new LifeAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
               Bundle bundle=new Bundle();
                bundle.putString("app_url",adapter.getLife(position).getApp_url());
                nextActivity(LifeDetailActivity.class,bundle);
            }

            @Override
            public void OnItemLongClick(int position, View view) {

            }
        });
    }

    /**
     * TODO 最初版本数据为固定状态
     * TODO 后续版本更新
     */
    private void initData() {
        mList=new ArrayList<>();
        for (int i =0;i<8;i++){
            mList.add(new Life(R.mipmap.ic_launcher,"bzu","http://www.baidu.com"));
        }

    }


}
