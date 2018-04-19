package news.design.graduation.com.news.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import news.design.graduation.com.news.newsInfo.DataBean;
import news.design.graduation.com.news.newsInfo.NewsInfo;

public class SearchNewsUtil {

    public static List<NewsInfo.ResultBean.DataBean> SearchNews(String key){
        ArrayList<NewsInfo.ResultBean.DataBean> news = new ArrayList<>();
        String selection = "title LIKE '%" + key + "%'";
        /**
         * uniquekey : 6c4caa0c3ba6e05e2a272892af43c00e
         * title : 杨幂的发际线再也回不去了么？网友吐槽像半秃
         * date : 2017-01-05 11:03
         * category : yule
         * author_name : 腾讯娱乐
         * url : http://mini.eastday.com/mobile/170105110355287.html?qid=juheshuju
         * thumbnail_pic_s : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_1_mwpm_03200403.jpeg
         * thumbnail_pic_s02 : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_2_mwpm_03200403.jpeg
         * thumbnail_pic_s03 : http://03.imgmini.eastday.com/mobile/20170105/20170105110355_ 806f4ed3fe71d04fa452783d6736a02b_3_mwpm_03200403.jpeg
         */
        String[] target = {"uniquekey","title","date","category","author_name","url",
                "thumbnail_pic_s","thumbnail_pic_s02","thumbnail_pic_s03"};
        DbOpenHelper dbOpenHelper = new DbOpenHelper(UiUtil.getContext(),"news");
        SQLiteDatabase writableDatabase = dbOpenHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query("news", target, selection, null, null, null, null);
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                NewsInfo.ResultBean.DataBean  dataBean = new NewsInfo.ResultBean.DataBean();
                dataBean.setUniquekey(cursor.getString(0));
                dataBean.setTitle(cursor.getString(1));
                dataBean.setDate(cursor.getString(2));
                dataBean.setCategory(cursor.getString(3));
                dataBean.setAuthor_name(cursor.getString(4));
                dataBean.setUrl(cursor.getString(5));
                dataBean.setThumbnail_pic_s(cursor.getString(6));
                dataBean.setThumbnail_pic_s02(cursor.getString(7));
                dataBean.setThumbnail_pic_s03(cursor.getString(8));
                news.add(dataBean);
                cursor.moveToNext();
            }
        }
        return news;
    }

}
