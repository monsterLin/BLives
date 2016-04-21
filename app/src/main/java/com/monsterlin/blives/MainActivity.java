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
import com.monsterlin.blives.activity.LoginActivity;
import com.monsterlin.blives.activity.SettingActivity;
import com.monsterlin.blives.activity.ShowUserActivity;
import com.monsterlin.blives.activity.ThemeActivity;
import com.monsterlin.blives.entity.BUser;
import com.monsterlin.blives.navfragment.CorporationFragment;
import com.monsterlin.blives.navfragment.MainFragment;
import com.monsterlin.blives.navfragment.SceneryFragment;
import com.monsterlin.blives.navfragment.SquareFragment;
import com.monsterlin.blives.utils.ImageLoader;

import butterknife.InjectView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
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

    private Fragment CorporationFragment;

    private Fragment  SceneryFragment;

    private  Menu menu;
    private BmobUser bmobUser ; //BmobUser对象
    private String  currentId ; //当前登录用户的id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivityButterKnife(this);  //初始化注解框架
        initToolBar(mToolBar,"BLives",false);  //初始化ToolBar
        initData();  //初始化用户数据
        initMain(); //初始化主页面
        initEvent();  //初始化点击事件
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
            showToast("消息");
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
            case R.id.item_corporation:
                menu.getItem(2).setChecked(true);
                setSelect(2);
                fab.setVisibility(View.VISIBLE);

                break;
            case R.id.item_square:
                menu.getItem(3).setChecked(true);
                setSelect(3);
                fab.setVisibility(View.INVISIBLE);
                break;

            case R.id.item_theme:
                Intent themeIntent = new Intent(MainActivity.this, ThemeActivity.class);
                startActivity(themeIntent);
                break;
            case R.id.item_setting:
               Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
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
                if (CorporationFragment == null)
                {
                    CorporationFragment = new CorporationFragment();
                    transaction.add(R.id.fram_main, CorporationFragment);
                } else
                {
                    transaction.show(CorporationFragment);
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
        if (CorporationFragment != null)
        {
            transaction.hide(CorporationFragment);
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
                    nextActivity(ShowUserActivity.class);
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






