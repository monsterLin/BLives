package com.monsterlin.blives.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.adapter.viewholder.NewsFootVHolder;
import com.monsterlin.blives.adapter.viewholder.UnionVHolder;

import java.util.List;

/**
 * Created by monsterLin on 6/30/2016.
 */
public class CommonUnionAdapter  <T>  extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public List<T> unionList ;
    public Context mContext ;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public UnionVHolder unionVHolder;
    public OnItemClickListener mOnItemClickListener;

    public CommonUnionAdapter(List<T> unionList, Context mContext) {
        this.unionList = unionList;
        this.mContext = mContext;
    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_union, parent,  false);
            unionVHolder  = new UnionVHolder(view);
            return unionVHolder;

        } else if (viewType == TYPE_FOOTER) {
            if(unionList.size()!=0){
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent,false);
                NewsFootVHolder footVHolder = new NewsFootVHolder(view);
                return footVHolder;
            }else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent, false);
                TextView tv =(TextView)view.findViewById(R.id.tv_foot);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
                tv.setText("已到最底部");
                NewsFootVHolder footVHolder = new NewsFootVHolder(view);
                return footVHolder;
            }

        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return unionList.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

}
