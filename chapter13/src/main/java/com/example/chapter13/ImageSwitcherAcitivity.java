package com.example.chapter13;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.chapter13.adapter.GalleryAdapter;
import com.example.chapter13.task.GestureTask;
import com.example.chapter13.util.Utils;

@RequiresApi(api = Build.VERSION_CODES.N)
public class ImageSwitcherAcitivity extends AppCompatActivity implements View.OnTouchListener,
        AdapterView.OnItemClickListener , GestureTask.GestureCallback {

    private ImageSwitcher is_switcher;
    private Gallery gl_switcher;

    private int[] mImageRes = {
            R.drawable.scene1, R.drawable.scene2, R.drawable.scene3,
            R.drawable.scene4, R.drawable.scene5, R.drawable.scene6};

    private GestureDetector mGesture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);
        initImageSwitcher();
        initGallery();
    }

    private void initImageSwitcher() {
        is_switcher=findViewById(R.id.is_switcher);
        is_switcher.setFactory(new ViewFactoryImpl());
        is_switcher.setImageResource(mImageRes[0]);
        GestureTask gestureListener=new GestureTask();
        mGesture=new GestureDetector(this,gestureListener);
        gestureListener.setGestureCallback(this);
        is_switcher.setOnTouchListener(this);
    }

    private void initGallery(){
        int dip_pad= Utils.dip2px(this,20);
        gl_switcher=findViewById(R.id.gl_switcher);
        gl_switcher.setPadding(0,dip_pad,0,dip_pad);
        gl_switcher.setSpacing(dip_pad);
        gl_switcher.setUnselectedAlpha(0.5f);
        gl_switcher.setAdapter(new GalleryAdapter(this,mImageRes));
        gl_switcher.setOnItemClickListener(this);
    }

    public class ViewFactoryImpl implements ViewSwitcher.ViewFactory{

        @Override
        public View makeView() {
            ImageView iv=new ImageView(ImageSwitcherAcitivity.this);
            iv.setBackgroundColor(Color.WHITE);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            return iv;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //由手势检测器接管触摸事件
        mGesture.onTouchEvent(event);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //图像切换器设置淡入动画
        is_switcher.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in));
        //图像切换器设置淡出动画
        is_switcher.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_out));
        //图像切换器设置图片的资源编号
        is_switcher.setImageResource(mImageRes[position]);
    }

    @Override
    public void gotoNext() {
        //给图像设置向左淡入动画
        is_switcher.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
        //给图像设置向右淡入动画
        is_switcher.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));

        int next_pos=(int)(gl_switcher.getSelectedItemId()+1);
        if(next_pos>=mImageRes.length){
            next_pos=0;
        }

        is_switcher.setImageResource(mImageRes[next_pos]);
        //设置画廊视图的选中项
        gl_switcher.setSelection(next_pos);
    }

    //切换到上一页时触发
    @Override
    public void gotoPre() {
        is_switcher.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
        is_switcher.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));

        int pre_pos = (int) (gl_switcher.getSelectedItemId() - 1);
        if (pre_pos < 0) {
            pre_pos = mImageRes.length - 1;
        }
        // 给图像切换器设置图片的资源编号
        is_switcher.setImageResource(mImageRes[pre_pos]);
        // 设置画廊视图的选中项
        gl_switcher.setSelection(pre_pos);
    }

}
