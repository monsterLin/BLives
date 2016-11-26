package com.monsterlin.blives.mainfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.monsterlin.blives.MainActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.activity.app.AppDetailActivity;
import com.monsterlin.blives.activity.user.LoginActivity;
import com.monsterlin.blives.activity.user.UserInfoActivity;
import com.monsterlin.blives.adapter.AppAdapter;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.bean.App;
import com.monsterlin.blives.bean.BUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by monsterLin on 2016/9/23.
 */

public class MeFragment extends Fragment {
    private Context mContext ;

    private RecyclerView rv_app;
    private AppAdapter adapter;
    private List<App> mList ;

    private CircleImageView iv_userphoto ;
    private TextView tv_nick ,tv_depart ;

    private BmobUser bmobUser;
    private String  currentId ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        initView(view);
        init();
        initData();
        initAdapter();
        initEvent();
        return view;
    }

    private void initEvent() {
        iv_userphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null ==bmobUser){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                    getActivity().finish();
                }else {
//                    String objectId = bmobUser.getObjectId();
                    Intent showUserIntent = new Intent(getActivity(),UserInfoActivity.class);
//                    showUserIntent.putExtra("objectId",objectId);
                    startActivity(showUserIntent);
                }
            }
        });
    }

    private void init() {
        bmobUser=BmobUser.getCurrentUser(getActivity());
        updateUserData();
    }

    private void updateUserData() {
        bmobUser=BmobUser.getCurrentUser(getActivity());
        if (bmobUser!=null){
            currentId=bmobUser.getObjectId();
            BmobQuery<BUser> query = new BmobQuery<>();
            query.getObject(getActivity(), currentId, new GetListener<BUser>() {
                @Override
                public void onSuccess(BUser bUser) {

                    Log.e("Buser",""+bUser.toString());

                    if (bUser!=null){
                        tv_nick.setText(bUser.getNick());
                        tv_depart.setText(bUser.getDepart());
                        if (TextUtils.isEmpty(bUser.getFigureurl())){
                            if (bUser.getUserPhoto()!=null){
                                ImageLoader.getInstance().displayImage(bUser.getUserPhoto().getFileUrl(getActivity()),iv_userphoto);
                            }
                        }else {
                            ImageLoader.getInstance().displayImage(bUser.getFigureurl(),iv_userphoto);
                        }
                    }


                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(getActivity(),"接收用户数据异常："+s,Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            tv_nick.setText("点击头像登陆");
            tv_depart.setText("");
            iv_userphoto.setImageResource(R.drawable.ic_bzu);
        }

    }

    private void initAdapter() {
        adapter=new AppAdapter(mContext,mList);
        rv_app.setAdapter(adapter);
        rv_app.setLayoutManager(new GridLayoutManager(mContext,4));

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void OnItemClick(int position, View view) {


                Intent appIntent = new Intent(mContext,AppDetailActivity.class);
                appIntent.putExtra("appUrl",adapter.getApp(position).getAppurl());
                appIntent.putExtra("appName",adapter.getApp(position).getAppname());
                startActivity(appIntent);

            }

            @Override
            public void OnItemLongClick(int position, View view) {

            }
        });

    }

    private void initData() {
        mList=new ArrayList<>();
        mList.add(new App(R.mipmap.ic_launcher,"滨州学院","http://www.bzu.edu.cn/"));
        mList.add(new App(R.drawable.ic_app_wx,"微信首页","http://weixin.bzu.edu.cn/html/xxgk/index.htm"));
        mList.add(new App(R.drawable.ic_app_inform,"通知公告","http://weixin.bzu.edu.cn/wp/?cat=10"));
        mList.add(new App(R.drawable.ic_app_cet,"四六级","http://weixin.bzu.edu.cn/html/cet"));
        mList.add(new App(R.drawable.ic_app_onekey,"一卡通","http://m.xzxyun.com/download/main"));
        mList.add(new App(R.drawable.ic_app_jobs,"招生就业","http://weixin.bzu.edu.cn/wp/?cat=11"));
        mList.add(new App(R.drawable.ic_app_borrow,"借阅","http://10.9.10.3/dzjs/login_form.asp"));
        mList.add(new App(R.drawable.ic_app_sight,"校园风光","http://weixin.bzu.edu.cn/wp/?cat=12"));
        mList.add(new App(R.drawable.ic_app_cz,"院系传真","http://weixin.bzu.edu.cn/wp/?cat=13"));
        mList.add(new App(R.drawable.ic_app_board,"留言板","http://weixin.bzu.edu.cn/wp/?page_id=52"));
        mList.add(new App(R.drawable.ic_app_ts,"师生风采","http://weixin.bzu.edu.cn/wp/?cat=14"));
        mList.add(new App(R.drawable.ic_app_topics,"互动话题","http://weixin.bzu.edu.cn/wp/?cat=17"));
        mList.add(new App(R.drawable.ic_app_about,"关于","file:///android_asset/web/showwx.html"));
    }

    private void initView(View view) {
        rv_app= (RecyclerView) view.findViewById(R.id.rv_app);
        tv_nick= (TextView) view.findViewById(R.id.tv_nick);
        tv_depart= (TextView) view.findViewById(R.id.tv_depart);
        iv_userphoto= (CircleImageView) view.findViewById(R.id.iv_userphoto);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
