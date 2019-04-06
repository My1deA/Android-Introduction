package com.example.chapter10.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.chapter10.bean.ApkInfo;

import java.util.ArrayList;

public class ApkUtil {
    private static final String TAG="ApkUtil";

    //获取指定应用以及安装的版本号
    public static String getInstallVersion(Context context,String packageName){
        String version="";
        try{
            PackageInfo info=context.getPackageManager().getPackageInfo(packageName,0);
            if(info!=null){
                version=info.versionName;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return version;
    }

    public static ArrayList<ApkInfo> getAllApkFile(Context context){
        ArrayList<ApkInfo> appArray=new ArrayList<ApkInfo>();

        Cursor cursor=context.getContentResolver().query(MediaStore.Files.getContentUri("external"),null,
                "mime_type=\"application/vnd.android.package-archive\"",null,null);

        if(cursor!=null){
            while(cursor.moveToNext()){
                String title =cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE));
                String path=cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
                int size=cursor.getInt(cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE));
                PackageManager pm=context.getPackageManager();
                PackageInfo pi=pm.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
                if(pi!=null){
                    Log.d(TAG, "packageName="+pi.packageName+", versionName="+pi.versionName);
                    String pkg_name=pi.packageName;
                    String vs_name=pi.versionName;
                    int vs_code=pi.versionCode;
                    appArray.add(new ApkInfo(title,path,size,pkg_name,vs_name,vs_code));
                }

            }
            cursor.close();
        }
        return appArray;
    }

    //获得指定文件的安装包信息
    public static ApkInfo getApkInfo(Context context,String path){
        ApkInfo info=new ApkInfo();
        PackageManager pm=context.getPackageManager();

        PackageInfo pi=pm.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
        if(pi!=null){
            Log.d(TAG,"packageName="+pi.packageName+" ,VersionName="+pi.versionName);
            info.file_path=path;
            info.package_name=pi.packageName;
            info.version_name=pi.versionName;
            info.version_code=pi.versionCode;
        }
        return  info;
    }
}
