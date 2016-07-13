package com.monsterlin.blives.navfragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.changeadapter.ViewPagerAdapter;
import com.monsterlin.blives.mainfragment.AcinformsFragment;
import com.monsterlin.blives.mainfragment.JobnewsFragment;
import com.monsterlin.blives.mainfragment.OffnewsFragment;
import com.monsterlin.blives.mainfragment.SNewsFragment;

/**
 * Created by monsterLin on 6/27/2016.
 */
public class MainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private int cachePagers = 3;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        initView(view);
        initMainContent();
        return view;
    }

    private void initView(View view) {
        mTabLayout= (TabLayout) view.findViewById(R.id.tl_main_tabs);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_main_content);
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
        mViewPager.setOffscreenPageLimit(cachePagers);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
