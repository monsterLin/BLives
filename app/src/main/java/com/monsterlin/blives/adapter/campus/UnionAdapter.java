package com.monsterlin.blives.adapter.campus;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.common.CommonUnionAdapter;
import com.monsterlin.blives.adapter.viewholder.NewsItemVHolder;
import com.monsterlin.blives.adapter.viewholder.UnionVHolder;
import com.monsterlin.blives.bean.Union;
import com.monsterlin.blives.utils.PhoneUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by monsterLin on 6/30/2016.
 */
public class UnionAdapter extends CommonUnionAdapter<Union> {


    public UnionAdapter(List<Union> unionList, Context mContext) {
        super(unionList, mContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof UnionVHolder) {

            if (unionList.get(position).getLogo()!=null){
                BmobFile imgFile = unionList.get(position).getLogo();
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.ic_news_default)
                        .showImageOnFail(R.drawable.ic_default_error_showimg)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                ImageLoader.getInstance().displayImage(imgFile.getFileUrl(mContext),unionVHolder.iv_union_logo,options);
            }else {
                unionVHolder.iv_union_logo.setImageResource(R.drawable.ic_bzu);
            }


            unionVHolder.tv_director.setText("理事长："+unionList.get(position).getDirector());
            unionVHolder.tv_community.setText("社团名称："+unionList.get(position).getCommunity());
            unionVHolder.iv_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    Toast.makeText(mContext, "收藏被点击", Toast.LENGTH_SHORT).show();
                }
            });

            unionVHolder.iv_tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneUtil.TelCALL(mContext,unionList.get(position).getDirectortel());
                }
            });


            if (mOnItemClickListener!=null){

                holder.itemView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        int LayoutPosition=holder.getLayoutPosition();
                        mOnItemClickListener.OnItemClick(LayoutPosition,holder.itemView);

                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int LayoutPosition=holder.getLayoutPosition();
                        mOnItemClickListener.OnItemLongClick(LayoutPosition,holder.itemView);
                        return false;
                    }
                });
            }
        }

    }


    public Union getUnion(int position){
        return unionList.get(position);
    }


}
