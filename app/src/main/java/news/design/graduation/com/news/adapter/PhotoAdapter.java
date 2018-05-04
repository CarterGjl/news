package news.design.graduation.com.news.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import news.design.graduation.com.news.R;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.UiUtil;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    private List<NewsInfo.ResultBean.DataBean> newsInfos = new ArrayList<>();

    private List<Integer> heightList  = new ArrayList<>();
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtil.getContext()).inflate(R.layout.list_photo, parent, false);
        return new PhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(UiUtil.getContext()).load(newsInfos.get(position).getThumbnail_pic_s()).into(holder.imageView);
        holder.textView.setText(newsInfos.get(position).getTitle());
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.height = heightList.get(position);
        holder.imageView.setLayoutParams(params);
    }

    public void updatePhoto(List<NewsInfo.ResultBean.DataBean> arrayList){

        newsInfos = arrayList;

        for (int i = 0; i < newsInfos.size(); i++) {

            int height = new Random().nextInt(300) + 100;
            heightList.add(height);
        }
    }
    @Override
    public int getItemCount() {
        return newsInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
           cardView = ((CardView) itemView);
           imageView = itemView.findViewById(R.id.iv_photo);
           textView = itemView.findViewById(R.id.tv_photo_title);
        }
    }
}
