package news.design.graduation.com.news.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import news.design.graduation.com.news.util.DbOpenHelper;

public class NewsProvider extends ContentProvider {

    private String tableName;
    private Context context;
    private SQLiteDatabase writableDatabase;

    public NewsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
/*
        long insert = writableDatabase.insert(tableName, null, values);
        ContentUris.withAppendedId(uri,insert);
        if (insert>0) {
        }*/
        return null;
    }

    @Override
    public boolean onCreate() {
        initProvider();
        return false;
    }

    private void initProvider() {
        tableName = DbOpenHelper.TABLE_NAME;
        context = getContext();
//        writableDatabase = new DbOpenHelper(context,"news").getWritableDatabase();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
