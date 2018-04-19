package news.design.graduation.com.news.util;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "news_list.db";
    public static final String TABLE_NAME = "news";
    private static final int DB_VERSION = 1;
    private String mSQL_create_table;

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
   /* String SQL_CREATE_TABLE = "create table if not exists " + TABLE_NAME + "(uniquekey TEXT primary " +
            "key, title TEXT,date TEXT,category TEXT,author_name TEXT,url TEXT,thumbnail_pic_s TEXT,thumbnail_pic_s02 TEXT,thumbnail_pic_s03 TEXT)";*/

    public DbOpenHelper(final Context context,String tableName) {
        super(context, DB_NAME, null, DB_VERSION);
        mSQL_create_table = "create table if not exists " + tableName + "(uniquekey TEXT primary " +
                "key, title TEXT,date TEXT,category TEXT,author_name TEXT,url TEXT,thumbnail_pic_s TEXT,thumbnail_pic_s02 TEXT,thumbnail_pic_s03 TEXT)";
    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    private static final String TAG = "DbOpenHelper";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mSQL_create_table);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
