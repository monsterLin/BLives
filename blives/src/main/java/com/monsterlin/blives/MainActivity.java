package com.monsterlin.blives;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.blives.mainfragment.CampusFragment;
import com.monsterlin.blives.mainfragment.HomeFragment;
import com.monsterlin.blives.mainfragment.MapFragment;
import com.monsterlin.blives.mainfragment.MeFragment;
import com.monsterlin.blives.mainfragment.SceneryFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    private Toolbar mToolBar;

    private TextView main_bottombar_home, main_bottombar_scenery, main_bottombar_campus, main_bottombar_map, main_bottombar_me;

    private TextView main_tv_title;

    private String[] tags = new String[]{"homeFragment", "scenerylFragment", "campusFragment", "mapFragment", "meFragment"};

    private int curIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
       setSelect(0);
    }

    private void initEvent() {
        main_bottombar_home.setOnClickListener(this);
        main_bottombar_scenery.setOnClickListener(this);
        main_bottombar_campus.setOnClickListener(this);
        main_bottombar_map.setOnClickListener(this);
        main_bottombar_me.setOnClickListener(this);
    }

    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        main_tv_title= (TextView) findViewById(R.id.main_tv_title);
        main_bottombar_home = (TextView) findViewById(R.id.main_bottombar_home);
        main_bottombar_scenery = (TextView) findViewById(R.id.main_bottombar_scenery);
        main_bottombar_campus = (TextView) findViewById(R.id.main_bottombar_campus);
        main_bottombar_map = (TextView) findViewById(R.id.main_bottombar_map);
        main_bottombar_me = (TextView) findViewById(R.id.main_bottombar_me);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_bottombar_home:
                setSelect(0);
                break;
            case R.id.main_bottombar_scenery:
                setSelect(1);
                break;
            case R.id.main_bottombar_campus:
                setSelect(2);
                break;
            case R.id.main_bottombar_map:
                setSelect(3);
                break;
            case R.id.main_bottombar_me:
                setSelect(4);
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param i 切换页
     */
    private void setSelect(int i) {

        if (curIndex == i)
            return;

        changIcon(i);

        /**
         * 创建Fragment，如果为空，则进行创建
         */
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tags[i]);
        if (fragment == null) {
            switch (i) {
                case 0:
                    fragment = new HomeFragment();
                    break;
                case 1:
                    fragment = new SceneryFragment();
                    break;
                case 2:
                    fragment = new CampusFragment();
                    break;
                case 3:
                    fragment = new MapFragment();
                    break;
                case 4:
                    fragment = new MeFragment();
                    break;
            }
            /**
             * 添加视图，使得页面得以显示，并且设置tags标签
             */

            getSupportFragmentManager().beginTransaction().add(R.id.main_frame_main, fragment, tags[i]).commit();
        }

        /**
         * 进行Fragment的循环
         * 首先全部隐藏
         */
        for (int j = 0; j < 5; j++) {
            Fragment f = getSupportFragmentManager().findFragmentByTag(tags[j]);
            if (f != null) {
                getSupportFragmentManager().beginTransaction().hide(f).commit();
            }
        }
        /**
         * 然后显示当前的Fragment
         */
        getSupportFragmentManager().beginTransaction().show(fragment).commit();


    }

    /**
     * 改变图标颜色
     *
     * @param i
     */
    private void changIcon(int i) {
        main_bottombar_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_main_before, 0, 0);
        main_bottombar_scenery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_scenery_before, 0, 0);
        main_bottombar_campus.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_campus_before, 0, 0);
        main_bottombar_map.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map_before, 0, 0);
        main_bottombar_me.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_me_before, 0, 0);
        switch (i) {
            case 0:
                main_tv_title.setText("首页");
                main_bottombar_home.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_main_after, 0, 0);
                break;
            case 1:
                main_tv_title.setText("校园风景");
                main_bottombar_scenery.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_scenery_after, 0, 0);
                break;
            case 2:
                main_tv_title.setText("校园社团");
                main_bottombar_campus.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_campus_after, 0, 0);
                break;
            case 3:
                main_tv_title.setText("校园地图");
                main_bottombar_map.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map_after, 0, 0);
                break;
            case 4:
                main_tv_title.setText("我的");
                main_bottombar_me.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_me_after, 0, 0);
                break;
        }

    }
}
