package com.monsterlin.blives.navfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.BUser;
import com.monsterlin.blives.entity.Campus;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 校园活动
 * Created by monsterLin on 2016/2/16.
 */
public class CampusFragment extends Fragment {

    private Context mContext ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campus,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       BUser bUser =  BmobUser.getCurrentUser(mContext, BUser.class);
        Campus campus =new Campus();
        campus.setbUser(bUser);
        campus.setTitle("测试标题");
        campus.setContent("测试内容");
        campus.setCampusType("社交");
        campus.setCampusObject("在校学生");
        campus.setCampusPlace("一餐广场");
        campus.setCampusDate("周五");

        campus.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(mContext,"FAILDED"+s,Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
}
