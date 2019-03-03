package com.example.chapter4.bean;

import com.example.chapter4.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid;
    public int xuhao;
    public String name;
    public String desc;
    public float price;
    public String thumb_path;
    public String pic_path;
    public int thumb;
    public int pic;

    public GoodsInfo(){
        rowid=0L;
        xuhao=0;
        name="";
        desc="";
        price=0;
        thumb_path="";
        pic_path="";
        thumb=0;
        pic=0;
    }

    private static String[] mNameArray={
      "iPhone8","Mate10","小米6","OPPO R11","vivo X9S","魅族Pro6s"
    };

    private static String[] mDesArray={
            "Apple iPhone 8 256GB 玫瑰金色 移动联通电信4G手机",
            "华为 HUAWEI Mate10 6GB+128GB 全网通（香槟金）",
            "小米 MI6 全网通版 6GB+128GB 亮白色",
            "OPPO R11 4G+64G 全网通4G智能手机 玫瑰金",
            "vivo X9s 4GB+64GB 全网通4G拍照手机 玫瑰金",
            "魅族 PRO6S 4GB+64GB 全网通公开版 星空黑 移动联通电信4G手机"
    };

    private static float[] mPriceArray={6888,3999,2999,2899,2698,2098};


    private static int[] mThumbArray={
            R.drawable.iphone_s,R.drawable.huawei_s,R.drawable.xiaomi_s,
            R.drawable.oppo_s,R.drawable.vivo_s,R.drawable.meizu_s
    };


    private static int[] mPicArray={
        R.drawable.iphone,R.drawable.huawei,R.drawable.xiaomi,
        R.drawable.oppo,R.drawable.vivo,R.drawable.meizu
    };

    public static ArrayList<GoodsInfo> getDefaultList(){
        ArrayList<GoodsInfo> goodsInfos=new ArrayList<GoodsInfo>();
        for(int i=0;i<mNameArray.length;i++){
            GoodsInfo info=new GoodsInfo();
            info.name=mNameArray[i];
            info.desc=mDesArray[i];
            info.price=mPriceArray[i];
            info.thumb=mThumbArray[i];
            goodsInfos.add(info);
        }
        return goodsInfos;
    }

}




















