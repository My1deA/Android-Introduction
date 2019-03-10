package com.example.chapter5;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.chapter5.calender.Constant;
import com.example.chapter5.fragment.CalendarFragment;

public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    private int mYear; // 声明当前日历所处的年份

    public CalendarPagerAdapter(FragmentManager fm,int nowYear){
        super(fm);
        mYear=nowYear;

    }

    @Override
    public Fragment getItem(int i) {
        return CalendarFragment.newInstance(mYear,i+1);
    }

    @Override
    public int getCount() {
        return 12;
    }

    public CharSequence getPageTitle(int position){
        return new String(Constant.xuhaoArray[position+1]+"月");
    }

}
