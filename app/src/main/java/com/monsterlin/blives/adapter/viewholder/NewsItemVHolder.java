package com.monsterlin.blives.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;

/**
 * Created by monsterLin on 6/24/2016.
 */
public class NewsItemVHolder extends RecyclerView.ViewHolder {

    public ImageView iv_show_img;
    public TextView tv_title ,  tv_date  ,tv_from ;

    public NewsItemVHolder(View itemView) {
        super(itemView);
        iv_show_img= (ImageView) itemView.findViewById(R.id.iv_show_img);
        tv_title= (TextView) itemView.findViewById(R.id.tv_title);
        tv_from= (TextView) itemView.findViewById(R.id.tv_from);
        tv_date= (TextView) itemView.findViewById(R.id.tv_date);
    }

}
