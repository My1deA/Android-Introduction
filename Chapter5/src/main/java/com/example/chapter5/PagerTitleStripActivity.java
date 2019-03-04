package com.example.chapter5;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.Toast;

import com.example.chapter5.adapter.ImagePagerAdapater;
import com.example.chapter5.adapter.PlanetListAdapter;
import com.example.chapter5.bean.GoodsInfo;

import java.util.ArrayList;

public class PagerTitleStripActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<GoodsInfo> goodsInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_strip);
        initPagerStrip();
        initViewPager();
    }

    private void initPagerStrip() {
        PagerTitleStrip pts_title=findViewById(R.id.pts_title);
        pts_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
        pts_title.setTextColor(Color.BLUE);
    }

    private void initViewPager() {
        goodsInfos=GoodsInfo.getDefaultList();
        ImagePagerAdapater adapater=new ImagePagerAdapater(this,goodsInfos);
        ViewPager viewPager=findViewById(R.id.vp_content);
        viewPager.setAdapter(adapater);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int position) {
        Toast.makeText(this,"你翻到的手机品牌是:"+goodsInfos.get(position).name,Toast.LENGTH_SHORT).show();
    }
}
