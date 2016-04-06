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
import com.monsterlin.blives.adapter.NormalAdapter;
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

    //private NewsAdapter adapter;

    private ProgressWheel  progressWheel ;

    BmobQuery<SchoolNews> query ;
    private List<SchoolNews> mList = new ArrayList<>();
    private NormalAdapter adapter ;

    boolean isLoading ; //监听加载状态

    private int limit = 2;		// 每页的数据是10条
    private int curPage = 0;		// 当前页的编号，从0开始


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
        query.setLimit(limit);
        query.setSkip(curPage*limit);
        curPage++;
        query.findObjects(mContext, new FindListener<SchoolNews>() {
            @Override
            public void onSuccess(List<SchoolNews> list) {
                //TODO　新数据的添加
                mList.addAll(list);
                if (null != adapter){
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.e("OnError :",s);
            }
        });

    }

    private void getData() {

        //TODO 进行服务器端的分页处理



    }


    private void initView(View view) {
        srl= (SwipeRefreshLayout) view.findViewById(R.id.srl);
        rynews= (RecyclerView) view.findViewById(R.id.rynews);
//        progressWheel= (ProgressWheel) view.findViewById(R.id.progressWheel);


        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                //TODO 首先在这里得到数据 ，得到数据后进行刷新停止的操作
//                mList.clear(); //清空List
//
//                //TODO  得到数据
//
//                getData();
                //initData里面做了分页
                initData();
                srl.setRefreshing(false);
            }
        });


        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rynews.setLayoutManager(layoutManager);
        adapter = new NormalAdapter(mContext,mList);
        rynews.setAdapter(adapter);

        //滑动监听
        rynews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                /**
                 * 当RecyclerView的滑动状态改变时触发
                 * 0： 手指离开屏幕
                 * 1：  手指触摸屏幕
                 * 2： 手指加速滑动并放开，此时滑动状态伴随SCROLL_STATE_IDLE
                 */
                Log.d("test", "StateChanged = " + newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("test", "onScrolled");
                int lastVisibleItemPosition =layoutManager.findLastVisibleItemPosition(); //最后一个可视的Item
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {

                    boolean isRefreshing = srl.isRefreshing();  //刷新状态

                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        //TODO　加载数据

                        getData();
                        isLoading = false;
                    }
                }

            }
        });

     adapter.setOnItemClickListener(new NormalAdapter.OnItemClickListener() {
         @Override
         public void OnItemClick(int position, View view) {
             Log.d("test", "item position = " + position);
         }

         @Override
         public void OnItemLongClick(int position, View view) {

         }
     });


    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }


}
