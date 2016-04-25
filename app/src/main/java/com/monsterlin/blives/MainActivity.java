package com.monsterlin.blives;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.blives.activity.FeedBackActivity;
import com.monsterlin.blives.activity.LifeActivity;
import com.monsterlin.blives.activity.LoginActivity;
import com.monsterlin.blives.activity.SettingActivity;
import com.monsterlin.blives.activity.ShowUserActivity;
import com.monsterlin.blives.entity.BUser;
import com.monsterlin.blives.navfragment.CampusFragment;
import com.monsterlin.blives.navfragment.MainFragment;
import com.monsterlin.blives.navfragment.SceneryFragment;
import com.monsterlin.blives.navfragment.SquareFragment;
import com.monsterlin.blives.utils.CheckNetWork;
import com.monsterlin.blives.utils.ImageLoader;

import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import de.hdodenhof.circleimageview.CircleImageView;

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
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    @InjectView(R.id.toolbar)
    Toolbar mToolBar;

    @InjectView(R.id.fab)
    FloatingActionButton fab;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer;

    @InjectView(R.id.nav_view)
    NavigationView navigationView;

    @InjectView(R.id.iv_userphoto)
    CircleImageView iv_userphoto;


    @InjectView(R.id.tv_nick)
    TextView tv_nick;

    @InjectView(R.id.tv_depart)
    TextView tv_depart;

    private ActionBarDrawerToggle toggle;

    private Fragment  MainFragment;

    private Fragment  SquareFragment;

    private Fragment CampusFragment;

    private Fragment  SceneryFragment;

    private  Menu menu;
    private BmobUser bmobUser ; //BmobUser对象
    private String  currentId ; //当前登录用户的id


    private boolean isNet;

    @InjectView(R.id.tv_netWork)
    TextView tv_netWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivityButterKnife(this);  //初始化注解框架
        initToolBar(mToolBar,"BLives",false);  //初始化ToolBar
        checkNet();

    }

    /**
     * 网络监测
     */
    private void checkNet() {
        isNet=new CheckNetWork().isNetworkAvailable(this);
        if (isNet){
            //网络连接
            checkVersion();
            initData();  //初始化用户数据
            initMain(); //初始化主页面
            initEvent();  //初始化点击事件
        }else {
            //无网络连接
            new CheckNetWork().showNetDialog(this);
            tv_netWork.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 版本监测
     */
    private void checkVersion() {
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);
//        BmobUpdateAgent.update(this);  //强制更新



    }

    /**
     * 初始化用户数据
     */
    private void initData() {
        bmobUser=BmobUser.getCurrentUser(this);
        updateleftUserData();  //更新左侧用户资料
    }



    /**
     * 更新左侧用户资料
     */
    public void updateleftUserData(){
        bmobUser=BmobUser.getCurrentUser(this);
        if (bmobUser!=null){
            currentId=bmobUser.getObjectId();
            BmobQuery<BUser> query = new BmobQuery<>();
            query.getObject(MainActivity.this, currentId, new GetListener<BUser>() {
                @Override
                public void onSuccess(BUser bUser) {
                    tv_nick.setText(bUser.getNick());
                    tv_depart.setText(bUser.getDepart());
                    String photoUrl = bUser.getUserPhoto().getFileUrl(MainActivity.this);
                    if(photoUrl!=null){
                        iv_userphoto.setTag(photoUrl);
                        new ImageLoader().showImageByThread(iv_userphoto,photoUrl);
                    }else {
                        iv_userphoto.setBackgroundResource(R.drawable.ic_bzu);
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("接收用户数据异常："+s);
                }
            });
        }else {
            tv_nick.setText("滨州学院");
            tv_depart.setText("信息工程系");
           iv_userphoto.setImageResource(R.drawable.ic_bzu);
        }
         //   drawer.closeDrawer(GravityCompat.START);
    }


    /**
     * 初始化主内容区域
     */
    private void initMain() {
        Menu menu = navigationView.getMenu();
        menu.getItem(0).setChecked(true);
        setSelect(0);
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
               Intent feedbackIntent = new Intent(MainActivity.this, FeedBackActivity.class);
                startActivity(feedbackIntent);
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

        /**
         * 个人信息区域
         */
        iv_userphoto.setOnClickListener(this);
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
            showToast("功能暂无开放，敬请期待");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
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
                setSelect(0);
                fab.setVisibility(View.VISIBLE);

                break;
            case R.id.item_scenery:
                menu.getItem(1).setChecked(true);
                setSelect(1);
                fab.setVisibility(View.VISIBLE);

                break;
            case R.id.item_campus:
                menu.getItem(2).setChecked(true);
                setSelect(2);
                fab.setVisibility(View.GONE);

                break;
            case R.id.item_square:
                menu.getItem(3).setChecked(true);
                setSelect(3);
                fab.setVisibility(View.INVISIBLE);
                break;

            case R.id.item_life:
               nextActivity(LifeActivity.class);
                break;
            case R.id.item_setting:
                nextActivity(SettingActivity.class);
                break;
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 切换Fragment
     * @param i
     */
    private void setSelect(int i)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        // 设置内容区域
        switch (i)
        {
            case 0:
                if (MainFragment == null)
                {
                    MainFragment = new MainFragment();
                    transaction.add(R.id.fram_main, MainFragment);
                } else
                {
                    transaction.show(MainFragment);
                }

                break;
            case 1:
                if (SceneryFragment == null)
                {
                    SceneryFragment = new SceneryFragment();transaction.add(R.id.fram_main, SceneryFragment);
                } else
                {
                    transaction.show(SceneryFragment);

                }

                break;
            case 2:
                if (CampusFragment == null)
                {
                    CampusFragment = new CampusFragment();
                    transaction.add(R.id.fram_main, CampusFragment);
                } else
                {
                    transaction.show(CampusFragment);
                }

                break;
            case 3:
                if (SquareFragment == null)
                {
                    SquareFragment = new SquareFragment();
                    transaction.add(R.id.fram_main, SquareFragment);
                } else
                {
                    transaction.show(SquareFragment);
                }

                break;

            default:
                break;
        }

        transaction.commit();
    }

    /**
     * 隐藏Fragment
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction)
    {
        if (MainFragment != null)
        {
            transaction.hide(MainFragment);
        }
        if (SquareFragment != null)
        {
            transaction.hide(SquareFragment);
        }
        if (CampusFragment != null)
        {
            transaction.hide(CampusFragment);
        }
        if (SceneryFragment != null)
        {
            transaction.hide(SceneryFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_userphoto:
                if(null ==bmobUser){
                    nextActivity(LoginActivity.class);
                }else {
                   String objectId = bmobUser.getObjectId();
                    Intent showUserIntent = new Intent(this,ShowUserActivity.class);
                    showUserIntent.putExtra("objectId",objectId);
//                    nextActivity(ShowUserActivity.class);
                    startActivity(showUserIntent);

                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateleftUserData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateleftUserData();
    }

}






