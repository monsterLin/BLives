package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.Campus;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 校园活动适配器
 * Created by monsterLin on 2016/4/25.
 */
public class CampusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    private Context mContext;
    private List<Campus> campusList;

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


    public CampusAdapter(Context mContext, List<Campus> campusList) {
        this.mContext = mContext;
        this.campusList = campusList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_campus, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            if(campusList.size()!=0){
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent,
                        false);
                return new FootViewHolder(view);
            }else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_foot, parent,
                        false);
                TextView tv =(TextView)view.findViewById(R.id.tv_foot);
                ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);
                tv.setText("已到最底部");
                return new FootViewHolder(view);
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            if (campusList.get(position).getbUser().getUserPhoto()!=null){
                BmobFile userPhoto = campusList.get(position).getbUser().getUserPhoto();
                userPhoto.loadImage(mContext,((ItemViewHolder) holder).iv_userphoto);
            }else {
                ((ItemViewHolder) holder).iv_userphoto.setImageResource(R.drawable.ic_bzu);
            }

           ((ItemViewHolder) holder).tv_nick.setText(campusList.get(position).getbUser().getNick());
            ((ItemViewHolder) holder).tv_title.setText(campusList.get(position).getTitle());
            ((ItemViewHolder) holder).tv_date.setText(campusList.get(position).getUpdatedAt());
            ((ItemViewHolder) holder).tv_type.setText(campusList.get(position).getCampusType());


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
    }


    /**
     * 返回单个实体类对象
     * @param position
     * @return
     */
    public Campus getCampusData(int position){
        return campusList.get(position);
    }

    @Override
    public int getItemCount() {
        return  campusList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

         CircleImageView iv_userphoto ;
         TextView tv_nick,tv_date,tv_type,tv_title;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_userphoto= (CircleImageView) itemView.findViewById(R.id.iv_userphoto);
            tv_nick= (TextView) itemView.findViewById(R.id.tv_nick);
            tv_date= (TextView) itemView.findViewById(R.id.tv_date);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
        }

    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
