package com.monsterlin.blives.adapter.campus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monsterlin.blives.R;
import com.monsterlin.blives.adapter.base.BaseRecyclerAdapter;
import com.monsterlin.blives.adapter.viewholder.CampusAcVHolder;
import com.monsterlin.blives.bean.CampusAc;

/**
 * Created by monsterLin on 2016/7/28.
 */
public class CampusAcAdapter  extends BaseRecyclerAdapter<CampusAc> {
    @Override
    public RecyclerView.ViewHolder onCreate(ViewGroup parent, int viewType) {
        View campusAcView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_campusac, parent, false);
        CampusAcVHolder campusAcVHolder = new CampusAcVHolder(campusAcView);
        return campusAcVHolder;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder viewHolder, int RealPosition, CampusAc data) {
        if (viewHolder instanceof CampusAcVHolder) {
            ((CampusAcVHolder) viewHolder).tv_title.setText(data.getAcTitle());
            ((CampusAcVHolder) viewHolder).tv_date.setText(data.getUpdatedAt());
            ((CampusAcVHolder) viewHolder).tv_union.setText(data.getAcUnion().getCommunity());

            //TODO logo加载

        }
    }


}
