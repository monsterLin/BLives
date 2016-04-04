package com.monsterlin.blives.mainfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.DetailsActivity;
import com.monsterlin.blives.adapter.NewsAdapter;
import com.monsterlin.blives.constants.SchoolURL;
import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.presenter.ParseBZUWeb;
import com.monsterlin.blives.presenter.impl.ParseBZUWebImpl;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 主内容_学校新闻
 * Created by monsterlin on 2016/4/1.
 */
public class SNewsFragment extends Fragment{

    private RecyclerView ry_schoolNews;
    private Context mContext;
    private LinearLayoutManager layoutManager;
    private List<SchoolNews> schoolNewsList = new ArrayList<>();
    private ParseBZUWeb bzuData = new ParseBZUWebImpl();
    private NewsAdapter newsAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressWheel progressWheel ;

    private Thread mThread;
    private final static int MSG_SUCCESS = 0; //成功拿到数据的标识
    private final static int MSG_FAILURE = 1; //无法拿到数据的标识

    private List<SchoolNews> mList = new ArrayList<>();

    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg){  //此方法在UI线程中运行
            switch(msg.what){
                case MSG_SUCCESS:
                   mList= (List<SchoolNews>) msg.obj;
                    initUI(mList);
                    break;
                case MSG_FAILURE:
                    break;
            }
        }

    };

    private void initUI(List<SchoolNews> list) {
        newsAdapter = new NewsAdapter(schoolNewsList,mContext);
        ry_schoolNews.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        ry_schoolNews.setLayoutManager(layoutManager);

        progressWheel.setVisibility(View.INVISIBLE);  //进度条显示

        /**
         * 点击事件
         */
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {

                Bundle newsBundle = new Bundle();

                SchoolNews schoolNews = new SchoolNews();
                schoolNews.setNewsTitle(newsAdapter.getSchoolNews(position).getNewsTitle());
                schoolNews.setNewsContent(newsAdapter.getSchoolNews(position).getNewsContent());
                schoolNews.setNewsDate(newsAdapter.getSchoolNews(position).getNewsDate());
                schoolNews.setNewsImgURLList(newsAdapter.getSchoolNews(position).getNewsImgURLList());
                schoolNews.setNewsCurrentURL(newsAdapter.getSchoolNews(position).getNewsCurrentURL());

                newsBundle.putSerializable("newsBundle",schoolNews);

               Intent detailIntent = new Intent(mContext, DetailsActivity.class);
                detailIntent.putExtra("newsBundle",newsBundle);
                startActivity(detailIntent);
            }

            @Override
            public void OnItemLongClick(int position, View view) {

            }
        });

        //分页加载



    }


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
        progressWheel.setVisibility(View.VISIBLE);  //进度条显示
        initData();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        ry_schoolNews= (RecyclerView) view.findViewById(R.id.ry_schoolNews);
        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        progressWheel= (ProgressWheel) view.findViewById(R.id.progress_wheel);

    }

    /**
     * 初始化数据源
     */
    private void initData() {
        if(mThread==null){
            mThread=new Thread(runnable);
            mThread.start();
        }
    };


    Runnable runnable=new Runnable() {

        @Override
        public void run() {
            try {
                schoolNewsList = bzuData.getSchoolNews(SchoolURL.schoolNewsURL);
                mHandler.obtainMessage(MSG_SUCCESS,schoolNewsList).sendToTarget();
            } catch (IOException e) {
                mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
            }
        }
    };
    /**
     * 得到上下文对象
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
