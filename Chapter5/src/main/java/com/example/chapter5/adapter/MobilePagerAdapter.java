package com.example.chapter5.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.chapter5.bean.GoodsInfo;
import com.example.chapter5.fragment.DynamicFragment;

import java.util.ArrayList;

public class MobilePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<GoodsInfo> mGoodsList=new ArrayList<GoodsInfo>();


    //碎片页适配器的构造函数 传入碎片管理器的商品信息队列
    public MobilePagerAdapter(FragmentManager fm, ArrayList<GoodsInfo> goodsInfos){
        super(fm);
        mGoodsList=goodsInfos;
    }

    @Override
    public Fragment getItem(int position) {
        return DynamicFragment.newInstance(position,mGoodsList.get(position).pic,mGoodsList.get(position).desc);
    }

    @Override
    public int getCount() {
        return  mGoodsList.size();
    }
}
