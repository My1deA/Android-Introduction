package com.example.chapter10.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermissionUtil {
    private final static String TAG="PermissionUtil";

    //检查某个权限，返回true 表示已启用该权限 返回false表示未启用权限
    public static boolean checkPermission(Activity act,String permission,int requestCode){
        Log.d(TAG,"checkPermission:"+permission);

        boolean reuslt=true;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int check= ContextCompat.checkSelfPermission(act,permission);
            if(check!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(act,new String[]{permission},requestCode);
                reuslt=false;
            }
        }
        return reuslt;
    }

    //检查多个权限
    public static boolean checkMultiPermission(Activity act,String[] permissions,int requestConde ){
        boolean result=true;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int check=PackageManager.PERMISSION_GRANTED;
            for(String permission: permissions){
                check=ContextCompat.checkSelfPermission(act,permission);
                if(check!=PackageManager.PERMISSION_GRANTED){
                    break;
                }
            }
            if(check!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(act,permissions,requestConde);
                result=false;
            }
        }
        return result;
    }

    public static void goActivity(Context ctx,Class<?> cls){
        Intent intent=new Intent(ctx,cls);
        ctx.startActivity(intent);
    }

}
