package com.monsterlin.blives.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.R;
import com.monsterlin.blives.entity.SchoolNews;
import com.monsterlin.blives.utils.ImageLoader;

import java.util.List;

/**
 * 新闻适配器
 * Created by monsterLin on 2016/2/27.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private List<SchoolNews> newsList;
    private Context mContext ;
    private LayoutInflater mInflater;


    public NewsAdapter(List<SchoolNews> newsList, Context mContext) {
        this.newsList = newsList;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news,parent,false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
           if (newsList.get(position).getNewsImgURLList().size() !=0){
               String firstImgURL = newsList.get(position).getNewsImgURLList().get(0);  //第一张图片的url
               holder.iv_show_img.setTag(firstImgURL);
               new ImageLoader().showImageByAsyncTask(holder.iv_show_img,firstImgURL);

           }else {
                holder.iv_show_img.setImageResource(R.drawable.test_banner_load_fail);
           }

        holder.tv_title.setText(cutText(newsList.get(position).getNewsTitle()));
        holder.tv_content.setText(cutText(newsList.get(position).getNewsContent()));
        holder.tv_date.setText(newsList.get(position).getNewsDate());
    }

    /**
     * 剪切文本
     * @param allText
     * @return
     */
    private String cutText(String allText) {
        int length = allText.length();
        if(length>=14){
            String text = allText.substring(0,13)+".......";
            return text;
        }else {
            return  allText;
        }

    }
}

/**
 * ViewHolder
 */
class NewsViewHolder extends RecyclerView.ViewHolder{

    ImageView iv_show_img;
    TextView tv_title , tv_content , tv_date;
        public NewsViewHolder(View itemView) {
            super(itemView);
            iv_show_img= (ImageView) itemView.findViewById(R.id.iv_show_img);
            tv_title= (TextView) itemView.findViewById(R.id.tv_title);
            tv_content= (TextView) itemView.findViewById(R.id.tv_content);
            tv_date= (TextView) itemView.findViewById(R.id.tv_date);
        }

}