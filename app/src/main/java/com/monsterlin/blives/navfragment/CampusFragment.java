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
import com.monsterlin.blives.campusfragment.CampusAcFragment;
import com.monsterlin.blives.campusfragment.DepartAcFragment;
import com.monsterlin.blives.campusfragment.UnionFragment;
import com.monsterlin.blives.mainfragment.AcinformsFragment;
import com.monsterlin.blives.mainfragment.JobnewsFragment;
import com.monsterlin.blives.mainfragment.OffnewsFragment;
import com.monsterlin.blives.mainfragment.SNewsFragment;

/**
 * Created by monsterLin on 6/27/2016.
 */
public class CampusFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private int cachePagers = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus,container,false);
        initView(view);
        initMainContent();
        return view;
    }

    private void initView(View view) {
        mTabLayout= (TabLayout) view.findViewById(R.id.tl_campus_tabs);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_campus_content);
    }

    /**
     * 初始化viewpager
     */
    private void initMainContent() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());

        Fragment campusAcFragment = new CampusAcFragment();
        Fragment departAcFragment = new DepartAcFragment();
        Fragment unionFragment = new UnionFragment();

        adapter.addFragment(departAcFragment, "系院活动");
        adapter.addFragment(campusAcFragment, "社团活动");
        adapter.addFragment(unionFragment, "社团联盟");


        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(cachePagers);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
