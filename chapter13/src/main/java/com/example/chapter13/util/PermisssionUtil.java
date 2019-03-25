package com.example.chapter13.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class PermisssionUtil {
    private final static String TAG="PermissionUtil";

    public static boolean checkPermission(Activity act,String permission,int requestCode){
        Log.d(TAG,"checkPermission "+permission);
        boolean result=true;

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int check =ContextCompat.checkSelfPermission(act,permission);
            if(check!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(act,new String[]{permission},requestCode);
                result= false;
            }
        }
        return result;
    }

    public static boolean checkMultiPermission(Activity act,String[] permissions,int requestCode){
        boolean result=true;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int check=PackageManager.PERMISSION_GRANTED;
            //通过权限数组检查是否开启了这些权限
            for(String permission:permissions){
                check=ContextCompat.checkSelfPermission(act,permission);
                if(check!=PackageManager.PERMISSION_GRANTED);{
                    break;
                }
            }
            if(check!=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(act,permissions,requestCode);
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
