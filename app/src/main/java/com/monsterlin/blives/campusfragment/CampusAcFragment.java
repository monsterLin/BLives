package com.monsterlin.blives.campusfragment;

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
import com.monsterlin.blives.adapter.base.BaseRecyclerAdapter;
import com.monsterlin.blives.adapter.campus.CampusAcAdapter;
import com.monsterlin.blives.bean.CampusAc;
import com.monsterlin.blives.utils.SnackbarUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 社团活动
 * Created by monsterLin on 6/29/2016.
 */
public class CampusAcFragment extends Fragment {

    private CampusAcAdapter adapter;
    private RecyclerView rylist ;
    private BmobQuery<CampusAc> query;
    private  SwipeRefreshLayout srl ;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ac,container,false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SnackbarUtil.ShortSnackbar(view,"已是最新数据",SnackbarUtil.Info).show();
                srl.setRefreshing(false);
            }
        });
    }

    private void initData() {
        query=new BmobQuery<>();
        query.setLimit(20);
        query.order("-updateAt");
        query.include("acUnion");
        query.findObjects(getActivity(), new FindListener<CampusAc>() {
            @Override
            public void onSuccess(List<CampusAc> list) {
                initAdapter(list);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getActivity(), "获取数据异常："+s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initAdapter(List<CampusAc> list) {
        adapter=new CampusAcAdapter();
        adapter.addDatas(list);
        Log.e("AA",""+list.size());
        rylist.setAdapter(adapter);
        rylist.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object data) {
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
               Log.e ("Click",""+((CampusAc)data).getAcTitle());
            }
        });
    }

    private void initView(View view) {
        rylist= (RecyclerView) view.findViewById(R.id.rylist);
        srl= (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
    }
}
