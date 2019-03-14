package com.example.chapter6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.chapter6.widget.CircleAnimation;

public class CircleAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private CircleAnimation mAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_animation);
        findViewById(R.id.btn_play).setOnClickListener(this);
        LinearLayout ll_layout=findViewById(R.id.ll_layout);
        mAnimation = new CircleAnimation(this);
        // 把圆弧动画添加到线性布局ll_layout之中
        ll_layout.addView(mAnimation);
        // 渲染圆弧动画。渲染操作包括初始化与播放两个动作
        mAnimation.render();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_play){
            mAnimation.play();
        }
    }
}
