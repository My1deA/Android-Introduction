package com.example.chapter5;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.chapter5.adapter.PlanetListAdapter;
import com.example.chapter5.bean.Planet;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private final static String TAG="ListViewActivity";
    private ListView lv_planet;
    private Drawable drawable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ArrayList<Planet> planets=Planet.getDefaultList();
        PlanetListAdapter planetListAdapter=new PlanetListAdapter(this,planets);
        //从布局视图中获得名叫lv_planet
        lv_planet=findViewById(R.id.lv_planet);

        lv_planet.setAdapter(planetListAdapter);
        lv_planet.setOnItemClickListener(planetListAdapter);
        lv_planet.setOnItemLongClickListener(planetListAdapter);
        drawable=getResources().getDrawable(R.drawable.divider_red2);
        //初始下拉框
        initDividerSpinner();


    }

    private void initDividerSpinner() {
        ArrayAdapter<String> dividerAdapter=new ArrayAdapter<String>(this,R.layout.item_select,dividerArray);
        Spinner sp=findViewById(R.id.sp_list);
        sp.setAdapter(dividerAdapter);
        sp.setPrompt("选择分割线显示方式");
        sp.setSelection(0);
        sp.setOnItemSelectedListener(new DividerSelectedListener());

    }

    private String[] dividerArray = {
            "不显示分隔线(分隔线高度为0)",
            "不显示分隔线(分隔线为null)",
            "只显示内部分隔线(先设置分隔线高度)",
            "只显示内部分隔线(后设置分隔线高度)",
            "显示底部分隔线(高度是wrap_content)",
            "显示底部分隔线(高度是match_parent)",
            "显示顶部分隔线(别瞎折腾了，显示不了)",
            "显示全部分隔线(看我用padding大法)"
    };

    class DividerSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int arg2, long id) {
            int dividerHeight=5;
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lv_planet.setDivider(drawable);
            lv_planet.setDividerHeight(dividerHeight);
            lv_planet.setPadding(0, 0, 0, 0);  // 设置lv_planet的四周空白
            lv_planet.setBackgroundColor(Color.TRANSPARENT);

            if (arg2 == 0) {  // 不显示分隔线(分隔线高度为0)
                lv_planet.setDividerHeight(0);
            } else if (arg2 == 1) {  // 不显示分隔线(分隔线为null)
                lv_planet.setDivider(null);
                lv_planet.setDividerHeight(dividerHeight);
            } else if (arg2 == 2) {  // 只显示内部分隔线(先设置分隔线高度)
                lv_planet.setDividerHeight(dividerHeight);
                lv_planet.setDivider(drawable);
            } else if (arg2 == 3) {  // 只显示内部分隔线(后设置分隔线高度)
                lv_planet.setDivider(drawable);
                lv_planet.setDividerHeight(dividerHeight);
            } else if (arg2 == 4) {  // 显示底部分隔线(高度是wrap_content)
                lv_planet.setFooterDividersEnabled(true);
            } else if (arg2 == 5) {  // 显示底部分隔线(高度是match_parent)
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                lv_planet.setFooterDividersEnabled(true);
            } else if (arg2 == 6) {  // 显示顶部分隔线(别瞎折腾了，显示不了)
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
                lv_planet.setFooterDividersEnabled(true);
                lv_planet.setHeaderDividersEnabled(true);
            } else if (arg2 == 7) {  // 显示全部分隔线(看我用padding大法)
                lv_planet.setDivider(null);
                lv_planet.setDividerHeight(dividerHeight);
                lv_planet.setPadding(0, dividerHeight, 0, dividerHeight);
                lv_planet.setBackgroundDrawable(drawable);
            }
            lv_planet.setLayoutParams(params);  // 设置lv_planet的布局参数
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
