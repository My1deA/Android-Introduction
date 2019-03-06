package com.example.chapter5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.LinearLayout;

import com.example.chapter5.adapter.BroadcastPagerAdapter;
import com.example.chapter5.bean.GoodsInfo;
import com.example.chapter5.fragment.BroadcastFragment;

import java.util.ArrayList;

public class BroadTempActivity extends AppCompatActivity {
    private static final String TAG="BroadTempActivity";
    private LinearLayout ll_brd_temp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_temp);
        ll_brd_temp=findViewById(R.id.ll_brd_temp);
        initPagerStrip();
        initViewPager();
    }

    private void initPagerStrip() {
        PagerTabStrip pts_tab=findViewById(R.id.pts_tab);
        pts_tab.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        pts_tab.setTextColor(Color.BLACK);
    }

    private void initViewPager() {
        ArrayList<GoodsInfo> goodsList=GoodsInfo.getDefaultList();
        BroadcastPagerAdapter adapter=new BroadcastPagerAdapter(getSupportFragmentManager(),goodsList);

        ViewPager vp_content=findViewById(R.id.vp_content);
        vp_content.setAdapter(adapter);
        vp_content.setCurrentItem(0);
    }

    private BgChangeReceiver bgChangeReceiver;

    private class BgChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                int color=intent.getIntExtra("color",Color.WHITE);
                ll_brd_temp.setBackgroundColor(color);
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        // 创建一个背景色变更的广播接收器
        bgChangeReceiver = new BgChangeReceiver();
        // 创建一个意图过滤器，只处理指定事件来源的广播
        IntentFilter filter = new IntentFilter(BroadcastFragment.EVENT);
        // 注册广播接收器，注册之后才能正常接收广播
        LocalBroadcastManager.getInstance(this).registerReceiver(bgChangeReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 注销广播接收器，注销之后就不再接收广播
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bgChangeReceiver);
    }

}
