package com.monsterlin.blives.activity.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.monsterlin.blives.BaseActivity;
import com.monsterlin.blives.R;
import com.monsterlin.blives.bean.Acinforms;
import com.monsterlin.blives.bean.Jobnews;
import com.monsterlin.blives.bean.Offnews;
import com.monsterlin.blives.bean.SchoolNews;
import com.monsterlin.blives.constants.DetailType;
import com.monsterlin.blives.utils.MTextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by monsterLin on 6/28/2016.
 */
public class NewsDetailsActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tv_title)
    TextView tv_title ;

    @InjectView(R.id.tv_date)
    TextView tv_date ;

    @InjectView(R.id.tv_from)
    TextView tv_from ;

    @InjectView(R.id.tv_content)
    TextView tv_content ;

    @InjectView(R.id.iv_newsimg)
    ImageView iv_newsimg ;

    private SchoolNews schoolNews;
    private Acinforms acinforms ;
    private Offnews offnews;
    private Jobnews jobnews;

    private String newsInfo ; //分享的新闻内容


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);
        ButterKnife.inject(this);
        initToolBar(toolbar,"详情",true);
        initData();
    }

    private void initData() {
        int type = getIntent().getBundleExtra("dataExtra").getInt("type");
        switch (type){
            case DetailType.SchoolNews:
                schoolNews = (SchoolNews) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
                initDetail(schoolNews.getTitle(),schoolNews.getContent(),schoolNews.getNewsdate().getDate(),schoolNews.getNewsimg());
                newsInfo=""+schoolNews.getTitle()+"\n"+schoolNews.getContent()+"\n";
                break;
            case DetailType.Acinforms:
                acinforms= (Acinforms) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
                initDetail(acinforms.getTitle(),acinforms.getContent(),acinforms.getNewsdate().getDate(),acinforms.getNewsimg());
                newsInfo=""+acinforms.getTitle()+"\n"+acinforms.getContent()+"\n";
                break;
            case DetailType.Offnews:
                offnews= (Offnews) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
                initDetail(offnews.getTitle(),offnews.getContent(),offnews.getNewsdate().getDate(),offnews.getNewsimg());
                newsInfo=""+offnews.getTitle()+"\n"+offnews.getContent()+"\n";
                break;
            case DetailType.Jobnews:
                jobnews= (Jobnews) getIntent().getBundleExtra("dataExtra").getSerializable("detail");
                initDetail(jobnews.getTitle(),jobnews.getContent(),jobnews.getNewsdate().getDate(),jobnews.getNewsimg());
                newsInfo=""+jobnews.getTitle()+"\n"+jobnews.getContent()+"\n";
                break;
        }
    }

    /**
     * 初始化内容
     * @param title 标题
     * @param content 内容
     * @param date 时间
     * @param newsimg 图片
     */
    private void initDetail(String title, String content, String date, BmobFile newsimg) {
        tv_title.setText(title);
        tv_date.setText(MTextUtils.dateFormat(date));
        tv_from.setText("滨州学院");
        tv_content.setText(content);

        if (newsimg!=null){
            ImageLoader.getInstance().displayImage(newsimg.getFileUrl(this),iv_newsimg);
        }else {
            iv_newsimg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newsdetails,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.item_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsInfo);
                startActivity(Intent.createChooser(shareIntent, "分享"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
