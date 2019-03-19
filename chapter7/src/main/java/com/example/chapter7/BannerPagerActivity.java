package com.example.chapter7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chapter7.constant.ImageList;
import com.example.chapter7.util.Utils;
import com.example.chapter7.widget.BannerPager;

public class BannerPagerActivity extends AppCompatActivity implements BannerPager.BannerClickListener {
    private static final String TAG = "BannerPagerActivity";
    private TextView tv_pager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_pager);
        tv_pager=findViewById(R.id.tv_pager);

        BannerPager banner=findViewById(R.id.banner_pager);
        ViewGroup.LayoutParams params=(ViewGroup.LayoutParams)banner.getLayoutParams();
        params.height = (int) (Utils.getScreenWidth(this) * 250f / 640f);
        banner.setLayoutParams(params);
        banner.setImage(ImageList.getDefault());
        banner.setOnBannerListener(this);
        banner.start();

    }

    @Override
    public void onBannerClick(int positon) {

        String desc=String.format("你点击了第%d图片",positon+1);
        tv_pager.setText(desc);
    }
}
