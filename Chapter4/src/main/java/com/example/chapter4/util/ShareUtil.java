package com.example.chapter4.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtil {
    private static ShareUtil mUtil;
    private static SharedPreferences mShared;

    public static ShareUtil getInstance(Context cx){
        if(mUtil==null){
            mUtil=new ShareUtil();
        }
        mShared=cx.getSharedPreferences("share",Context.MODE_PRIVATE);
        return mUtil;
    }

    public void writeShared(String key,String value){
        SharedPreferences.Editor editor=mShared.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public String readShared(String key,String defaultValue){
        return mShared.getString(key,defaultValue);
    }
}
