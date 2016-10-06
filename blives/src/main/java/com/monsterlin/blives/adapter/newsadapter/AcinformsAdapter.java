package com.monsterlin.blives.adapter.newsadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.common.CommonAdapter;
import com.monsterlin.blives.adapter.viewholder.NewsItemVHolder;
import com.monsterlin.blives.bean.Acinforms;
import com.monsterlin.blives.utils.MTextUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * 学术通知适配器
 * Created by monsterLin on 2016/4/5.
 */
public class AcinformsAdapter extends CommonAdapter<Acinforms> {

    public AcinformsAdapter(List<Acinforms> newsList, Context mContext) {
        super(newsList, mContext);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsItemVHolder) {

            if (newsList.get(position).getNewsimg()!=null){
                BmobFile imgFile = newsList.get(position).getNewsimg();
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnLoading(R.drawable.ic_news_default)
                        .showImageOnFail(R.drawable.ic_default_error_showimg)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .bitmapConfig(Bitmap.Config.RGB_565)
                        .build();

                ImageLoader.getInstance().displayImage(imgFile.getFileUrl(mContext),newsItemVHolder.iv_show_img,options);
            }else {
                newsItemVHolder.iv_show_img.setImageResource(R.drawable.ic_news_default);
            }


            newsItemVHolder.tv_title.setText(MTextUtils.textFormat(newsList.get(position).getTitle()));
            newsItemVHolder.tv_date.setText(MTextUtils.dateFormat(newsList.get(position).getNewsdate().getDate()));
            newsItemVHolder.tv_from.setText("滨州学院");


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


    public Acinforms getAcinform(int position){
        return newsList.get(position);
    }


}
