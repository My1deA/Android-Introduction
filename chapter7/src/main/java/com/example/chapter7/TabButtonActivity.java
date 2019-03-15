package com.example.chapter7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TabButtonActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView tv_tab_button;
    private CheckBox ck_select;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_button);
        tv_tab_button=findViewById(R.id.tv_tab_button);
        tv_tab_button.setOnClickListener(this);
        ck_select=findViewById(R.id.ck_select);
        ck_select.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_tab_button){
            tv_tab_button.setSelected(!ck_select.isChecked());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId()==R.id.ck_select){
            tv_tab_button.setSelected(isChecked);
        }
    }
}
