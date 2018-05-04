package news.design.graduation.com.news.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.DrmInitData;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import news.design.graduation.com.news.R;
import news.design.graduation.com.news.adapter.PhotoAdapter;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.DbOpenHelper;
import news.design.graduation.com.news.util.UiUtil;

public class PhotoActivity extends Activity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        recyclerView = findViewById(R.id.rv_photo);
        initView();
        initData();
    }

    private void initView() {

    }

    private void initData() {

        Observable.create(new ObservableOnSubscribe<List<NewsInfo.ResultBean.DataBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NewsInfo.ResultBean.DataBean>> e) throws Exception {

                DbOpenHelper newsDB = new DbOpenHelper(UiUtil.getContext(),"news");
                SQLiteDatabase writableDatabase = newsDB.getWritableDatabase();
                List<NewsInfo.ResultBean.DataBean> dataBeanArrayList = new ArrayList<>();
                Cursor cursor = writableDatabase.query("news", new String[]{}, null, null, null, null,
                        null);
                if (cursor.moveToFirst()){
                    while (!cursor.isAfterLast()){
                        NewsInfo.ResultBean.DataBean dataBean = new NewsInfo.ResultBean.DataBean();
                        dataBean.setUniquekey(cursor.getString(0));
                        dataBean.setTitle(cursor.getString(1));
                        dataBean.setDate(cursor.getString(2));
                        dataBean.setCategory(cursor.getString(3));
                        dataBean.setAuthor_name(cursor.getString(4));
                        dataBean.setUrl(cursor.getString(5));
                        dataBean.setThumbnail_pic_s(cursor.getString(6));
                        dataBean.setThumbnail_pic_s02(cursor.getString(7));
                        dataBean.setThumbnail_pic_s03(cursor.getString(8));
                        dataBeanArrayList.add(dataBean);
                        cursor.moveToNext();
                    }
                }
                cursor.close();
                writableDatabase.close();
                e.onNext(dataBeanArrayList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsInfo.ResultBean.DataBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {


                    }

                    @Override
                    public void onNext(List<NewsInfo.ResultBean.DataBean> dataBeans) {

                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        PhotoAdapter photoAdapter = new PhotoAdapter();
                        photoAdapter.updatePhoto(dataBeans);
                        recyclerView.setAdapter(photoAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
