package com.monsterlin.blives.navfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.monsterlin.blives.R;

/**
 * 校园广场
 * Created by monsterLin on 2016/2/16.
 */
public class SquareFragment extends Fragment {

    private Context mContext ;
    private MapView mapView = null;
    private BaiduMap map ;

    Marker markers; //标记点

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initMap();
    }

    private void initMap() {
        map =mapView.getMap();

        /**
         * 设置地图的中心点
         */
        LatLng latLng = new LatLng(37.391485,117.991591) ; //地理坐标基本数据结构  -->纬度/经度
        MapStatus mapStatus =new MapStatus.Builder()
                .target(latLng)  //中心点
                .zoom(17) //缩放级别
                .build();

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        map.setMapStatus(mapStatusUpdate);


        /**
         * 设置地图的Mark点
         */
        LatLng libraryPoint = new LatLng(37.391485,117.991591);  //图书馆坐标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_map);
        OverlayOptions option = new MarkerOptions()
                .position(libraryPoint)
                .icon(bitmap)
                .title("图书馆");

        map.addOverlay(option);


        /**
         * mark的点击事件
         */
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //TODO Test
                LatLng libraryPoint = new LatLng(37.391485,117.991591);  //图书馆坐标

                //TODO view需要进行优化
                View popView = LayoutInflater.from(mContext).inflate(R.layout.view_map_pop, null);
                TextView tv_info = (TextView) popView.findViewById(R.id.tv_popview);
                tv_info.setText("图书馆");

                /**
                 * 弹出框
                 */
                InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popView), libraryPoint, -70, new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        Toast.makeText(mContext,"弹出框被点击",Toast.LENGTH_SHORT).show();
                    }
                });


                /**
                 * 控制mark的显示和隐藏
                 */
                if(markers==null){
                    markers=marker;
                    map.showInfoWindow(infoWindow);

                }else {
                    if(markers.equals(marker)){
                        map.hideInfoWindow();
                        markers=null;
                    }
                }

                return true;
            }

        });

        /**
         * 地图点击事件
         */
        map.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.hideInfoWindow();
                markers=null;
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }



    private void initView(View view) {
        mapView = (MapView)view.findViewById(R.id.bmapView);
        
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
}
