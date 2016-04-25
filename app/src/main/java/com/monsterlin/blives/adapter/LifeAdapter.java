package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.Life;

import java.util.List;

/**
 * Created by monsterLin on 2016/4/22.
 */
public class LifeAdapter extends RecyclerView.Adapter<LifeViewHolder>{

    private Context mContext ;
    private List<Life> mList ;
    private LayoutInflater mInflater ;

    /**
     * 声明一个接口，用于实现点击事件
     */
    public interface  OnItemClickListener{
        void OnItemClick(int position, View view);
        void OnItemLongClick(int position, View view);
    }

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
    public LifeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_life,parent,false);
        LifeViewHolder lifeViewHolder= new LifeViewHolder(view);
        return lifeViewHolder;
    }

    @Override
    public void onBindViewHolder(final LifeViewHolder holder, int position) {
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

    /**
     * 得到单个item的数据
     * @param position
     * @return
     */
    public Life getLife(int position){
        return mList.get(position);
    }
}

class LifeViewHolder extends RecyclerView.ViewHolder{
    ImageView iv_life;
    TextView tv_life;

    public LifeViewHolder(View itemView) {
        super(itemView);
        iv_life= (ImageView) itemView.findViewById(R.id.iv_life);
        tv_life= (TextView) itemView.findViewById(R.id.tv_life);
    }
}