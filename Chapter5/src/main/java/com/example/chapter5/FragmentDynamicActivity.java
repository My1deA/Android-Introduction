package com.example.chapter5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;

import com.example.chapter5.adapter.MobilePagerAdapter;
import com.example.chapter5.bean.GoodsInfo;

import java.util.ArrayList;

public class FragmentDynamicActivity extends AppCompatActivity {
    private static  final String TAG="FragmentDynamicActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_dynamic);
        Log.d(TAG,"onCreate");
        initPagerStrip();
        initViewPager();
    }

    private void initPagerStrip() {
        PagerTabStrip pts_tab=findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        pts_tab.setTextColor(Color.BLACK);
    }

    private void initViewPager(){
        ArrayList<GoodsInfo> goodsInfos=GoodsInfo.getDefaultList();
        MobilePagerAdapter adapter=new MobilePagerAdapter(getSupportFragmentManager(),goodsInfos);
        ViewPager vp_content=findViewById(R.id.vp_content);
        vp_content.setAdapter(adapter);
        vp_content.setCurrentItem(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
}
