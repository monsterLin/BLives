//package com.monsterlin.blives.campusfragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.monsterlin.blives.MainActivity;
//import com.monsterlin.blives.R;
//import com.yyydjk.library.BannerLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 系院活动
// * Created by monsterLin on 6/29/2016.
// */
//public class DepartAcFragment extends Fragment {
//
//    private Context mContext ;
//    private  BannerLayout bannerLayout;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View  view = inflater.inflate(R.layout.fragment_ac,container,false);
//        bannerLayout = (BannerLayout) view.findViewById(R.id.banner);
//         List<String> urls = new ArrayList<>();
//        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
//        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
//        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
//        bannerLayout.setViewUrls(urls);
//
//        initEvent();
//        return view;
//    }
//
//    private void initEvent() {
//        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        mContext=activity;
//    }
//}
