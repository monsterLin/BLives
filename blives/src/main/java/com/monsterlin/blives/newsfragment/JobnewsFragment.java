package com.monsterlin.blives.newsfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.news.NewsDetailsActivity;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.adapter.newsadapter.JobnewsAdapter;
import com.monsterlin.blives.bean.Jobnews;
import com.monsterlin.blives.biz.NewsBiz;
import com.monsterlin.blives.constants.DetailType;
import com.monsterlin.blives.mainfragment.BaseFragment;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * 主内容_就业信息
 * Created by monsterLin on 2016/4/1.
 */
public class JobnewsFragment  extends BaseFragment<Jobnews>  implements NewsBiz {

    private JobnewsAdapter jobnewsAdapter;
    private LinearLayoutManager layoutManager ;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_news_list,container,false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String topdate = jobnewsAdapter.getJobNews(0).getUpdatedAt();
                getNewestData(topdate);
                srl.setRefreshing(false);
            }
        });

        layoutManager = new LinearLayoutManager(mContext);
        rynews.setLayoutManager(layoutManager);
        jobnewsAdapter = new JobnewsAdapter(mList,mContext);
        rynews.setAdapter(jobnewsAdapter);

        rynews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition =layoutManager.findLastVisibleItemPosition(); //最后一个可视的Item
                if (lastVisibleItemPosition + 1 == jobnewsAdapter.getItemCount()) {

                    boolean isRefreshing = srl.isRefreshing();

                    if (isRefreshing) {
                        jobnewsAdapter.notifyItemRemoved(jobnewsAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        getData(curPage);
                        isLoading = false;
                    }
                }
            }
        });


        jobnewsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                Jobnews jobnews = jobnewsAdapter.getJobNews(position);
                Bundle bundle = new Bundle();
                Jobnews detail = new Jobnews();
                detail.setTitle(jobnews.getTitle());
                detail.setContent(jobnews.getContent());
                detail.setNewsimg(jobnews.getNewsimg());
                detail.setNewsdate(jobnews.getNewsdate());

                bundle.putSerializable("detail",detail);
                bundle.putInt("type", DetailType.Jobnews);

                Intent detailIntent = new Intent(mContext, NewsDetailsActivity.class);
                detailIntent.putExtra("dataExtra",bundle);
                startActivity(detailIntent);
            }

            @Override
            public void OnItemLongClick(int position, View view) {

            }
        });

    }



    @Override
    public void initData() {
        super.initData();
        query.findObjects(mContext, new FindListener<Jobnews>() {
            @Override
            public void onSuccess(List<Jobnews> list) {
                mList.addAll(list);
                if (null!= jobnewsAdapter){
                    jobnewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                TastyToast.makeText(mContext, "发生异常："+s, TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        });
    }

    @Override
    public void getData(int page) {
        super.getData(page);
        query.findObjects(mContext, new FindListener<Jobnews>() {
            @Override
            public void onSuccess(List<Jobnews> list) {
                if(list.size()!=0){
                    mList.addAll(list);
                    jobnewsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                TastyToast.makeText(mContext, "发生异常："+s, TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        });
    }

    @Override
    public void getNewestData(String topDate) {
        super.getNewestData(topDate);
        query.findObjects(mContext, new FindListener<Jobnews>() {
            @Override
            public void onSuccess(List<Jobnews> list) {
                if (list.size()!=0){
                    for (Jobnews jobnews:list){
                        mList.add(0,jobnews);
                        jobnewsAdapter.notifyItemInserted(0);
                        TastyToast.makeText(mContext,"增加了"+list.size()+"条数据",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                    }
                }else {
                    TastyToast.makeText(mContext,"已是最新数据",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
                }
            }

            @Override
            public void onError(int i, String s) {
                TastyToast.makeText(mContext, "发生异常："+s, TastyToast.LENGTH_SHORT,TastyToast.ERROR);
            }
        });
    }

}
