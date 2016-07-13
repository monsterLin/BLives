package com.monsterlin.blives.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;

/**
 * Created by monsterLin on 6/30/2016.
 */
public class UnionVHolder extends RecyclerView.ViewHolder {

    public ImageView iv_union_logo , iv_star , iv_tel ;
    public TextView  tv_community , tv_director ;

    public UnionVHolder(View itemView) {
        super(itemView);

        iv_union_logo= (ImageView) itemView.findViewById(R.id.iv_union_logo);
        iv_star= (ImageView) itemView.findViewById(R.id.iv_star);
        iv_tel= (ImageView) itemView.findViewById(R.id.iv_tel);

        tv_community= (TextView) itemView.findViewById(R.id.tv_community);
        tv_director= (TextView) itemView.findViewById(R.id.tv_director);

    }
}
