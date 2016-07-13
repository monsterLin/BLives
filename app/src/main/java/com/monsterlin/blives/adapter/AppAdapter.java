package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.adapter.viewholder.AppVHolder;
import com.monsterlin.blives.bean.App;

import java.util.List;

/**
 * Created by monsterLin on 2016/4/22.
 */
public class AppAdapter extends RecyclerView.Adapter<AppVHolder>{

    private Context mContext ;
    private List<App> mList ;
    private LayoutInflater mInflater ;


    private OnItemClickListener mOnItemClickListener;

    public  void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }


    public AppAdapter(Context mContext, List<App> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public AppVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_app,parent,false);
       AppVHolder AppVHolder = new AppVHolder(view);
        return AppVHolder;
    }

    @Override
    public void onBindViewHolder(final AppVHolder holder, int position) {
        holder.tv_life.setText(mList.get(position).getAppname());
        holder.iv_life.setImageResource(mList.get(position).getAppicon());

        if (mOnItemClickListener!=null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int LayoutPosition=holder.getLayoutPosition(); //得到布局的position
                    mOnItemClickListener.OnItemClick(LayoutPosition,holder.itemView);

                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int LayoutPosition=holder.getLayoutPosition(); //得到布局的position
                    mOnItemClickListener.OnItemLongClick(LayoutPosition,holder.itemView);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public App getApp(int position){
        return mList.get(position);
    }
}

