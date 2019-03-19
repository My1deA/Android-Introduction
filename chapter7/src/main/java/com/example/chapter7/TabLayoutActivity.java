package com.example.chapter7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chapter7.adapter.GoodsPagerAdapter;
import com.example.chapter7.util.DateUtil;
import com.example.chapter7.util.MenuUtil;

import java.util.ArrayList;

public class TabLayoutActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    private final static String TAG="TabLayoutActivity";
    private ViewPager vp_content;
    private TabLayout tab_title;
    private ArrayList<String> mTitleArray=new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        Toolbar tl_head=findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        mTitleArray.add("商品");
        mTitleArray.add("详情");
        initTablayout();
        initTabViewPager();
    }

    private void initTablayout() {
        tab_title=findViewById(R.id.tab_title);
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(0)));
        tab_title.addTab(tab_title.newTab().setText(mTitleArray.get(1)));
        tab_title.addOnTabSelectedListener(this);
    }

    private void initTabViewPager() {
        vp_content=findViewById(R.id.vp_content);
        GoodsPagerAdapter adapter=new GoodsPagerAdapter(getSupportFragmentManager(),mTitleArray);
        vp_content.setAdapter(adapter);
        vp_content.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                tab_title.getTabAt(position).select();
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp_content.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        MenuUtil.setOverflowIconVisible(featureId,menu);
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overflow,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==android.R.id.home){
            finish();
        }else if(id==R.id.menu_refresh){
            Toast.makeText(this, "当前刷新时间: " + DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss")
                    , Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_about) { // 点击了关于菜单项
            Toast.makeText(this, "这个是标签布局的演示demo", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_quit) { // 点击了退出菜单项
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
