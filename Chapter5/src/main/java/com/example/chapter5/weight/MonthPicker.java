package com.example.chapter5.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class MonthPicker  extends DatePicker {
    public MonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获得年月日的下拉列表项
        ViewGroup vg=((ViewGroup) ((ViewGroup)getChildAt(0)).getChildAt(0));
        if(vg.getChildCount()==3){
            vg.getChildAt(2).setVisibility(View.GONE);
        }else if(vg.getChildCount()==5){
            vg.getChildAt(3).setVisibility(View.GONE);
            vg.getChildAt(4).setVisibility(View.GONE);
        }
    }
}
