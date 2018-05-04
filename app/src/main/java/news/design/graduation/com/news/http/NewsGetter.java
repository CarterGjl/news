package news.design.graduation.com.news.http;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import news.design.graduation.com.news.R;
import news.design.graduation.com.news.listener.OnGetTheNewsFinishListener;
import news.design.graduation.com.news.newsInfo.DataBean;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.DbOpenHelper;
import news.design.graduation.com.news.util.UiUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by carter on 2018/3/19
 */

public class NewsGetter {

    private static final String TAG = "NewsGetter";
    private ProgressDialog mProgressDialog;


    @SuppressLint("CheckResult")
    public void updateContent(OnGetTheNewsFinishListener onGetTheNewsFinishListener, String type,Activity activity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v.juhe.cn/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        DbOpenHelper newsDB = new DbOpenHelper(UiUtil.getContext(),"news");
        DbOpenHelper typeDB = new DbOpenHelper(UiUtil.getContext(), type);
        SQLiteDatabase writableDatabase = newsDB.getWritableDatabase();
        newsDB.onCreate(writableDatabase);
        SQLiteDatabase typeDBWritableDatabase = typeDB.getWritableDatabase();
        typeDB.onCreate(typeDBWritableDatabase);
        /*Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("");
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(GsonConverterFactory.create());*/
        NewsService newsService = retrofit.create(NewsService.class);
        Observable<NewsInfo> responser = newsService.getResponser("1c46093f956ea7fc162f8742194913e4",
                type);
        responser.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {

                        showProgressDialog(activity);
                    }
                }).subscribe(new Consumer<NewsInfo>() {
            @Override
            public void accept(NewsInfo newsInfo) throws Exception {

                mProgressDialog.dismiss();
                if (newsInfo.getResult() == null) {
                    Toast.makeText(UiUtil.getContext(), "服务器端异常", Toast.LENGTH_SHORT).show();
                    // TODO: 2018/4/16 显示本地缓存
                    Observable.create(new ObservableOnSubscribe<List<NewsInfo.ResultBean.DataBean>>() {
                        @Override
                        public void subscribe(ObservableEmitter<List<NewsInfo.ResultBean.DataBean>> e) throws Exception {
                            e.onNext(getNewsFromDataBase(writableDatabase,type));
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<NewsInfo.ResultBean.DataBean>>() {
                                @Override
                                public void accept(List<NewsInfo.ResultBean.DataBean> dataBeans) throws Exception {
                                    onGetTheNewsFinishListener.finish(dataBeans);
                                }
                            });
                }else {

                    List<NewsInfo.ResultBean.DataBean> data = newsInfo.getResult().getData();
                    onGetTheNewsFinishListener.finish(data);
                    Observable.create(new ObservableOnSubscribe<Boolean>() {
                        @Override
                        public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                            writeToDataBase(data, writableDatabase, typeDBWritableDatabase, type);
                            e.onNext(true);
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                    if (aBoolean){
                                        Toast.makeText(UiUtil.getContext(), "缓存完成", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            });
                }
            }

        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                Toast.makeText(UiUtil.getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
            }
        });

       /* responser.enqueue(new Callback<NewsInfo>() {
            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: "+response.isSuccessful());
                    NewsInfo.ResultBean result = response.body().getResult();
                    String reason = response.body().getReason();
                    Log.d(TAG, "onResponse: "+reason);
                    if (result == null) {
                        Toast.makeText(UiUtil.getContext(), "服务器端异常", Toast.LENGTH_SHORT).show();
                        // TODO: 2018/4/16 显示本地缓存
                        Disposable subscribe = Observable.create(new ObservableOnSubscribe<List<NewsInfo.ResultBean.DataBean>>() {
                            @Override
                            public void subscribe(ObservableEmitter<List<NewsInfo.ResultBean.DataBean>> e) throws Exception {
                                e.onNext(getNewsFromDataBase(writableDatabase,type));
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<NewsInfo.ResultBean.DataBean>>() {
                            @Override
                            public void accept(List<NewsInfo.ResultBean.DataBean> dataBeans) throws Exception {
                                onGetTheNewsFinishListener.finish(dataBeans);
                            }
                        });
                    }else {
                        List<NewsInfo.ResultBean.DataBean> data = result.getData();
                        onGetTheNewsFinishListener.finish(data);
                        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                                writeToDataBase(data, writableDatabase, typeDBWritableDatabase, type);
                                e.onNext(true);
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        if (aBoolean){
                                            Toast.makeText(UiUtil.getContext(), "缓存完成", Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }
                                });


                    }

                }else {
                    Toast.makeText(UiUtil.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsInfo> call, Throwable t) {
                Toast.makeText(UiUtil.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    private void showProgressDialog(Activity activity) {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("连接ing  请等候");
        mProgressDialog.setTitle("提示");
        mProgressDialog.setIcon(R.drawable.small_icon);
        mProgressDialog.show();
    }
    private void writeToDataBase(List<NewsInfo.ResultBean.DataBean> data, SQLiteDatabase writableDatabase, SQLiteDatabase typeDBWritableDatabase, String type) {
        for (int i = 0; i < data.size(); i++) {
            Log.d(TAG, "onResponse: "+data.get(i).getTitle());
            ContentValues values = new ContentValues();
            /*
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
            /*private final String SQL_CREATE_TABLE = "create table if no" +
                    "t exists " + TABLE_NAME + "(uniquekey TEXT primary key, title" +
                    " TEXT,date TEXT,category TEXT,author_name TEXT,url " +
                    "TEXT,thumbnail_pic_s TEXT,thumbnail_pic_s02 TEXT,thumbnail_" +
                    "pic_s03 TEXT)";*/
            values.put("uniquekey",data.get(i).getUniquekey());
            values.put("title",data.get(i).getTitle());
            values.put("date",data.get(i).getDate());
            values.put("category",data.get(i).getCategory());
            values.put("author_name",data.get(i).getAuthor_name());
            values.put("url",data.get(i).getUrl());
            values.put("thumbnail_pic_s",data.get(i).getThumbnail_pic_s());
            values.put("thumbnail_pic_s02",data.get(i).getThumbnail_pic_s02());
            values.put("thumbnail_pic_s03",data.get(i).getThumbnail_pic_s03());
//                            contentResolver.insert(uri,values);
            writableDatabase.insert("news",null,values);
            typeDBWritableDatabase.insert(type,null,values);

        }

        typeDBWritableDatabase.close();
        writableDatabase.close();
    }

    private List<NewsInfo.ResultBean.DataBean> getNewsFromDataBase(SQLiteDatabase typeDBWritableDatabase, String type) {

        List<NewsInfo.ResultBean.DataBean> dataBeanArrayList = new ArrayList<>();
        Cursor cursor = typeDBWritableDatabase.query(type, new String[]{}, null, null, null, null,
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
        typeDBWritableDatabase.close();
        return dataBeanArrayList;
    }
}
