package com.example.chapter5;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener,OnDateSetListener {

    private TextView tv_date;
    private DatePicker dp_date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        tv_date=findViewById(R.id.tv_date);
        dp_date=findViewById(R.id.dp_date);
        findViewById(R.id.btn_date).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_date){
            Calendar calendar=Calendar.getInstance();
            //构建一个日期对话框 该对话框已经集成了日期选择器
            DatePickerDialog dialog=new DatePickerDialog(this,this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();

        }else if(v.getId()==R.id.btn_ok){
            String desc=String.format("你选择的日期是%d年%d月%d日",dp_date.getYear(),dp_date.getMonth()+1,dp_date.getDayOfMonth());
            tv_date.setText(desc);
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String desc = String.format("你选择的日期是%d年%d月%d日",year,month,dayOfMonth);
            tv_date.setText(desc);
    }
}
