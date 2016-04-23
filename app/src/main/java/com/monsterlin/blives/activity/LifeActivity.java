package com.monsterlin.blives.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.Life;
import com.monsterlin.mlbasetools.recyclerview.CommonAdapter;
import com.monsterlin.mlbasetools.recyclerview.OnItemClickListener;
import com.monsterlin.mlbasetools.viewholder.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 主题选择界面
 * Created by monsterLin on 2016/4/14.
 */
public class LifeActivity extends BaseActivity{

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.rv_life)
     RecyclerView rv_life ;

    private List<Life> mList ;

    ImageLoader imageLoader;

    private String app_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        initActivityButterKnife(this);
        imageLoader = ImageLoader.getInstance();
        initToolBar(toolbar,"生活应用",true);
        initData();
        initAdapter();
    }

    private void initAdapter() {
        rv_life.setLayoutManager(new GridLayoutManager(this,3));

        final CommonAdapter<Life> adapter=new CommonAdapter<Life>(this,R.layout.item_life,mList) {
            @Override
            public void convert(ViewHolder holder, Life life) {
                holder.setText(R.id.tv_life,life.getLifename());

                app_url=life.getApp_url();

                ImageView iv_life = holder.getView(R.id.iv_life);
                imageLoader.displayImage(life.getApp_url(), iv_life);
            }
        };

        rv_life.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("app_url",app_url);
                nextActivity(LifeDetailActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    /**
     * TODO 最初版本数据为固定状态
     * TODO 后续版本更新
     */
    private void initData() {
        mList=new ArrayList<>();
        for (int i =0;i<8;i++){
            mList.add(new Life(R.mipmap.ic_launcher,"bzu","http://www.baidu.com"));
        }

    }


}
