package com.monsterlin.blives.navfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.app.AppDetailActivity;
import com.monsterlin.blives.adapter.AppAdapter;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.bean.App;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monsterLin on 6/27/2016.
 */
public class AppFragment extends Fragment {

    private Context mContext ;

    private RecyclerView rv_app;
    private AppAdapter adapter;
    private List<App> mList ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app,container,false);
        initData();
        initView(view);
        initAdapter();
        return view;
    }

    private void initAdapter() {
        adapter=new AppAdapter(mContext,mList);
        rv_app.setAdapter(adapter);
        rv_app.setLayoutManager(new GridLayoutManager(mContext,3));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {


                Intent appIntent = new Intent(mContext,AppDetailActivity.class);
                appIntent.putExtra("appUrl",adapter.getApp(position).getAppurl());
                appIntent.putExtra("appName",adapter.getApp(position).getAppname());
                startActivity(appIntent);

            }

            @Override
            public void OnItemLongClick(int position, View view) {

            }
        });

    }

    private void initData() {
        mList=new ArrayList<>();
        mList.add(new App(R.mipmap.ic_launcher,"滨州学院","http://www.bzu.edu.cn/"));
        mList.add(new App(R.drawable.ic_app_wx,"微信首页","http://weixin.bzu.edu.cn/html/xxgk/index.htm"));
        mList.add(new App(R.drawable.ic_app_inform,"通知公告","http://weixin.bzu.edu.cn/wp/?cat=10"));
        mList.add(new App(R.drawable.ic_app_cet,"四六级","http://weixin.bzu.edu.cn/html/cet"));
        mList.add(new App(R.drawable.ic_app_onekey,"一卡通","http://m.xzxyun.com/download/main"));
        mList.add(new App(R.drawable.ic_app_jobs,"招生就业","http://weixin.bzu.edu.cn/wp/?cat=11"));
        mList.add(new App(R.drawable.ic_app_borrow,"借阅","http://10.9.10.3/dzjs/login_form.asp"));
        mList.add(new App(R.drawable.ic_app_sight,"校园风光","http://weixin.bzu.edu.cn/wp/?cat=12"));
        mList.add(new App(R.drawable.ic_app_cz,"院系传真","http://weixin.bzu.edu.cn/wp/?cat=13"));
        mList.add(new App(R.drawable.ic_app_board,"留言板","http://weixin.bzu.edu.cn/wp/?page_id=52"));
        mList.add(new App(R.drawable.ic_app_ts,"师生风采","http://weixin.bzu.edu.cn/wp/?cat=14"));
        mList.add(new App(R.drawable.ic_app_topics,"互动话题","http://weixin.bzu.edu.cn/wp/?cat=17"));
        mList.add(new App(R.drawable.ic_app_about,"关于","file:///android_asset/web/showwx.html"));
    }

    private void initView(View view) {
        rv_app= (RecyclerView) view.findViewById(R.id.rv_app);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
