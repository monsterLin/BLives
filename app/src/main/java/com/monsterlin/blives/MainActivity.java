package com.monsterlin.blives;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.monsterlin.blives.activity.map.MapActivity;
import com.monsterlin.blives.activity.setting.SettingActivity;
import com.monsterlin.blives.activity.user.LoginActivity;
import com.monsterlin.blives.activity.user.UserInfoActivity;
import com.monsterlin.blives.bean.BUser;
import com.monsterlin.blives.navfragment.AppFragment;
import com.monsterlin.blives.navfragment.CampusFragment;
import com.monsterlin.blives.navfragment.MainFragment;
import com.monsterlin.blives.navfragment.SceneryFragment;
import com.monsterlin.blives.utils.CheckNetWork;
import com.monsterlin.blives.utils.SnackbarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
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

    @InjectView(R.id.tv_netWork)
    TextView tv_netWork;


    private ActionBarDrawerToggle toggle;

    private boolean isNet;

    private  Menu menu;
    private long exitTime = 0;
    private BmobUser bmobUser;
    private String  currentId ;

    private String[] tags = new String[]{"MainFragment","SceneryFragment","CampusFragment","AppFragment"};
    private int curIndex = -1;

    @InjectView(R.id.cl)
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initToolBar(mToolBar,"BLives",false);
        checkNet();
    }


    private void checkNet() {
        isNet=new CheckNetWork().isNetworkAvailable(this);
        if (isNet){
            checkVersion();
            init();  //初始化用户数据
            initMain(); //初始化主页面
            initEvent();  //初始化点击事件
        }else {
            //无网络连接
            new CheckNetWork().showNetDialog(this);
            tv_netWork.setVisibility(View.VISIBLE);
        }
    }

    private void checkVersion() {
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);
    }

    private void init() {
        bmobUser=BmobUser.getCurrentUser(this);
        updateleftUserData();
    }

    private void updateleftUserData() {
        bmobUser=BmobUser.getCurrentUser(this);
        if (bmobUser!=null){
            currentId=bmobUser.getObjectId();
            BmobQuery<BUser> query = new BmobQuery<>();
            query.getObject(MainActivity.this, currentId, new GetListener<BUser>() {
                @Override
                public void onSuccess(BUser bUser) {

                    Log.e("Buser",""+bUser.toString());

                    if (bUser!=null){
                        tv_nick.setText(bUser.getNick());
                        tv_depart.setText(bUser.getDepart());
                        if (TextUtils.isEmpty(bUser.getFigureurl())){
                            if (bUser.getUserPhoto()!=null){
                                ImageLoader.getInstance().displayImage(bUser.getUserPhoto().getFileUrl(MainActivity.this),iv_userphoto);
                            }
                        }else {
                            ImageLoader.getInstance().displayImage(bUser.getFigureurl(),iv_userphoto);
                        }
                    }


                }

                @Override
                public void onFailure(int i, String s) {
                    showToast("接收用户数据异常："+s);
                }
            });
        }else {
            tv_nick.setText("点击头像登陆");
            tv_depart.setText("");
            iv_userphoto.setImageResource(R.drawable.ic_bzu);
        }
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_userphoto:
                if(null ==bmobUser){
                    nextActivity(LoginActivity.class);
                    finish();
                }else {
//                    String objectId = bmobUser.getObjectId();
                    Intent showUserIntent = new Intent(this,UserInfoActivity.class);
//                    showUserIntent.putExtra("objectId",objectId);
                    startActivity(showUserIntent);
                }
                break;
        }
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
                menu.getItem(0).setChecked(true);
                setSelect(0);
                break;
            case R.id.item_scenery:
                menu.getItem(1).setChecked(true);
                setSelect(1);
                break;
            case R.id.item_campus:
                menu.getItem(2).setChecked(true);
                setSelect(2);
                break;
            case R.id.item_app:
                menu.getItem(3).setChecked(true);
                setSelect(3);
                break;

            case R.id.item_map:
                nextActivity(MapActivity.class);
                break;
            case R.id.item_setting:
                nextActivity(SettingActivity.class);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setSelect(int i)
    {
    if (curIndex == i)
        return;
        else {
        curIndex = i;
    }
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tags[i]);
        if (fragment == null){
            switch (i){
                case 0:
                    fragment = new MainFragment();
                    break;
                case 1:
                    fragment = new SceneryFragment();
                    break;
                case 2:
                    fragment = new CampusFragment();
                    break;
                case 3:
                    fragment = new AppFragment();
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.frame_main,fragment,tags[i]).commit();
        }
        for (int j = 0; j < 4;j++) {
            Fragment f = getSupportFragmentManager().findFragmentByTag(tags[j]);
            if (f!=null){
                getSupportFragmentManager().beginTransaction().hide(f).commit();
            }
        }
        getSupportFragmentManager().beginTransaction().show(fragment).commit();

    }



    @Override
    protected void onResume() {
        super.onResume();
        updateleftUserData();
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {

            SnackbarUtil.ShortSnackbar(view,"再按一次退出程序",SnackbarUtil.Alert).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
