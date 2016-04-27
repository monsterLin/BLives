package com.monsterlin.blives.navfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.monsterlin.blives.entity.MarkPoint;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 校园广场
 * Created by monsterLin on 2016/2/16.
 */
public class SquareFragment extends Fragment {

    private Context mContext ;
    private MapView mapView = null;
    private BaiduMap map ;

    Marker markers; //标记点
    private BmobQuery<MarkPoint> markQuery ;

    private Button btn_lbs;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_square,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initLocation();
        initEvent();
    }

    /**
     * 定位初始化
     */
    private void initLocation() {


    }

    private void initEvent() {
        btn_lbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showToast("定位");
            }
        });

    }


    //TODO  mark点的及时刷新问题

    /**
     * 初始化坐标数据
     */
    private void initData() {
        markQuery=new BmobQuery<>();
        markQuery.findObjects(mContext, new FindListener<MarkPoint>() {
            @Override
            public void onSuccess(List<MarkPoint> list) {
                if (null!=list){
                    initMap(list);
                }

            }

            @Override
            public void onError(int i, String s) {
                showToast("接受数据库数据异常："+s);
            }
        });
    }

    /**
     * 初始化地图
     * @param list
     */
    private void initMap(List<MarkPoint> list) {
        map =mapView.getMap();
        /**
         * 设置地图的中心点
         */
        LatLng latLng = new LatLng(37.391485,117.991591) ; //地理坐标基本数据结构  -->纬度/经度
        final MapStatus mapStatus =new MapStatus.Builder()
                .target(latLng)  //中心点
                .zoom(17) //缩放级别
                .build();

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        map.setMapStatus(mapStatusUpdate);


        /**------------------------Mark点为输出数据库中的信息--------------------------------**/
        /**
         * 设置地图的Mark点
         */
        for (int i =0;i<list.size();i++){
            MarkPoint markPoint =list.get(i);
            String latitude = markPoint.getLatitude();
            String longitude = markPoint.getLongitude();
            String plcae =markPoint.getPlace();
            //String introbuce =markPoint.getIntrobuce();

            LatLng libraryPoint = new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.ic_mark);
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

                LatLng libraryPoint =marker.getPosition();

                View popView = LayoutInflater.from(mContext).inflate(R.layout.view_map_pop, null);
                TextView tv_info = (TextView) popView.findViewById(R.id.tv_popview);
                tv_info.setText(marker.getTitle());

                /**
                 * 弹出框
                 */
                InfoWindow infoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popView), libraryPoint, -70, new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        Toast.makeText(mContext,marker.getTitle(),Toast.LENGTH_SHORT).show();
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
                markers=null;
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }


    /**
     * 实例化控件
     * @param view
     */
    private void initView(View view) {
        mapView = (MapView)view.findViewById(R.id.bmapView);
        btn_lbs= (Button) view.findViewById(R.id.btn_lbs);
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

    /**
     * 打印Toast
     * @param s
     */
    public void showToast(String s){
        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
    }
}
