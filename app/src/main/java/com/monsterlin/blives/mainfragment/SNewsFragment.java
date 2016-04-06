package com.monsterlin.blives.mainfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.NewsAdapter;
import com.monsterlin.blives.entity.SchoolNews;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 主内容_学校新闻
 * Created by monsterlin on 2016/4/1.
 */
public class SNewsFragment extends Fragment{


    private Context mContext ;
    private SwipeRefreshLayout srl ;
    private RecyclerView rynews ;

    private NewsAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ProgressWheel  progressWheel ;

    BmobQuery<SchoolNews> query ;
    private List<SchoolNews> mList = new ArrayList<>();

    /**
     * 创建视图，返回View对象
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_snews,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        query= new BmobQuery<SchoolNews>();
        query.order("-newsdate");
       query.setLimit(10);
        query.findObjects(mContext, new FindListener<SchoolNews>() {
            @Override
            public void onSuccess(List<SchoolNews> list) {
                adapter = new NewsAdapter(list,mContext);
                rynews.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
                rynews.setLayoutManager(layoutManager);
            }

            @Override
            public void onError(int i, String s) {
                Log.e("OnError :",s);
            }
        });
    }


    private void initView(View view) {
        srl= (SwipeRefreshLayout) view.findViewById(R.id.srl);
        rynews= (RecyclerView) view.findViewById(R.id.rynews);
        progressWheel= (ProgressWheel) view.findViewById(R.id.progressWheel);


        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
            }
        });


        //滑动监听
        rynews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
