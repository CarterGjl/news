package news.design.graduation.com.news.http;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import news.design.graduation.com.news.newsInfo.DataBean;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.newsInfo.NewsInfo1;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by carter on 2018/3/19
 */

public interface NewsService {

    @GET("toutiao/index")
    Observable<NewsInfo> getResponser(@Query("key")String key, @Query("type")String
            type);

}
