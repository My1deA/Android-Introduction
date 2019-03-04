package com.example.chapter5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.chapter5.adapter.ImagePagerAdapater;
import com.example.chapter5.bean.GoodsInfo;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ArrayList<GoodsInfo> goodsInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        goodsInfos=GoodsInfo.getDefaultList();
        ImagePagerAdapater adapater=new ImagePagerAdapater(this,goodsInfos);
        ViewPager vp_content=findViewById(R.id.vp_content);
        vp_content.setAdapter(adapater);
        vp_content.setCurrentItem(0);
        vp_content.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int position) {
        Toast.makeText(this, "您翻到的手机品牌是：" + goodsInfos.get(position).name, Toast.LENGTH_SHORT).show();
    }
}
