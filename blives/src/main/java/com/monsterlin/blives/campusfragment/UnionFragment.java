package com.monsterlin.blives.campusfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.campus.UnionActivity;
import com.monsterlin.blives.adapter.campus.UnionAdapter;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.bean.Union;
import com.monsterlin.blives.mainfragment.BaseFragment;
import com.monsterlin.blives.utils.SnackbarUtil;

import java.util.List;

import cn.bmob.v3.listener.FindListener;

/**
 * 社团联盟
 * Created by monsterLin on 6/29/2016.
 */
public class UnionFragment extends BaseFragment<Union>{

    private View view;
    private UnionAdapter unionAdapter;
    private LinearLayoutManager layoutManager ;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_union,container,false);
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
                String topdate = unionAdapter.getUnion(0).getUpdatedAt();
                getNewestData(topdate);
                srl.setRefreshing(false);
            }
        });

        layoutManager = new LinearLayoutManager(mContext);
        rynews.setLayoutManager(layoutManager);
        unionAdapter = new UnionAdapter(mList,mContext);
        rynews.setAdapter(unionAdapter);

        rynews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition =layoutManager.findLastVisibleItemPosition(); //最后一个可视的Item
                if (lastVisibleItemPosition + 1 == unionAdapter.getItemCount()) {

                    boolean isRefreshing = srl.isRefreshing();

                    if (isRefreshing) {
                        unionAdapter.notifyItemRemoved(unionAdapter.getItemCount());
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

        unionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {
                String objectId = unionAdapter.getUnion(position).getObjectId();
                Intent unionIntent = new Intent(mContext, UnionActivity.class);
                unionIntent.putExtra("objectId",objectId);
                startActivity(unionIntent);
            }
            @Override
            public void OnItemLongClick(int position, View view) {

            }
        });


    }


    @Override
    public void initData() {
        super.initData();
        query.findObjects(mContext, new FindListener<Union>() {
            @Override
            public void onSuccess(List<Union> list) {
                mList.addAll(list);
                if (null!= unionAdapter){
                    Log.e("TAG",""+list.size());
                    unionAdapter.notifyDataSetChanged();
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
        query.findObjects(mContext, new FindListener<Union>() {
            @Override
            public void onSuccess(List<Union> list) {
                if(list.size()!=0){
                    mList.addAll(list);
                    unionAdapter.notifyDataSetChanged();
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
        query.findObjects(mContext, new FindListener<Union>() {
            @Override
            public void onSuccess(List<Union> list) {
                if (list.size()!=0){
                    for (Union union:list){
                        mList.add(0,union);
                        unionAdapter.notifyItemInserted(0);
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
