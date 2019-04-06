package com.example.chapter10.bean;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.example.chapter10.R;
import com.example.chapter10.util.ApkUtil;

import java.util.ArrayList;

public class packageInfo {
    public String app_name;
    public String package_name;
    public int package_icon;
    public String download_url;
    public String old_version;
    public String new_version;

    public packageInfo(){
        app_name="";
        package_name="";
        package_icon=0;
        download_url="";
        old_version="";
        new_version="";
    }

    private static String[] mVersionArray={
            "8.12.5", "8.9.2", "7.0.5.0", "6.6.1", "7.4.0", "7.3.2"
    };

    public static String[] mUrlArray={
            "http://www.lenovomm.com/appdown/21661264-2",
            "http://www.lenovomm.com/appdown/21589548-2",
            "http://www.lenovomm.com/appdown/21665086-2",
            "http://www.lenovomm.com/appdown/21665350-2",
            "http://www.lenovomm.com/appdown/21672339-2",
            "http://www.lenovomm.com/appdown/21639509-2"
    };

    public static String[] mNameArray={
      "爱奇艺","酷狗音乐","美图秀秀","微信","淘宝","QQ"
    };

    private static String[] mPackageArray = {
            "com.qiyi.video", "com.kugou.android", "com.mt.mtxx.mtxx",
            "com.tencent.mm", "com.taobao.taobao", "com.tencent.mobileqq"
    };
    private static int[] mIconArray = {
            R.drawable.icon_aiqiyi, R.drawable.icon_kugou, R.drawable.icon_meitu,
            R.drawable.icon_weixin, R.drawable.icon_taobao, R.drawable.icon_qq
    };

    public static ArrayList<packageInfo> getDefaultList(Context context){
        ArrayList<packageInfo> packageInfos=new ArrayList<packageInfo>();
        for(int i=0;i<mNameArray.length;i++){
            packageInfo info=new packageInfo();
            info.app_name=mNameArray[i];
            info.package_name=mPackageArray[i];
            info.package_icon=mIconArray[i];
            info.old_version= ApkUtil.getInstallVersion(context,info.package_name);
            packageInfos.add(info);
        }
        return packageInfos;
    }


}
