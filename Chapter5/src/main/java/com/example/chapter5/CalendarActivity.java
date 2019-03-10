package com.example.chapter5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chapter5.util.DateUtil;
import com.example.chapter5.weight.MonthPicker;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG="CalendarActivity";
    private LinearLayout ll_calendar_main;
    private LinearLayout ll_month_select;
    private MonthPicker mp_month;
    private ViewPager vp_calendar;
    private TextView tv_calendar;
    private boolean isShowSelect=false;
    private int mSelectedYear=2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ll_calendar_main=findViewById(R.id.ll_calendar_main);
        ll_month_select=findViewById(R.id.ll_month_select);
        //从布局文件中获得名叫mp_month月份选择器
        mp_month=findViewById(R.id.mp_month);
        //获取翻页
        findViewById(R.id.btn_ok).setOnClickListener(this);
        PagerTabStrip pts_calendar=findViewById(R.id.pts_calendar);
        pts_calendar.setTextColor(Color.BLACK);
        pts_calendar.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);
        //从布局文件中获取名叫vp_calendar
        vp_calendar=findViewById(R.id.vp_calendar);
        tv_calendar=findViewById(R.id.tv_calendar);
        tv_calendar.setOnClickListener(this);
        showCalendar(DateUtil.getNowYear(), DateUtil.getNowMonth());
    }

    private void showCalendar(int nowYear, int nowMonth) {
        if(nowYear!=mSelectedYear){
            tv_calendar.setText(nowYear+"年");
            CalendarPagerAdapter adapter=new CalendarPagerAdapter(getSupportFragmentManager(),nowYear);
            vp_calendar.setAdapter(adapter);
            mSelectedYear=nowYear;
        }
        vp_calendar.setCurrentItem(nowMonth-1);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_calendar){
            resetPage();
        }else if(v.getId()==R.id.btn_ok){
            showCalendar(mp_month.getYear(),mp_month.getMonth()+1);
            resetPage();
        }
    }

    private void resetPage(){
        isShowSelect=!isShowSelect;
        ll_calendar_main.setVisibility(isShowSelect?View.GONE:View.VISIBLE);
        ll_month_select.setVisibility(isShowSelect?View.GONE:View.VISIBLE);
    }
}
