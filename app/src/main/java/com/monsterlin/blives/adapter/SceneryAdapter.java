package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.Scenery;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by monsterLin on 2016/4/14.
 */
public class SceneryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;


    private List<Scenery> mList ;
    private Context mContext ;


    public SceneryAdapter(List<Scenery> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

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


    // RecyclerView的count设置为数据总条数+ 1（footerView）

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_scenery, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            if(mList.size()!=0){
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

            if (mList.get(position).getSceneryimg()!=null){
                WindowManager wm = (WindowManager) mContext
                        .getSystemService(Context.WINDOW_SERVICE);

                int width = wm.getDefaultDisplay().getWidth();

                BmobFile img = mList.get(position).getSceneryimg();
                img.loadImageThumbnail(mContext,  ((ItemViewHolder) holder).iv_img,width/2,200);
            }else {
                ((ItemViewHolder) holder).iv_img.setImageResource(R.drawable.ic_nopic);
            }

            ((ItemViewHolder) holder).tv_intro.setText(mList.get(position).getSceneryintro());


            if (mOnItemClickListener!=null){

                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int LayoutPosition=holder.getLayoutPosition(); //得到布局的position
                        mOnItemClickListener.OnItemClick(LayoutPosition,holder.itemView);

                    }
                });

                //longclickListener
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
     * 得到item的信息
     * @param position
     * @return
     */
    public Scenery getScenery(int position){
        return mList.get(position);
    }






    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img ;
        TextView tv_intro;

        public ItemViewHolder(View itemView) {
            super(itemView);

            iv_img= (ImageView) itemView.findViewById(R.id.iv_img);
            tv_intro= (TextView) itemView.findViewById(R.id.tv_intro);
        }

    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
