package com.monsterlin.blives;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.monsterlin.blives.navfragment.CorporationFragment;
import com.monsterlin.blives.navfragment.MainFragment;
import com.monsterlin.blives.navfragment.SquareFragment;
import com.monsterlin.blives.utils.StatusBarCompat;

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
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolBar ;

    private FloatingActionButton fab;

    private DrawerLayout drawer;

    private NavigationView navigationView;

    private ActionBarDrawerToggle toggle;

    private Fragment fragment;
    private  Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));  //沉浸式状态栏
        initView();
        initToolBar();
        initMain();
        initEvent();
    }

    @Override
    protected void initData() {

    }

    /**
     * 初始化视图
     */
    protected void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolBar.setTitle("BLives");
        setSupportActionBar(mToolBar);
    }


    /**
     * 初始化主内容区域
     */
    private void initMain() {
        Menu menu = navigationView.getMenu();
        menu.getItem(0).setChecked(true);
        fragment = new MainFragment();  //创建Fragment

        FragmentTransaction mainTransaction = getSupportFragmentManager().beginTransaction();
        mainTransaction.replace(R.id.fram_main,fragment);
        mainTransaction.commit();
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
        /**
         * 悬浮按钮点击事件
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("你好，我是FAB");
            }
        });

        /**
         * 控制菜单的变化
         */
        toggle = new ActionBarDrawerToggle(
                this, drawer,    mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /**
         * 菜单中的item的点击事件
         */
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 创建菜单视图（右上角部分）
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 菜单的点击事件（右上角部分）
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_notification) {
            showToast("消息");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * TODO 此处需优化
     * 左侧菜单视图的点击事件
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        menu = navigationView.getMenu();

        switch (id){
            case R.id.item_main:
                menu.getItem(0).setChecked(true); //用于item的选中状态
                fragment = new MainFragment();

                FragmentTransaction mainTransaction = getSupportFragmentManager().beginTransaction();
                mainTransaction.replace(R.id.fram_main,fragment);
                mainTransaction.commit();
                break;
            case R.id.item_scenery:
                menu.getItem(1).setChecked(true);
                fragment = new SquareFragment();

                FragmentTransaction sceneryTransaction = getSupportFragmentManager().beginTransaction();
                sceneryTransaction.replace(R.id.fram_main,fragment);
                sceneryTransaction.commit();

                break;
            case R.id.item_corporation:
                menu.getItem(2).setChecked(true);
                fragment = new CorporationFragment();

                FragmentTransaction corporationTransaction = getSupportFragmentManager().beginTransaction();
                corporationTransaction.replace(R.id.fram_main,fragment);
                corporationTransaction.commit();

                break;
            case R.id.item_square:
                menu.getItem(3).setChecked(true);
                fragment = new SquareFragment();

                FragmentTransaction squareTransaction = getSupportFragmentManager().beginTransaction();
                squareTransaction.replace(R.id.fram_main,fragment);
                squareTransaction.commit();
                break;

            //TODO 关于submenu 替换fragment
            case R.id.item_theme:
              showToast("theme");
                break;
            case R.id.item_setting:
                showToast("setting");
                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
