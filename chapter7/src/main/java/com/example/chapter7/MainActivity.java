package com.example.chapter7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_tab_button).setOnClickListener(this);
        findViewById(R.id.btn_tab_host).setOnClickListener(this);
        findViewById(R.id.btn_tab_group).setOnClickListener(this);
        findViewById(R.id.btn_tab_fragment).setOnClickListener(this);
        findViewById(R.id.btn_toolbar).setOnClickListener(this);
        findViewById(R.id.btn_toolbar_custom).setOnClickListener(this);
        findViewById(R.id.btn_overflow_menu).setOnClickListener(this);
        findViewById(R.id.btn_search_view).setOnClickListener(this);
        findViewById(R.id.btn_tab_layout).setOnClickListener(this);
        findViewById(R.id.btn_tab_custom).setOnClickListener(this);
        findViewById(R.id.btn_banner_indicator).setOnClickListener(this);
        findViewById(R.id.btn_banner_pager).setOnClickListener(this);
        findViewById(R.id.btn_banner_top).setOnClickListener(this);
        findViewById(R.id.btn_recycler_linear).setOnClickListener(this);
        findViewById(R.id.btn_recycler_grid).setOnClickListener(this);
        findViewById(R.id.btn_recycler_combine).setOnClickListener(this);
        findViewById(R.id.btn_recycler_staggered).setOnClickListener(this);
        findViewById(R.id.btn_recycler_dynamic).setOnClickListener(this);
        findViewById(R.id.btn_coordinator).setOnClickListener(this);
        findViewById(R.id.btn_appbar_recycler).setOnClickListener(this);
        findViewById(R.id.btn_appbar_nested).setOnClickListener(this);
        findViewById(R.id.btn_collapse_pin).setOnClickListener(this);
        findViewById(R.id.btn_collapse_parallax).setOnClickListener(this);
        findViewById(R.id.btn_image_fade).setOnClickListener(this);
        findViewById(R.id.btn_scroll_flag).setOnClickListener(this);
        findViewById(R.id.btn_scroll_alipay).setOnClickListener(this);
        findViewById(R.id.btn_swipe_refresh).setOnClickListener(this);
        findViewById(R.id.btn_swipe_recycler).setOnClickListener(this);
        findViewById(R.id.btn_department_store).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_tab_host) {
            Intent intent = new Intent(this, TabHostActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_tab_button){
            Intent intent = new Intent(this, TabButtonActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_tab_fragment){
            Intent intent = new Intent(this, TabFragmentActivity.class);
            startActivity(intent);
        }
    }
}
