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
 *生活界面
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
                bundle.putString("life_name",adapter.getLife(position).getLifename());
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
        mList.add(new Life(R.mipmap.ic_launcher,"滨州学院","http://www.bzu.edu.cn/"));
        mList.add(new Life(R.mipmap.ic_launcher,"微信首页","http://weixin.bzu.edu.cn/html/xxgk/index.htm"));
        mList.add(new Life(R.mipmap.ic_launcher,"通知公告","http://weixin.bzu.edu.cn/wp/?cat=10"));
        mList.add(new Life(R.mipmap.ic_launcher,"四六级","http://weixin.bzu.edu.cn/html/cet"));
        mList.add(new Life(R.mipmap.ic_launcher,"一卡通","http://m.xzxyun.com/download/main"));
        mList.add(new Life(R.mipmap.ic_launcher,"招生就业","http://weixin.bzu.edu.cn/wp/?cat=11"));
        mList.add(new Life(R.mipmap.ic_launcher,"借阅","http://10.9.10.3/dzjs/login_form.asp"));
        mList.add(new Life(R.mipmap.ic_launcher,"校园风光","http://weixin.bzu.edu.cn/wp/?cat=12"));
        mList.add(new Life(R.mipmap.ic_launcher,"院系传真","http://weixin.bzu.edu.cn/wp/?cat=13"));
        mList.add(new Life(R.mipmap.ic_launcher,"留言板","http://weixin.bzu.edu.cn/wp/?page_id=52"));
        mList.add(new Life(R.mipmap.ic_launcher,"师生风采","http://weixin.bzu.edu.cn/wp/?cat=14"));
        mList.add(new Life(R.mipmap.ic_launcher,"互动话题","http://weixin.bzu.edu.cn/wp/?cat=17"));
        mList.add(new Life(R.mipmap.ic_launcher,"关于","file:///android_asset/web/showwx.html"));
    }



}