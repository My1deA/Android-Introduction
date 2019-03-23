package com.example.chapter9.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SwtichUtil {
    private static final String TAG="SwitchUtil";

    //开关状态
    public static boolean getGpsStatus(Context ctx){
        LocationManager lm= (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //检查定位功能是否打开
    public static void checkGpsIsOpen(Context ctx,String hint){
        if(!getGpsStatus(ctx)){
            Toast.makeText(ctx,hint,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            ctx.startActivity(intent);
        }
    }

    //无限网络开关
    public static boolean getWlanStatus(Context ctx){
        WifiManager wm= (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        return  wm.isWifiEnabled();
    }

    //打开或关闭无限网络
    public static void setWlanStatus(Context ctx,boolean enabled){
        WifiManager wm= (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        wm.setWifiEnabled(enabled);
    }

    //检查是否插入sim卡
    public static boolean getSimcardStatus(Context ctx){
        TelephonyManager tm= (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if(ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            return false;
        }
        String serial=tm.getSimSerialNumber();
        if(TextUtils.isEmpty(serial)){
            return false;
        }else{
            return true;
        }
    }

    //获得数据连接开关
    public static boolean getMobileDataStatus(Context ctx){
        if(!getSimcardStatus(ctx)){
            return false;
        }

        ConnectivityManager cm= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isopen=false;

        try{
            String methodName="getMonileDateEnabled";
            Method method=cm.getClass().getMethod(methodName);
            isopen=(boolean)method.invoke(cm);
            Log.d(TAG, "getMobileDataStatus isOpen="+isopen);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return isopen;
    }

    //打开或关闭数据连接
    public static void setMobileDateStatus(Context ctx,boolean enabled){
        //从系统服务中获取连接管理器
        ConnectivityManager cm= (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        try{
            String methodName="setMobileDateEnabled";
            Method method=cm.getClass().getMethod(methodName,Boolean.TYPE);
            method.invoke(cm,enabled);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //设置亮度自动调节的开关
    public static void setAutoBrigthtStatus(Context ctx,boolean enabled){
        int screenMode=(enabled)?Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC:
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        Settings.System.putInt(ctx.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,screenMode);
    }

    // 获取亮度自动调节的状态
    public static boolean getAutoBrightStatus(Context ctx) {
        int screenMode = Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
        try {
            screenMode = Settings.System.getInt(ctx.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
    }


}
