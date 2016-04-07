package com.monsterlin.blives.navfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.SceneryAdapter;
import com.monsterlin.blives.entity.Scenery;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 校园风光
 * Created by monsterLin on 2016/2/16.
 */
public class SceneryFragment extends Fragment {

    private RecyclerView ryimgs;
    private Context mContext;
    private SceneryAdapter adapter;

    BmobQuery<Scenery> query;
    private SwipeRefreshLayout srl;
    //private List<Scenery> mList = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenery, container, false);
        return view;
    }



    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        progressBar.setVisibility(View.INVISIBLE);
    }


    private void initView(View view) {
        ryimgs= (RecyclerView) view.findViewById(R.id.ryimgs);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        //srl= (SwipeRefreshLayout) view.findViewById(R.id.srl);

//
//        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
//        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//                srl.setRefreshing(false);
//            }
//        });
//
//
    }




    private void initData() {
        query= new BmobQuery<Scenery>();
        query.order("-updatedAt");
        query.setLimit(10);
        query.findObjects(mContext, new FindListener<Scenery>() {
            @Override
            public void onSuccess(List<Scenery> list) {
                //　新数据的添加
                final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                ryimgs.setLayoutManager(layoutManager);
                adapter = new SceneryAdapter(list,mContext);
                ryimgs.setAdapter(adapter);
                //ryimgs.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                ryimgs.setLayoutManager(new GridLayoutManager(mContext,2));
            }

            @Override
            public void onError(int i, String s) {
                Log.e("OnError :",s);
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
