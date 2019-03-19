package com.example.chapter7;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ToolbarActivity extends AppCompatActivity {
    private final static String TAG="ToolbarActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar tl_head=findViewById(R.id.tl_head);

        tl_head.setNavigationIcon(R.drawable.ic_back);
        tl_head.setTitle("工具页面");
        tl_head.setTitleTextColor(Color.RED);
        tl_head.setLogo(R.drawable.ic_app);
        tl_head.setSubtitle("Toolbar");
        tl_head.setSubtitleTextColor(Color.YELLOW);
        tl_head.setBackgroundResource(R.color.blue_light);
        setSupportActionBar(tl_head);
        tl_head.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 结束当前页面
            }
        });
    }
}
