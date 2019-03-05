package com.example.chapter5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.chapter5.adapter.LaunchSimpleAdapter;

public class LaunchSimpleActivity extends AppCompatActivity {
    //声明引导页面的图片数组
    private int[] launchImageArray={
            R.drawable.guide_bg1, R.drawable.guide_bg2,R.drawable.guide_bg3,R.drawable.guide_bg4
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ViewPager vp_launch = findViewById(R.id.vp_launch);

        //构建一个引导页面的翻页适配器
        LaunchSimpleAdapter adapter=new LaunchSimpleAdapter(this,launchImageArray);
        vp_launch.setAdapter(adapter);
        vp_launch.setCurrentItem(0);

    }
}
