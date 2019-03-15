package com.example.chapter7;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class TabHostActivity extends TabActivity implements View.OnClickListener {

    private static final String TAG="TabHostActivity";
    private Bundle mBundle=new Bundle();
    private TabHost tab_host;
    private LinearLayout ll_first,ll_second,ll_third;
    private String First_TAG="first";
    private String Second_TAG="second";
    private String Third_TAG="third";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        mBundle.putString("tag",TAG);
        ll_first = findViewById(R.id.ll_first); // 获取第一个标签的线性布局
        ll_second = findViewById(R.id.ll_second); // 获取第二个标签的线性布局
        ll_third = findViewById(R.id.ll_third); // 获取第三个标签的线性布局
        ll_first.setOnClickListener(this); // 给第一个标签注册点击监听器
        ll_second.setOnClickListener(this); // 给第二个标签注册点击监听器
        ll_third.setOnClickListener(this);

        tab_host=getTabHost();
        tab_host.addTab(getNewTab(First_TAG,R.string.menu_first,R.drawable.tab_first_selector,TabFirstActivity.class));
        tab_host.addTab(getNewTab(First_TAG,R.string.menu_second,R.drawable.tab_second_selector,TabSecondActivity.class));
        tab_host.addTab(getNewTab(First_TAG,R.string.menu_third,R.drawable.tab_third_selector,TabThirdActivity.class));
    }

    private TabHost.TabSpec getNewTab(String spec,int label,int icon,Class<?> cls){
        Intent intent=new Intent(this,cls).putExtras(mBundle);
        // 生成并返回新的标签规格（包括内容意图、标签文字和标签图标）
        return tab_host.newTabSpec(spec).setContent(intent)
                .setIndicator(getString(label), getResources().getDrawable(icon));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_first || v.getId() == R.id.ll_second || v.getId() == R.id.ll_third) {
            changeContainerView(v); // 点击了哪个标签，就切换到该标签对应的内容视图
        }
    }

    private void changeContainerView(View v) {
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        v.setSelected(true);
        if(v==ll_first){
            tab_host.setCurrentTabByTag(First_TAG);
        }else if(v==ll_second){
            tab_host.setCurrentTabByTag(Second_TAG);
        }else if(v==ll_third){
            tab_host.setCurrentTabByTag(Third_TAG);
        }

    }
}
