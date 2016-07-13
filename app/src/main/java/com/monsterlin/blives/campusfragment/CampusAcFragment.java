package com.monsterlin.blives.campusfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;

/**
 * 社团活动
 * Created by monsterLin on 6/29/2016.
 */
public class CampusAcFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_ac,container,false);
        return view;
    }
}
