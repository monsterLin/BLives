package com.monsterlin.blives.navfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.ViewPagerAdapter;
import com.monsterlin.blives.mainfragment.AcinformsFragment;
import com.monsterlin.blives.mainfragment.JobnewsFragment;
import com.monsterlin.blives.mainfragment.OffnewsFragment;
import com.monsterlin.blives.mainfragment.SNewsFragment;

/**
 * 说明：
 * 布局的名字和对应的类名字为测试的名字，最后基本布局确定后，通过重构的形式更改即可
 * Created by monsterLin on 2016/2/16.
 */
public class MainFragment extends Fragment {

    /**
     * TabLayout和ViewPager组合实现切换的功能
     */
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private Context mContext;
    /**
     * 创建视图，返回View对象
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initMainContent();
    }

    /**
     * 初始化viewpager
     */
    private void initMainContent() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        Fragment sNewsFragment = new SNewsFragment();
        Fragment acinformsFragment = new AcinformsFragment();
        Fragment offnewsFragment = new OffnewsFragment();
        Fragment jobnewsFragment = new JobnewsFragment();

        adapter.addFragment(sNewsFragment, "学校新闻");
        adapter.addFragment(acinformsFragment, "学术通知");
        adapter.addFragment(offnewsFragment, "教务要闻");
        adapter.addFragment(jobnewsFragment, "就业信息");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 初始化视图
     * @param view
     */
    private void initView(View view) {
        mTabLayout= (TabLayout) view.findViewById(R.id.tl_main_tabs);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_main_content);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
