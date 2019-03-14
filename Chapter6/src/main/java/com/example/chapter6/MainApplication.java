package com.example.chapter6;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import com.example.chapter6.util.NotifyUtil;

public class MainApplication extends Application {
    private final static String TAG="MainApplication";
    private static MainApplication mApp;
//    public TrafficDBHelper mTrafficHelper;


    public static MainApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotifyUtil.createNotifyChannel(this, getString(R.string.app_name));
        }
//        mTrafficHelper = TrafficDBHelper.getInstance(this, 1);
//        // 打开流量数据库帮助器的写连接
//        mTrafficHelper.openWriteLink();
//        Log.d(TAG, "onCreate");
    }
}
