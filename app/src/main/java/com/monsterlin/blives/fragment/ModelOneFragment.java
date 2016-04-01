package com.monsterlin.blives.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;

/**
 * 说明：
 * 布局的名字和对应的类名字为测试的名字，最后基本布局确定后，通过重构的形式更改即可
 * Created by monsterLin on 2016/2/16.
 */
public class ModelOneFragment extends Fragment {


    /**
     * 创建视图，返回View对象
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modelone,container,false);
        return view;
    }

}
