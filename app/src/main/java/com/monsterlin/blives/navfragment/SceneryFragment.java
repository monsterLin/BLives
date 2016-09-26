package com.monsterlin.blives.navfragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.BScenery;
import com.monsterlin.blives.utils.ToastUtils;
import com.monsterlin.blives.widget.ProgressWebView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by monsterLin on 6/27/2016.
 */
public class SceneryFragment extends Fragment {

    private ProgressWebView wv_scenery;
//    private static final String appUrl ="http://720yun.com/wx/t/ed128ma89cv?pano_id=470704l";
//    private static final String appUrl ="http://monsterlin.ren/bzu/bzu_scenery.htm";

    private String appUrl;
    private FloatingActionButton fab;

    private BmobQuery<BScenery> query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenery, container, false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        wv_scenery = (ProgressWebView) view.findViewById(R.id.wv_scenery);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        query = new BmobQuery<>();
        query.setLimit(1);
        query.findObjects(getActivity(), new FindListener<BScenery>() {
            @Override
            public void onSuccess(List<BScenery> list) {
                if (list.size() == 0) {
                    ToastUtils.showToast(getActivity(), "暂无数据库", Toast.LENGTH_SHORT);
                } else {
                    BScenery bScenery = list.get(0);
                    appUrl = bScenery.getWebUrl();
                    initWeb(appUrl);
                }
            }


            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(getActivity(), "服务器异常：" + s, Toast.LENGTH_SHORT);
            }
        });
    }

    private void initWeb(String appUrl) {
        wv_scenery.getSettings().setJavaScriptEnabled(true);
        wv_scenery.loadUrl(appUrl);
    }

    private void initEvent() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wv_scenery.canGoBack()) {
                    wv_scenery.goBack();//返回上一页面
                } else {
                    Toast.makeText(getActivity(), "已是风光板块主页", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
