package com.monsterlin.blives.newsfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.NewsDetailsActivity;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.adapter.newsadapter.AcinformsAdapter;
import com.monsterlin.blives.bean.Acinforms;
import com.monsterlin.blives.biz.NewsBiz;
import com.monsterlin.blives.constants.DetailType;
import com.monsterlin.blives.mainfragment.BaseFragment;
import com.monsterlin.blives.utils.SnackbarUtil;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * 主内容_学术通知
 * Created by monsterlin on 2016/4/1.
 */
public class AcinformsFragment extends BaseFragment<Acinforms> implements NewsBiz {

    private AcinformsAdapter acinformsAdapter;
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
                String topdate = acinformsAdapter.getAcinform(0).getUpdatedAt();
                getNewestData(topdate);
                srl.setRefreshing(false);
            }
        });

        layoutManager = new LinearLayoutManager(mContext);
        rynews.setLayoutManager(layoutManager);
        acinformsAdapter = new AcinformsAdapter(mList,mContext);
        rynews.setAdapter(acinformsAdapter);

        rynews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition =layoutManager.findLastVisibleItemPosition(); //最后一个可视的Item
                if (lastVisibleItemPosition + 1 == acinformsAdapter.getItemCount()) {

                    boolean isRefreshing = srl.isRefreshing();

                    if (isRefreshing) {
                        acinformsAdapter.notifyItemRemoved(acinformsAdapter.getItemCount());
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

        acinformsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                Acinforms acinforms = acinformsAdapter.getAcinform(position);
                Bundle bundle = new Bundle();
                Acinforms detail = new Acinforms();
                detail.setTitle(acinforms.getTitle());
                detail.setContent(acinforms.getContent());
                detail.setNewsimg(acinforms.getNewsimg());
                detail.setNewsdate(acinforms.getNewsdate());

                bundle.putSerializable("detail",detail);
                bundle.putInt("type", DetailType.Acinforms);

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
        query.findObjects(mContext, new FindListener<Acinforms>() {
            @Override
            public void onSuccess(List<Acinforms> list) {
                mList.addAll(list);
                if (null!= acinformsAdapter){
                    acinformsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "发生异常："+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getData(int page) {
        super.getData(page);
        query.findObjects(mContext, new FindListener<Acinforms>() {
            @Override
            public void onSuccess(List<Acinforms> list) {
                if(list.size()!=0){
                    mList.addAll(list);
                    acinformsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "发生异常："+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void getNewestData(String topDate) {
        super.getNewestData(topDate);
        query.findObjects(mContext, new FindListener<Acinforms>() {
            @Override
            public void onSuccess(List<Acinforms> list) {
                if (list.size()!=0){
                    for (Acinforms acinforms:list){
                        mList.add(0,acinforms);
                        acinformsAdapter.notifyItemInserted(0);
                        SnackbarUtil.ShortSnackbar(view,"增加了"+list.size()+"条数据",SnackbarUtil.Warning).show();
                    }
                }else {
                    SnackbarUtil.ShortSnackbar(view,"已是最新数据",SnackbarUtil.Info).show();
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "发生异常："+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
