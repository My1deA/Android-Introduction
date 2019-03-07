package com.example.chapter5;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chapter5.util.DateUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG="AlarmActivity";
    private static TextView tv_alarm;
    private int mDelay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        tv_alarm=findViewById(R.id.tv_alarm);
        findViewById(R.id.btn_alarm).setOnClickListener(this);
        initDelaySpinner();
    }

    private int[] delayArray = {5, 10, 15, 20, 25, 30};
    private String[] delayDescArray = {"5秒", "10秒", "15秒", "20秒", "25秒", "30秒"};

    private void initDelaySpinner() {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.item_select,delayDescArray);
        Spinner sp_delay=findViewById(R.id.sp_delay);
        sp_delay.setPrompt("请选择闹钟延迟");
        sp_delay.setAdapter(adapter);
        sp_delay.setOnItemSelectedListener(new DelaySelectedListener());
        sp_delay.setSelection(0);
    }


    private class DelaySelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mDelay=delayArray[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private String ALARM_EVENT="com.example.Chapter5.AlarmActivity.AlarmReceiver";
    private static String mDesc="";
    private static boolean isArrived=false;

    public static final  class AlarmReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                Log.d(TAG,"AlarmReceiver onReceive");
                if(tv_alarm!=null&&!isArrived){
                    isArrived=true;
                    mDesc=String.format("%s\n%s 闹钟时间到达",mDesc, DateUtil.getNowTime());
                    tv_alarm.setText(mDesc);
                }
            }
        }
    }

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            alarmReceiver=new AlarmReceiver();
            IntentFilter filter=new IntentFilter(ALARM_EVENT);
            registerReceiver(alarmReceiver,filter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            unregisterReceiver(alarmReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_alarm){
            Intent intent=new Intent(ALARM_EVENT);
            PendingIntent pIntent=PendingIntent.getBroadcast(this,0,intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            //从系统服务中获取闹钟管理器
            AlarmManager alarmMgr=(AlarmManager)getSystemService(ALARM_SERVICE);
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND,mDelay);
            alarmMgr.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pIntent);
            mDesc=DateUtil.getNowTime()+"设置闹钟";
            tv_alarm.setText(mDesc);
        }
    }
}
