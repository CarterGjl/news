package news.design.graduation.com.news.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import news.design.graduation.com.news.R;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.UiUtil;

/**
 * Created by carter on 2018/3/6
 */

public class SearchAdapter extends Adapter<SearchAdapter.ViewHolder> {
    private List<NewsInfo.ResultBean.DataBean> mNewsInfos = new ArrayList<>();
    private NewsListAdapter.OnClickNewsListener mOnClickNewsListener;

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtil.getContext()).inflate(R.layout.list_item, parent,
                false);
        return new ViewHolder(view);
    }

    public void updateSearchResults(List<NewsInfo.ResultBean.DataBean> searchResults) {
        this.mNewsInfos = searchResults;
    }

    private static final String TAG = "SearchAdapter";
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, final int position) {
        final NewsInfo.ResultBean.DataBean newsInfo = mNewsInfos.get(position);
        holder.mTvTitle.setText(mNewsInfos.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: "+mNewsInfos.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: "+mNewsInfos.get(position).getThumbnail_pic_s());
        Log.d(TAG, "onBindViewHolder: 新闻详情"+mNewsInfos.get(position).getUrl());
        Glide.with(UiUtil.getContext()).load(mNewsInfos.get(position).getThumbnail_pic_s()).into(holder.mImageView);
        holder.mTvContent.setText(mNewsInfos.get(position).getAuthor_name());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsInfo.ResultBean.DataBean dataBean = mNewsInfos.get(position);
                mOnClickNewsListener.onClickNews(dataBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsInfos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        private ImageView mImageView;
        private TextView mTvTitle;
        private TextView mTvContent;
        ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            mImageView = itemView.findViewById(R.id.iv_image);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_au);
        }
    }

    public void setOnClickNewsListener(NewsListAdapter.OnClickNewsListener onClickNewsListener){
        mOnClickNewsListener = onClickNewsListener;
    }

    public interface OnClickNewsListener{

        void onClickNews(NewsInfo.ResultBean.DataBean dataBean);
    }
}
