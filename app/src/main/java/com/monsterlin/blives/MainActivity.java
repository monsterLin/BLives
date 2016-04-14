package com.monsterlin.blives;

import android.content.Intent;
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
import android.widget.TextView;

import com.monsterlin.blives.activity.FeedBackActivity;
import com.monsterlin.blives.activity.LoginActivity;
import com.monsterlin.blives.entity.BUser;
import com.monsterlin.blives.navfragment.CorporationFragment;
import com.monsterlin.blives.navfragment.MainFragment;
import com.monsterlin.blives.navfragment.SceneryFragment;
import com.monsterlin.blives.navfragment.SquareFragment;
import com.monsterlin.blives.utils.ImageLoader;

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

    private Toolbar mToolBar ;

    private FloatingActionButton fab;

    private DrawerLayout drawer;

    private NavigationView navigationView;

    private ActionBarDrawerToggle toggle;

    private MainFragment  MainFragment;

    private SquareFragment  SquareFragment;

    private CorporationFragment CorporationFragment;

    private SceneryFragment  SceneryFragment;

    private Fragment mContent;

    private  Menu menu;

    private CircleImageView iv_userphoto;
    private TextView tv_nick , tv_depart;

    private BmobUser bmobUser ; //BmobUser对象
    private String  currentId ; //当前登录用户的id


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bmobUser=BmobUser.getCurrentUser(this);
       // StatusBarCompat.compat(this, getResources().getColor(R.color.colorAccent));  //沉浸式状态栏
        initView();
        initToolBar();
        initMain();
        initEvent();
    }


    /**
     * 初始化视图
     */
    protected void initView() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        iv_userphoto= (CircleImageView) findViewById(R.id.iv_userphoto);
        tv_nick= (TextView) findViewById(R.id.tv_nick);
        tv_depart= (TextView) findViewById(R.id.tv_depart);

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
        }
    //   drawer.closeDrawer(GravityCompat.START);
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
        MainFragment = new MainFragment();  //创建Fragment

        FragmentTransaction mainTransaction = getSupportFragmentManager().beginTransaction();
        mainTransaction.add(R.id.fram_main, MainFragment);
        mContent = MainFragment;
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
                if(null == MainFragment){
                    MainFragment=new MainFragment();
                }
                switchContent(MainFragment);
                break;
            case R.id.item_scenery:
                menu.getItem(1).setChecked(true);
                if(null ==SceneryFragment){
                    SceneryFragment = new SceneryFragment();
                }
                switchContent(SceneryFragment);
                break;
            case R.id.item_corporation:
                menu.getItem(2).setChecked(true);
                if (null == CorporationFragment){
                    CorporationFragment = new CorporationFragment();
                }
                switchContent(CorporationFragment);
                break;
            case R.id.item_square:
                menu.getItem(3).setChecked(true);
                if(null ==SquareFragment){
                    SquareFragment = new SquareFragment();
                };
                switchContent(SquareFragment);
                break;

            //TODO 关于submenu 替换fragment
            case R.id.item_theme:
              showToast("theme");
                break;
            case R.id.item_setting:
                showToast("setting");
                break;
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /** 修改显示的内容 不会重新加载 **/
    public void switchContent(Fragment to) {
        if (mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(mContent).add(R.id.fram_main, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_userphoto:
                //TODO 需要处理
                if(null ==bmobUser){
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivity(loginIntent);
                }else {
                        showToast("用户资料区域制作中....");
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






