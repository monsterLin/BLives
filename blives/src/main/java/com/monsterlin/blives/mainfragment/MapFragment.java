package com.monsterlin.blives.mainfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.monsterlin.blives.bean.MarkPoint;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by monsterLin on 2016/9/23.
 */

public class MapFragment extends Fragment {

    private MapView mapView = null;
    private BaiduMap map;
    private Marker markers;
    private BmobQuery<MarkPoint> markQuery;
    private LocationClient locationClient = null;
    private FloatingActionButton fab_loc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initView(view);
        initData();
        initLocation();
        initEvent();
        return view;
    }

    private void initEvent() {
        fab_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locationClient == null) {
                    return;
                }
                if (locationClient.isStarted()) {
                    TastyToast.makeText(getActivity(), "定位已关闭", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    locationClient.stop();
                } else {
                    TastyToast.makeText(getActivity(), "正在定位中", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    locationClient.start();
                    /*
                     *当所设的整数值大于等于1000（ms）时，定位SDK内部使用定时定位模式。
                     *调用requestLocation( )后，每隔设定的时间，定位SDK就会进行一次定位。
                     *如果定位SDK根据定位依据发现位置没有发生变化，就不会发起网络请求，
                     *返回上一次定位的结果；如果发现位置改变，就进行网络请求进行定位，得到新的定位结果。
                     *定时定位时，调用一次requestLocation，会定时监听到定位结果。
                     */
                    locationClient.requestLocation();
                }
            }

        });
    }

    private void initLocation() {
        locationClient = new LocationClient(getActivity());
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置高精度定位定位模式
        option.setCoorType("bd09ll");//设置百度经纬度坐标系格式
        //option.setScanSpan(UPDATE_TIME);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(true);//反编译获得具体位置，只有网络定位才可以
        locationClient.setLocOption(option);  //讲option的初始化信息添加到客户端

        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }

                Double Latitude = location.getLatitude();//纬度
                Double Longitude = location.getLongitude(); //精度

                String AddStr = location.getAddrStr();  //详细地址

                TastyToast.makeText(getActivity(), "你当前位置：" + AddStr, TastyToast.LENGTH_SHORT, TastyToast.INFO);

                LatLng locationLbs = new LatLng(Latitude, Longitude);
                //设置地图默认展开缩放大小为18
                MapStatus status = new MapStatus.Builder().zoom(18.0f).target(locationLbs).build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(status);

                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_map_location_arrows);
                OverlayOptions option = new MarkerOptions()
                        .position(locationLbs)
                        .icon(bitmap)
                        .title(AddStr);

                map.addOverlay(option);

                map.animateMapStatus(mapStatusUpdate);


            }
        });
    }

    /**
     * 初始化坐标数据
     */
    private void initData() {
        markQuery = new BmobQuery<>();
        markQuery.findObjects(getActivity(), new FindListener<MarkPoint>() {
            @Override
            public void onSuccess(List<MarkPoint> list) {
                if (null != list) {
                    initMap(list);
                }

            }

            @Override
            public void onError(int i, String s) {
                TastyToast.makeText(getActivity(), "接收数据异常", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
            }
        });
    }

    /**
     * 初始化地图
     *
     * @param list
     */
    private void initMap(List<MarkPoint> list) {
        map = mapView.getMap();
        /**
         * 设置地图的中心点
         */
        LatLng latLng = new LatLng(37.391485, 117.991591); //地理坐标基本数据结构  -->纬度/经度
        final MapStatus mapStatus = new MapStatus.Builder()
                .target(latLng)  //中心点
                .zoom(17) //缩放级别
                .build();

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        map.setMapStatus(mapStatusUpdate);


        /**------------------------Mark点为输出数据库中的信息--------------------------------**/
        /**
         * 设置地图的Mark点
         */
        for (int i = 0; i < list.size(); i++) {
            MarkPoint markPoint = list.get(i);
            String latitude = markPoint.getLatitude();
            String longitude = markPoint.getLongitude();
            String plcae = markPoint.getPlace();
            //String introbuce =markPoint.getIntrobuce();

            LatLng libraryPoint = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_map_mark);
            OverlayOptions option = new MarkerOptions()
                    .position(libraryPoint)
                    .icon(bitmap)
                    .title(plcae);

            map.addOverlay(option);

        }

        /**----------------------------------Mark点的点击事件--------------------------------------**/

        /**
         * mark的点击事件
         */
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {

                LatLng libraryPoint = marker.getPosition();

                View popView = LayoutInflater.from(getActivity()).inflate(R.layout.view_map_pop, null);
                TextView tv_info = (TextView) popView.findViewById(R.id.tv_popview);
                tv_info.setText(marker.getTitle());

                /**
                 * 弹出框
                 */
                InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popView), libraryPoint, -70, new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        TastyToast.makeText(getActivity(), marker.getTitle(), TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    }
                });

                /**
                 * 控制mark的显示和隐藏
                 */
                if (markers == null) {
                    markers = marker;
                    map.showInfoWindow(infoWindow);
                } else {
                    if (markers.equals(marker)) {
                        map.hideInfoWindow();
                        markers = null;
                    } else {
                        map.hideInfoWindow();
                        map.showInfoWindow(infoWindow);
                        markers = marker;
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
                markers = null;
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }


    private void initView(View view) {
        mapView = (MapView) view.findViewById(R.id.bmapView);
        fab_loc = (FloatingActionButton) view.findViewById(R.id.fab_loc);
    }
}
