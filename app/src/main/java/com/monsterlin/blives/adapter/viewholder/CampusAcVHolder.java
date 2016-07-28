package com.monsterlin.blives.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.monsterlin.blives.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by monsterLin on 2016/7/28.
 */
public class CampusAcVHolder extends RecyclerView.ViewHolder {
    public TextView tv_title ,tv_date,tv_union;
    private CircleImageView iv_union_logo;

    public CampusAcVHolder(View itemView) {
        super(itemView);
        tv_title= (TextView) itemView.findViewById(R.id.tv_title);
        tv_date= (TextView) itemView.findViewById(R.id.tv_date);
        tv_union= (TextView) itemView.findViewById(R.id.tv_union);
        iv_union_logo= (CircleImageView) itemView.findViewById(R.id.iv_union_logo);
    }
}
