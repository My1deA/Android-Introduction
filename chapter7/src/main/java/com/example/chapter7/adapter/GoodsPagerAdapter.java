package com.example.chapter7.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import com.example.chapter7.R;
import com.example.chapter7.fragment.BookCoverFragment;
import com.example.chapter7.fragment.BookDetailFragment;

public class GoodsPagerAdapter extends FragmentPagerAdapter{
    private ArrayList<String> mTitleArray; // 声明一个标题文字队列

    public GoodsPagerAdapter(FragmentManager fm,ArrayList<String> titleArray){
        super(fm);
        mTitleArray=titleArray;
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new BookCoverFragment();
        }else if(i==1){
            return new BookDetailFragment();
        }
        return new BookCoverFragment();
    }

    @Override
    public int getCount() {
        return mTitleArray.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArray.get(position);
    }
}
