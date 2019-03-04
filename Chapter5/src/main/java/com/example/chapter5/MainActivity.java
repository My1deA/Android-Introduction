package com.example.chapter5;

import android.content.Intent;
import android.support.v4.view.PagerTitleStrip;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_date_picker).setOnClickListener(this);
        findViewById(R.id.btn_time_picker).setOnClickListener(this);
        findViewById(R.id.btn_base_adapter).setOnClickListener(this);
        findViewById(R.id.btn_list_view).setOnClickListener(this);
        findViewById(R.id.btn_list_cart).setOnClickListener(this);
        findViewById(R.id.btn_grid_view).setOnClickListener(this);
        findViewById(R.id.btn_grid_channel).setOnClickListener(this);
        findViewById(R.id.btn_view_pager).setOnClickListener(this);
        findViewById(R.id.btn_title_strip).setOnClickListener(this);
        findViewById(R.id.btn_tab_strip).setOnClickListener(this);
        findViewById(R.id.btn_launch_simple).setOnClickListener(this);
        findViewById(R.id.btn_fragment_static).setOnClickListener(this);
        findViewById(R.id.btn_fragment_dynamic).setOnClickListener(this);
        findViewById(R.id.btn_launch_improve).setOnClickListener(this);
        findViewById(R.id.btn_broad_temp).setOnClickListener(this);
        findViewById(R.id.btn_broad_system).setOnClickListener(this);
        findViewById(R.id.btn_alarm).setOnClickListener(this);
        findViewById(R.id.btn_month_picker).setOnClickListener(this);
        findViewById(R.id.btn_calendar).setOnClickListener(this);
        findViewById(R.id.btn_vibrator).setOnClickListener(this);
        findViewById(R.id.btn_schedule).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_date_picker){
            Intent intent = new Intent(this, DatePickerActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_time_picker){
            Intent intent=new Intent(this,TimePickerActivity.class);
            startActivity(intent);
        }else  if(v.getId()==R.id.btn_view_pager){
            Intent intent=new Intent(this,ViewPagerActivity.class);
            startActivity(intent);
        }else  if(v.getId()==R.id.btn_view_pager){
            Intent intent=new Intent(this, PagerTitleStripActivity.class);
            startActivity(intent);
        }
    }
}
