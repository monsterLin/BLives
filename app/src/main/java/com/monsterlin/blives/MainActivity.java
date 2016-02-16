package com.monsterlin.blives;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.monsterlin.blives.adapter.ViewPagerAdapter;
import com.monsterlin.blives.fragment.ModelFourFragment;
import com.monsterlin.blives.fragment.ModelOneFragment;
import com.monsterlin.blives.fragment.ModelThreeFragment;
import com.monsterlin.blives.fragment.ModelTwoFragment;

/**
 *
 *----------Dragon be here!----------/
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃神兽保佑
 * 　　　　┃　　　┃代码无BUG！
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 */
public class MainActivity extends BaseActivity {

    /**
     * ToolBar 控件
     */
    private Toolbar mToolBar ;
    /**
     * 悬浮按钮控件
     */
    private FloatingActionButton fab;
    /**
     * 类似于SLideMenu的布局
     */
    private DrawerLayout mDrawerLayout;
    /**
     * 菜单布局
     */
    private NavigationView mNavigationView;
    /**
     * 控制菜单栏的出现关闭按钮
     */
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    /**
     * TabLayout和ViewPager组合实现切换的功能
     */
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initToolBar();
        initMainContent();
        initAction();
    }

    /**
     * 监听事件的处理
     */
    private void initAction() {

        /**
         * 侧滑菜单监听事件
         */
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    /**
     * 初始化主内容区域
     */
    private void initMainContent() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Fragment modelOne = new ModelOneFragment();
        Fragment modelTwo = new ModelTwoFragment();
        Fragment modelThree = new ModelThreeFragment();
        Fragment modelFour = new ModelFourFragment();

        adapter.addFragment(modelOne, "modelOne");
        adapter.addFragment(modelTwo, "modelTwo");
        adapter.addFragment(modelThree, "modelThree");
        adapter.addFragment(modelFour, "modelFour");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolBar.setTitle("BLives");
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolBar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.nv_main_menu);
        mViewPager = (ViewPager) findViewById(R.id.vp_main_content);
        mTabLayout = (TabLayout) findViewById(R.id.tl_main_tabs);
    }

    /**
     * 右上角菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
