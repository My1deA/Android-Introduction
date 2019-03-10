package com.example.chapter5.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chapter5.R;
import com.example.chapter5.bean.CalendarTransfer;
import com.example.chapter5.calender.LunarCalendar;
import com.example.chapter5.calender.SpecialCalendar;
import com.example.chapter5.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarGridAdapter extends BaseAdapter {

    private static final String TAG="CalendarGridAdapter";
    private Context mContext;
    private boolean isLeapyear=false;//是否闰年
    private int daysOfMonth=0;
    private int dayOfWeek=0;
    private int lastDaysOfMonth=0;
    private String[] dayNumber=new String[49];
    private ArrayList<CalendarTransfer> transfers=new ArrayList<CalendarTransfer>();
    private static String weekTitle[]={"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private LunarCalendar lc;
    private int currentDay=-1;

    public CalendarGridAdapter(Context context,int year,int month,int day){
        mContext=context;
        lc=new LunarCalendar();
        Log.d(TAG, "currentYear=" + year + ", currentMonth=" + month + ", currentDay=" + day);
        isLeapyear= SpecialCalendar.isLeapYear(year);
        daysOfMonth=SpecialCalendar.getDaysOfMonth(isLeapyear,month);
        dayOfWeek=SpecialCalendar.getWeekdayOfMonth(year,month);
        lastDaysOfMonth=SpecialCalendar.getDaysOfMonth(isLeapyear,month-1);
        Log.d(TAG, isLeapyear + " ======  " + daysOfMonth + "  ============  " + dayOfWeek + "  =========   " + lastDaysOfMonth);
        getWeekDays(year, month);

    }


    @Override
    public int getCount() {
        return dayNumber.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();

            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_calendar,null);
            holder.tv_day=convertView.findViewById(R.id.tv_day);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        String day=dayNumber[position].split("\\.")[0];
        String festival=dayNumber[position].split("\\.")[1];
        String itemText=day;
        if(position>7){
            itemText=itemText+"\n"+festival;
        }
        holder.tv_day.setText(itemText);
        holder.tv_day.setTextColor(Color.BLACK);

        if(position<7){
            holder.tv_day.setTextColor(Color.BLACK);
            holder.tv_day.setBackgroundColor(Color.LTGRAY);
        }else if (currentDay == position) {
            holder.tv_day.setBackgroundColor(Color.GREEN); // 设置当天的背景
        } else {
            holder.tv_day.setBackgroundColor(Color.WHITE); // 设置其他日期的背景
        }

        if(position<daysOfMonth+dayOfWeek+7-1&&position>=dayOfWeek+7-1){
            if(DateUtil.isHoliday(festival)){
                holder.tv_day.setTextColor(Color.BLUE);
            }else if((position+1)%7==6||(position+1)%7==0){
                holder.tv_day.setTextColor(Color.RED);
            }else{
                holder.tv_day.setTextColor(Color.BLACK);
            }
        }

        return convertView;
    }

    public final class ViewHolder{
        public TextView tv_day;
    }

    public CalendarTransfer getCalendarList(int pos){
        return transfers.get(pos);
    }



    private void getWeekDays(int year, int month) {
        int nextMonthDay=1;
        String lunarDay="";
        Log.d(TAG,"begin getWeekDays");
        for(int i=0;i<dayNumber.length;i++){
            CalendarTransfer trans=new CalendarTransfer();
            int weekda=(i-7)%7+1;
            //周一
            if(i<7){
                dayNumber[i]=weekTitle[i]+"."+" ";
            }else if(i<dayOfWeek+7-1){
                int temp=lastDaysOfMonth-dayOfWeek+1-7+1;
                trans=lc.getSubDate(trans,year,month-1,temp+i,weekda,false);
                lunarDay=trans.day_name;
                dayNumber[i]=(temp+i)+"."+lunarDay;
            }else if(i<daysOfMonth+dayOfWeek+7-1){
                int day=i-dayOfWeek-7+1+1;
                trans=lc.getSubDate(trans,year,month,day,weekda,false );
                lunarDay=trans.day_name;
                dayNumber[i]=day+"."+lunarDay;

                if(year== DateUtil.getNowYear()&&month==DateUtil.getNowMonth()&&day==DateUtil.getNowDay()){
                    currentDay=i;
                }
            }else {
                int next_month=month+1;
                int next_year=year;
                if (next_month >= 13) {
                    next_month=1;
                    next_year++;
                }
                trans=lc.getSubDate(trans,next_year,next_month,nextMonthDay,weekda,false);
                lunarDay=trans.day_name;
                dayNumber[i]=nextMonthDay+"."+lunarDay;
                nextMonthDay++;
            }
            transfers.add(trans);
        }
    }


}
