package com.example.chapter5;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private TextView tv_time;
    private TimePicker tp_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        tv_time=findViewById(R.id.tv_time);
        tp_time=findViewById(R.id.tp_time);
        findViewById(R.id.btn_time).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_time){
            Calendar calendar=Calendar.getInstance();
            TimePickerDialog dialog=new TimePickerDialog(this,this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true);// true表示24小时制，false表示12小时制
            dialog.show();
        }else if(v.getId()==R.id.btn_ok){
            String desc=String.format("你选择的时间%d时%d分",tp_time.getCurrentHour(),tp_time.getCurrentMinute());
            tv_time.setText(desc);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String desc = String.format("您选择的时间是%d时%d分", hourOfDay, minute);
        tv_time.setText(desc);
    }
}
