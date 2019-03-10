package com.example.chapter5.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.chapter5.R;
import com.example.chapter5.adapter.CalendarGridAdapter;

public class CalendarFragment extends Fragment {
    private static final String TAG="CalendarFragment";
    protected View mView;
    protected Context mContext;
    private int mYear,mMonth;
    private GridView gv_calendar;

    //获取碎片的一个实例
    public static CalendarFragment newInstance(int year,int month){
        CalendarFragment fragment=new CalendarFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("year",year);
        bundle.putInt("month",month);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        mContext=getActivity();
        if(getArguments()!=null){
            mMonth=getArguments().getInt("month",1);
            mYear=getArguments().getInt("year",2000);
        }

        mView=inflater.inflate(R.layout.fragment_calendar,container,false);
        gv_calendar=mView.findViewById(R.id.gv_calendar);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        CalendarGridAdapter adapter=new CalendarGridAdapter(mContext,mYear,mMonth,1);

        gv_calendar.setAdapter(adapter);
    }
}
