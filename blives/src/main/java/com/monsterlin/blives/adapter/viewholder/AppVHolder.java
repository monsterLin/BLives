package com.monsterlin.blives.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;

/**
 * Created by monsterLin on 6/24/2016.
 */
public class AppVHolder extends RecyclerView.ViewHolder {
    public ImageView iv_life;
    public TextView tv_life;

    public AppVHolder(View itemView) {
        super(itemView);
        iv_life= (ImageView) itemView.findViewById(R.id.iv_app);
        tv_life= (TextView) itemView.findViewById(R.id.tv_app);
    }
}
