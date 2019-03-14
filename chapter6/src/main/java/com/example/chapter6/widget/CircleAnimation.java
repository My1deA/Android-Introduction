package com.example.chapter6.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;


import com.example.chapter6.util.Utils;

public class CircleAnimation extends RelativeLayout {
    private final static String TAG="CicleAnimation";
    private RectF mRect;//矩形边界
    private int mBeginAngle=0;//起始角度
    private int mEndAngle=0;
    private float mFrontLine=10;
    private int mFrontColor=0xffff0000;
    private Paint.Style mFrontStyle= Paint.Style.STROKE;
    private FrontView mFrontView;
    private int mShadeColor = 0xffeeeeee; // 阴影颜色
    private float mShadeLine = 10; // 阴影线宽
    private Paint.Style mShadeStyle = Paint.Style.STROKE; // 阴影风格。STROK表示空心，FILL表示实心
    private ShadeView mShadeView; // 阴影视图
    private int mRate=2;
    private int mDrawTimes;
    private int mInterval=70;
    private int mFactor;
    private int mSeq;
    private int mDrawingAngle=0;
    private Context mContext;

    public CircleAnimation(Context context){
        super(context);
        mContext=context;
        mRect=new RectF(Utils.dip2px(context,30), Utils.dip2px(context,10), Utils.dip2px(context,330), Utils.dip2px(context,310));

    }

    //渲染圆弧动画 渲染操作包括初始化和播放两个动作
    public void render(){
        removeAllViews();
        mShadeView=new ShadeView(mContext);
        addView(mShadeView);
        mFrontView=new FrontView(mContext);
        addView(mFrontView);
        play();
    }

    public void play() {
        mSeq=0;
        mDrawingAngle=0;
        mDrawTimes=mEndAngle/mRate;
        mFactor=mDrawTimes/mInterval+1;
        Log.d(TAG, "mDrawTimes=" + mDrawTimes + ",mInterval=" + mInterval + ",mFactor=" + mFactor);
        mFrontView.invalidateView();

    }


    public void setRect(int left,int top,int right,int bottom){
        mRect=new RectF(left,top,right,bottom);
    }

    public void setAngle(int begin_angle,int end_angle){
        mBeginAngle=begin_angle;
        mEndAngle=end_angle;
    }

    public void setRate(int speed, int frames) {
        mRate = speed;
        mInterval = 1000 / frames;
    }

    public void setFront(int color, float line, Style style){
        mFrontColor=color;
        mFrontLine=line;
        mFrontStyle=style;
    }

    public void setShade(int color,float line,Style style){
        mShadeColor=color;
        mShadeLine=line;
        mShadeStyle=style;
    }



    private class ShadeView extends View{
        private Paint paint;
        public ShadeView(Context context){
            super(context);
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(mShadeColor);
            paint.setStrokeWidth(mShadeLine);
            paint.setStyle(mShadeStyle);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawArc(mRect,mBeginAngle,360,false,paint);
        }
    }

    private class FrontView extends View {
        private Paint paint;
        private Handler handler=new Handler();

        public FrontView(Context context){
            super(context);
            paint=new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(mFrontColor);
            paint.setStrokeWidth(mFrontLine);
            paint.setStyle(mFrontStyle);
            paint.setStrokeCap(Paint.Cap.ROUND);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawArc(mRect, mBeginAngle, (float) (mDrawingAngle), false, paint);
        }

        public void invalidateView(){
            handler.post(drawRunnable);
        }

        private Runnable drawRunnable=new Runnable() {
            @Override
            public void run() {
                if(mDrawingAngle>=mEndAngle){
                    mDrawingAngle=mEndAngle;
                    invalidate();
                    handler.removeCallbacks(drawRunnable); // 清除绘制任务
                }else{
                    mDrawingAngle=mSeq*mRate;
                    mSeq++;
                    //间隔若干时间后
                    handler.postDelayed(drawRunnable,(long)(mInterval-mSeq/mFactor));
                    invalidate();
                }
            }
        };

    }

}














