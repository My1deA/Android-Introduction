package com.example.chapter5.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.chapter5.fragment.LaunchFragment;

public class LaunchImproveAdapter extends FragmentStatePagerAdapter {

    private int[] mImageArray;//声明一个图片数组

    public LaunchImproveAdapter(FragmentManager fm,int[] imageArray){
        super(fm);
        mImageArray=imageArray;
    }

    @Override
    public Fragment getItem(int i) {
        return LaunchFragment.newInstance(i,mImageArray[i]);
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }
}
