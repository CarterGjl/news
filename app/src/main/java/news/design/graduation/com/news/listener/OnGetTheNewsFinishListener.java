package news.design.graduation.com.news.listener;

import java.util.List;

import news.design.graduation.com.news.newsInfo.NewsInfo;

/**
 * Created by carter on 2018/3/20
 */

public interface OnGetTheNewsFinishListener {
    void finish(List<NewsInfo.ResultBean.DataBean> data);
}
