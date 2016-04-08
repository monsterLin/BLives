package com.monsterlin.blives.mainfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.DetailsActivity;
import com.monsterlin.blives.adapter.SchoolNewsAdapter;
import com.monsterlin.blives.constants.DetailType;
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

    BmobQuery<SchoolNews> query ;
    private List<SchoolNews> mList = new ArrayList<>();
    private SchoolNewsAdapter adapter ;

    boolean isLoading ; //监听加载状态

    private int limit =10;		// 每页的数据是8条
    private int curPage = 0;		// 当前页的编号，从0开始

    private ProgressWheel progressWheel ;

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
        progressWheel.setVisibility(View.INVISIBLE);
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
                //　新数据的添加
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

    private void getData(int page) {

        //TODO 进行服务器端的分页处理
        BmobQuery<SchoolNews> query = new BmobQuery<>();
        query.order("-newsdate");
        query.setSkip(page*limit+1);
        query.setLimit(5);
        curPage++;
        query.findObjects(mContext, new FindListener<SchoolNews>() {
            @Override
            public void onSuccess(List<SchoolNews> list) {
                if(list.size()!=0){
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onError(int i, String s) {

            }
        });



    }


    private void initView(View view) {
        srl= (SwipeRefreshLayout) view.findViewById(R.id.srl);
        rynews= (RecyclerView) view.findViewById(R.id.rynews);
        progressWheel = (ProgressWheel) view.findViewById(R.id.progressWheel);




        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
                srl.setRefreshing(false);
            }
        });


        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rynews.setLayoutManager(layoutManager);
        adapter = new SchoolNewsAdapter(mContext,mList);
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

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition =layoutManager.findLastVisibleItemPosition(); //最后一个可视的Item
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {

                    boolean isRefreshing = srl.isRefreshing();  //刷新状态

                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        //　加载数据
                       getData(curPage);

                        isLoading = false;
                    }
                }

            }
        });

        /**
         * 点击事件
         */
     adapter.setOnItemClickListener(new SchoolNewsAdapter.OnItemClickListener() {
         @Override
         public void OnItemClick(int position, View view) {
            SchoolNews schoolNews = adapter.getSchoolNews(position);
            //showToast(""+schoolNews.getObjectId()+"\n"+""+schoolNews.getTitle());

             Bundle bundle = new Bundle();
             SchoolNews detail = new SchoolNews();
             detail.setTitle(schoolNews.getTitle());
             detail.setContent(schoolNews.getContent());
             detail.setNewsdate(schoolNews.getNewsdate());
             detail.setNewsimg(schoolNews.getNewsimg());

             bundle.putSerializable("detail",detail);
             bundle.putInt("type", DetailType.SchoolNews);

             Intent detailIntent = new Intent(mContext, DetailsActivity.class);
             detailIntent.putExtra("dataExtra",bundle);
            startActivity(detailIntent);
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

    private void showToast(String s){
        Toast.makeText(mContext,""+s,Toast.LENGTH_SHORT).show();
    }

}
