package com.example.chapter4.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Utils {

    //dp转为px
    public static int dip2px(Context context, float dpValue){
        //手机像素密度
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    //px转成dp
    public static int px2dip(Context context,float pxValue){
        //像数密度
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }


    //获得屏幕的宽度 int
    public static int getScreenWidth(Context ctx){
        WindowManager wm=(WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    //获得屏幕的高度  int
    public static int getScreenHeight(Context ctx){
        WindowManager wm=(WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return  dm.heightPixels;
    }

    //获得屏幕的像素密度 float
    public  static float getScreenDensity(Context ctx){
        WindowManager wm=(WindowManager)ctx.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }






}
