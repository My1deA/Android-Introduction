package com.example.z2.Util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.security.Permission;

public class PermissionUtil {
    private final static String TAG="PermissionUtil";

    public static boolean checkPermission(Activity activity,String permission,int requestCode){
        Log.d(TAG, "checkPermission: "+permission);
        boolean result=true;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //检查是否已经开启
            int check=ContextCompat.checkSelfPermission(activity,permission);
            //没有开启
            if(check!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
                result=false;
            }
        }
        return result;
    }

    public static boolean checkMultiPermission(Activity activity,String [] permissions,int requestCode){

        boolean reuslt=true;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            //检查是否已经开启
            int check=PackageManager.PERMISSION_GRANTED;
            for(String permission: permissions){
                check=ContextCompat.checkSelfPermission(activity,permission);
                if(check!=PackageManager.PERMISSION_GRANTED){
                    break;
                }
            }
            if(check!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,permissions,requestCode);
                reuslt=false;
            }
        }
        return reuslt;
    }



}


















//    private final static String TAG="PermissionUtil";
//
//    public boolean checkPermission(Activity activity,String permission,int requestCode){
//        Log.d(TAG,"checkPermission"+permission);
//        boolean result=true;
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
//            //检测一下
//            int check= ContextCompat.checkSelfPermission(activity,permission);
//            if(check!= PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(activity,new String[]{permission},requestCode);
//                result=false;
//            }
//
//        }
//        return result;
//    }