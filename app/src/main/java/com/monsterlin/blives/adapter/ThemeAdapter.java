package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.Theme;
import com.rey.material.widget.CheckBox;

import java.util.List;

/**
 * 主题选择适配器
 * Created by monsterLin on 2016/4/18.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeHolder> {

    private List<Theme> mList ;
    private Context mContext ;
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

    public ThemeAdapter(List<Theme> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ThemeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_theme,parent,false);
        ThemeHolder themeHolder=new ThemeHolder(view);
        return themeHolder;
    }

    @Override
    public void onBindViewHolder(final ThemeHolder holder, int position) {
        holder.iv_color.setBackgroundResource(mList.get(position).getColor());
        holder.tv_colorname.setText(mList.get(position).getColorname());


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
}

//TODO    主题选择的按钮需要优化

class  ThemeHolder extends RecyclerView.ViewHolder {
     ImageView iv_color;
     TextView tv_colorname ;
     CheckBox cb_use;


    public ThemeHolder(View itemView) {
        super(itemView);
        iv_color= (ImageView) itemView.findViewById(R.id.iv_color);
        tv_colorname= (TextView) itemView.findViewById(R.id.tv_colorname);
        cb_use= (CheckBox) itemView.findViewById(R.id.cb_use);

    }
}