package com.example.z2;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.LinearLayout;

public class AppMainActivity extends ActivityGroup implements View.OnClickListener {

    private static final String TAG="AppMainActivity";
    private Bundle mBundle=new Bundle();
    private LinearLayout  ll_container,ll_home,ll_find,ll_message,ll_me;
    public static Boolean isLogin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        ll_container=findViewById(R.id.ll_container);
        ll_home=findViewById(R.id.ll_home);
        ll_find=findViewById(R.id.ll_find);
        ll_message=findViewById(R.id.ll_message);
        ll_me=findViewById(R.id.ll_me);

        ll_home.setOnClickListener(this);
        ll_find.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_me.setOnClickListener(this);
        mBundle.putString("tag", TAG); // 往包裹中存入名叫tag的标记串
        changeContainerView(ll_home);
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ll_find||v.getId()==R.id.ll_message||v.getId()==R.id.ll_me||v.getId()==R.id.ll_home){
            changeContainerView(v);
        }
    }

    //设置跳转页面
    private  void changeContainerView(View v) {
        ll_home.setSelected(false);// 取消选中第一个标签
        ll_find.setSelected(false);
        ll_message.setSelected(false);
        ll_me.setSelected(false);
        v.setSelected(true);
        if(v==ll_home){
            toActivity("home",AppHomeActivity.class);
        }else if(v==ll_find){
            toActivity("find",AppFindActivity.class);
        }else if(v==ll_message){
            toActivity("message",AppMessageActivity.class);
        }else if(v==ll_me){
                toActivity("me",AppMeActivity.class);
        }
    }

    private void toActivity(String label,Class<?> cls){
        // 创建一个意图，并存入指定包裹
        Intent intent =new Intent(this,cls).putExtras(mBundle);
        // 移除内容框架下面的所有下级视图
        ll_container.removeAllViews();
        // 启动意图指向的活动，并获取该活动页面的顶层视图
        View v=getLocalActivityManager().startActivity(label,intent).getDecorView();
        // 设置内容视图的布局参数
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        // 把活动页面的顶层视图（即内容视图）添加到内容框架上
        ll_container.addView(v);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1){
//            AppMeActivity appMeActivity= (AppMeActivity) getLocalActivityManager().getCurrentActivity();
//            appMeActivity.handleActivityResult(requestCode,resultCode,data);
//        }
//    }
}
