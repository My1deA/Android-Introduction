package com.example.chapter13.task;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureTask implements GestureDetector.OnGestureListener {

    private float mFlipGap=20f;

    public GestureTask(){

    }
    //手势按下
    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    //已经按下但还没滑动或者松开触发
    @Override
    public void onShowPress(MotionEvent e) {

    }

    //轻点时触发，点击时触发
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    //手势滑动触发
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    //手势长按触发
    @Override
    public void onLongPress(MotionEvent e) {

    }

    //在手势飞快掠过是触发
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //从右向左掠过
        if(e1.getX()-e2.getX()>mFlipGap){
            if(mListener!=null){
                mListener.gotoNext();
            }
        }
        if(e1.getX()-e2.getX()<-mFlipGap){
            mListener.gotoNext();
        }
        return true;
    }

    private GestureCallback mListener;

    public void setGestureCallback(GestureCallback listener){
        mListener=listener;
    }

    public interface GestureCallback{
        void gotoNext();
        void gotoPre();
    }
}
