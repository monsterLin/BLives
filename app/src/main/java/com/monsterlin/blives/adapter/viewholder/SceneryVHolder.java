package com.monsterlin.blives.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;

/**
 * Created by monsterLin on 6/28/2016.
 */
public class SceneryVHolder extends RecyclerView.ViewHolder {

    public ImageView iv_show_img;
    public TextView tv_intro ;

    public SceneryVHolder(View itemView) {
        super(itemView);
        iv_show_img= (ImageView) itemView.findViewById(R.id.iv_show_img);
        tv_intro = (TextView) itemView.findViewById(R.id.tv_intro);
    }
}
