package news.design.graduation.com.news.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import news.design.graduation.com.news.R;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.UiUtil;

/**
 * Created by carter on 2018/3/20
 * 新闻数据适配器
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private static final String TAG = "NewsListAdapter";

    private OnClickNewsListener mOnClickNewsListener;
    private List<NewsInfo.ResultBean.DataBean> mBeanArrayList;

    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtil.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    public void updateNews(List<NewsInfo.ResultBean.DataBean> beanArrayList) {
        mBeanArrayList = beanArrayList;
    }

    @Override
    public int getItemCount() {
        return mBeanArrayList.size();
    }

    public void setOnClickNewsListener(OnClickNewsListener onClickNewsListener){
        mOnClickNewsListener = onClickNewsListener;
    }
    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {
        holder.mTvTitle.setText(mBeanArrayList.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: "+mBeanArrayList.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: "+mBeanArrayList.get(position).getThumbnail_pic_s());
        Log.d(TAG, "onBindViewHolder: 新闻详情"+mBeanArrayList.get(position).getUrl());
        Glide.with(UiUtil.getContext()).load(mBeanArrayList.get(position).getThumbnail_pic_s()).into(holder.mImageView);
        holder.mTvContent.setText(mBeanArrayList.get(position).getAuthor_name());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsInfo.ResultBean.DataBean dataBean = mBeanArrayList.get(position);
                mOnClickNewsListener.onClickNews(dataBean);
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                mOnClickNewsListener.onLongClickListener(mBeanArrayList.get(position));
                return true;
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTvTitle;
        private TextView mTvContent;
        private CardView mCardView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.iv_image);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvContent = itemView.findViewById(R.id.tv_au);
            mCardView = itemView.findViewById(R.id.cv_item);
        }
    }
    public interface OnClickNewsListener{

        void onClickNews(NewsInfo.ResultBean.DataBean dataBean);
        void onLongClickListener(NewsInfo.ResultBean.DataBean dataBean);
    }
}
