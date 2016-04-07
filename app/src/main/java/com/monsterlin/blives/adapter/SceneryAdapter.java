package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.Scenery;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 图片适配器
 * Created by monsterLin on 2016/4/7.
 */
public class SceneryAdapter extends RecyclerView.Adapter<SceneryAdapter.ItemView> {

    private List<Scenery> mList ;
    private Context mContext ;
    private LayoutInflater mInflater;

    public SceneryAdapter(List<Scenery> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=mInflater.inflate(R.layout.item_scenery,parent,false);
        ItemView viewHolder=new ItemView(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemView holder, int position) {

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();

        BmobFile img = mList.get(position).getSceneryimg();
       // img.loadImage(mContext,holder.iv_img,width/2,200);
        img.loadImageThumbnail(mContext,holder.iv_img,width/2,200);
        holder.tv_intro.setText(mList.get(position).getSceneryintro());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ItemView extends RecyclerView.ViewHolder{

        ImageView iv_img ;
        TextView tv_intro;

        public ItemView(View itemView) {
            super(itemView);

            iv_img= (ImageView) itemView.findViewById(R.id.iv_img);
            tv_intro= (TextView) itemView.findViewById(R.id.tv_intro);
        }
}



}

