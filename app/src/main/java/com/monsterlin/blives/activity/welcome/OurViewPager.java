package com.monsterlin.blives.activity.welcome;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.MainActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.changeadapter.WelWpadapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 引导页面
 * Created by monsterLin on 2016/4/27.
 */
public class OurViewPager extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private List<View> viewList;
    private View view1,view2,view3,view4;

    private TextView go;
    private WelWpadapter adapter;

    private ImageView[] dots;
    private int[] ids = { R.id.iv0, R.id.iv1, R.id.iv2, R.id.iv3 };//4个导航点

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpager);
        initialize();
        initDots();
        adapter = new WelWpadapter(viewList);
        viewPager.setAdapter(adapter);
    }

    private void initialize() {
        viewPager =(ViewPager) findViewById(R.id.viewPager);

        view1 = View.inflate(this, R.layout.view1_of_pager, null);
        view2 = View.inflate(this, R.layout.view2_of_pager, null);
        view3 = View.inflate(this, R.layout.view3_of_pager, null);
        view4 = View.inflate(this, R.layout.view4_of_pager, null);

        go=(TextView) view4.findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              nextActivity(MainActivity.class);
                finish();
            }
        });

        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        viewPager.setOnPageChangeListener(this);
    }


    private void initDots() {
        dots = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 当Viewpager被选择,即正在显示
     * @param   position 当前显示的是第几个
     */
    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < ids.length; i++) {
            if (position == i) {
                dots[i].setImageResource(R.drawable.bg_point_selected);
            } else {
                dots[i].setImageResource(R.drawable.bg_point);
            }
        }
    }

}
