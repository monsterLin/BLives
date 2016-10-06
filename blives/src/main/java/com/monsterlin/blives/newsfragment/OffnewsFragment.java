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
import com.monsterlin.blives.adapter.newsadapter.OffnewsAdapter;
import com.monsterlin.blives.bean.Offnews;
import com.monsterlin.blives.biz.NewsBiz;
import com.monsterlin.blives.constants.DetailType;
import com.monsterlin.blives.mainfragment.BaseFragment;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * 主内容_教务信息
 * Created by monsterLin on 2016/4/1.
 */
public class OffnewsFragment extends   BaseFragment<Offnews>  implements NewsBiz {

    private OffnewsAdapter offnewsAdapter;
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
                String topdate = offnewsAdapter.getOffnews(0).getUpdatedAt();
                getNewestData(topdate);
                srl.setRefreshing(false);
            }
        });

        layoutManager = new LinearLayoutManager(mContext);
        rynews.setLayoutManager(layoutManager);
        offnewsAdapter = new OffnewsAdapter(mList,mContext);
        rynews.setAdapter(offnewsAdapter);

        rynews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition =layoutManager.findLastVisibleItemPosition(); //最后一个可视的Item
                if (lastVisibleItemPosition + 1 == offnewsAdapter.getItemCount()) {

                    boolean isRefreshing = srl.isRefreshing();

                    if (isRefreshing) {
                        offnewsAdapter.notifyItemRemoved(offnewsAdapter.getItemCount());
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

        offnewsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                Offnews offnews = offnewsAdapter.getOffnews(position);
                Bundle bundle = new Bundle();
                Offnews detail = new Offnews();
                detail.setTitle(offnews.getTitle());
                detail.setContent(offnews.getContent());
                detail.setNewsimg(offnews.getNewsimg());
                detail.setNewsdate(offnews.getNewsdate());

                bundle.putSerializable("detail",detail);
                bundle.putInt("type", DetailType.Offnews);

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
        query.findObjects(mContext, new FindListener<Offnews>() {
            @Override
            public void onSuccess(List<Offnews> list) {
                mList.addAll(list);
                if (null!= offnewsAdapter){
                    offnewsAdapter.notifyDataSetChanged();
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
        query.findObjects(mContext, new FindListener<Offnews>() {
            @Override
            public void onSuccess(List<Offnews> list) {
                if(list.size()!=0){
                    mList.addAll(list);
                    offnewsAdapter.notifyDataSetChanged();
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
        query.findObjects(mContext, new FindListener<Offnews>() {
            @Override
            public void onSuccess(List<Offnews> list) {
                if (list.size()!=0){
                    for (Offnews offnews:list){
                        mList.add(0,offnews);
                        offnewsAdapter.notifyItemInserted(0);
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
