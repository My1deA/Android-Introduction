package com.example.chapter5.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ArrayAdapter;

import com.example.chapter5.bean.GoodsInfo;
import com.example.chapter5.fragment.BroadcastFragment;

import java.util.ArrayList;

public class BroadcastPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<GoodsInfo> goodsInfos=new ArrayList<GoodsInfo>();

    public BroadcastPagerAdapter(FragmentManager fm,ArrayList<GoodsInfo> goodsInfos){
        super(fm);
        this.goodsInfos=goodsInfos;
    }

    @Override
    public Fragment getItem(int position) {
        return BroadcastFragment.newInstance(position,goodsInfos.get(position).pic,goodsInfos.get(position).desc);
    }

    @Override
    public int getCount() {
        return goodsInfos.size();
    }

    public CharSequence getPageTitle(int position){
        return goodsInfos.get(position).name;
    }

}

