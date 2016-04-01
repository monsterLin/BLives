package com.monsterlin.blives.mainfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;

/**
 * 主内容_教务信息
 * Created by monsterLin on 2016/4/1.
 */
public class OffnewsFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_offnews,container,false);
        return view;
    }
}
