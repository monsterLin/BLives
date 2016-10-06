package com.monsterlin.blives.newsfragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.monsterlin.blives.R;
import com.monsterlin.blives.biz.NewsBiz;
import com.monsterlin.blives.constants.QueryNumber;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * 新闻的基类
 * Created by monsterLin on 6/28/2016.
 */
public class BaseFragment<T> extends Fragment implements NewsBiz{

    public BmobQuery<T> query ;
    public List<T> mList = new ArrayList<>();

    public int curPage = 0;
    public Context mContext;

    public boolean isLoading ;

    public SwipeRefreshLayout srl ;
    public RecyclerView rynews ;

    @Override
    public void initView(View view) {
        srl= (SwipeRefreshLayout) view.findViewById(R.id.srl);
        rynews= (RecyclerView) view.findViewById(R.id.rylist);
        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
    }

    @Override
    public void initData() {
        query= new BmobQuery<>();
        query.setLimit(QueryNumber.limit);
        query.order("-updateAt");
        query.setSkip(curPage*(QueryNumber.limit));
        curPage++;

    }

    @Override
    public void getData(int page) {
        query = new BmobQuery<>();
        query.setSkip(page*(QueryNumber.limit)+1);
        query.setLimit(5);
        query.order("-updateAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        curPage++;
    }

    @Override
    public void getNewestData(String topDate) {
        query=new BmobQuery<>();
        query.addWhereGreaterThan("updateAt",topDate);
        query.setLimit(QueryNumber.topLimt);
        query.order("-updateAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
