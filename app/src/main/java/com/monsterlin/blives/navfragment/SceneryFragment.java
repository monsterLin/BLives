package com.monsterlin.blives.navfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.widget.ProgressWebView;

/**
 * Created by monsterLin on 6/27/2016.
 */
public class SceneryFragment extends Fragment {

    private ProgressWebView wv_scenery ;
    private static final String appUrl ="http://720yun.com/wx/t/ed128ma89cv?pano_id=470704l";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenery,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        wv_scenery= (ProgressWebView) view.findViewById(R.id.wv_scenery);
        wv_scenery.getSettings().setJavaScriptEnabled(true);
        wv_scenery.loadUrl(appUrl);
    }



}
