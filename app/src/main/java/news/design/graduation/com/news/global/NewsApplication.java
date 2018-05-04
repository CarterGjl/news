package news.design.graduation.com.news.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.mob.MobSDK;


public class NewsApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static int mMainThreadId;

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mMainThreadId = Process.myTid();
        MobSDK.init(this);
    }
}
