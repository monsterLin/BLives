package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.dao.OnItemClickListener;
import com.monsterlin.blives.adapter.viewholder.LifeVHolder;
import com.monsterlin.blives.entity.Life;

import java.util.List;

/**
 * Created by monsterLin on 2016/4/22.
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeVHolder>{

    private Context mContext ;
    private List<Life> mList ;
    private LayoutInflater mInflater ;


    private OnItemClickListener mOnItemClickListener;

    public  void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }


    public LifeAdapter(Context mContext, List<Life> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public LifeVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_life,parent,false);
       LifeVHolder lifeVHolder = new LifeVHolder(view);
        return lifeVHolder;
    }

    @Override
    public void onBindViewHolder(final LifeVHolder holder, int position) {
        holder.tv_life.setText(mList.get(position).getLifename());
        holder.iv_life.setImageResource(mList.get(position).getLifeicon());

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


    public Life getLife(int position){
        return mList.get(position);
    }
}

