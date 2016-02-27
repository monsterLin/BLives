package com.monsterlin.blives.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.NewsAdapter;
import com.monsterlin.blives.constants.SchoolURL;
import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.presenter.ParseBZUWeb;
import com.monsterlin.blives.presenter.impl.ParseBZUWebImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明：
 * 布局的名字和对应的类名字为测试的名字，最后基本布局确定后，通过重构的形式更改即可
 * Created by monsterLin on 2016/2/16.
 */
public class ModelOneFragment extends Fragment {

    private RecyclerView ry_schoolNews;
    private Context mContext;
    private LinearLayoutManager layoutManager;
    private List<SchoolNews> schoolNewsList = new ArrayList<>();
    private ParseBZUWeb bzuData = new ParseBZUWebImpl();
    private NewsAdapter newsAdapter;

    private Thread mThread;
    private final static int MSG_SUCCESS = 0; //成功拿到数据的标识
    private final static int MSG_FAILURE = 1; //无法拿到数据的标识

    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg){  //此方法在UI线程中运行
            switch(msg.what){
                case MSG_SUCCESS:
                     initUI((List<SchoolNews>) msg.obj);
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
        View view = inflater.inflate(R.layout.fragment_modelone,container,false);
        return view;
    }

    /**
     * 在这里可以书写相关控件的操作
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        //  initEvent();

    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        newsAdapter = new NewsAdapter(schoolNewsList,mContext);
        ry_schoolNews.setAdapter(newsAdapter);
        layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        ry_schoolNews.setLayoutManager(layoutManager);
    }

    /**
     * 初始化数据源
     */
    private void initData() {
        if(mThread==null){
            mThread=new Thread(runnable);
            mThread.start();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    schoolNewsList = bzuData.getSchoolNews(SchoolURL.schoolNewsURL);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

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
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        ry_schoolNews= (RecyclerView) view.findViewById(R.id.ry_schoolNews);

    }

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
