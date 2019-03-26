package com.example.chapter13.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.chapter13.R;
import com.example.chapter13.util.DateUtil;
import com.example.chapter13.util.Utils;

public class VideoController extends RelativeLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String TAG="VideoController";
    private Context mContext;
    private ImageView mImagePlay;//声明用于播放控制的图像图像
    private TextView mCurrentTime;//声明用于展示播放时长的文本视图的对象
    private SeekBar mSeekBar;//拖动条对线
    private TextView mTotalTime;//声明用于展示播放时长的文本视图对象
    private int dip_10,dip_40;
    private int mBeginViewId=0x7F24FFF0;
    private VideoView mVideoView;
    private int mCurrent=0;//当前的播放时间
    private int mBuffer=0;//缓冲进度
    private int mDuration=0;//视频播放时长
    private boolean isPaused=false;//是否暂停

    public VideoController(Context context){
        super(context,null);
        mContext=context;
        dip_10= Utils.dip2px(mContext,10);
        dip_40=Utils.dip2px(mContext,40);
        initView();
    }

    private void initView() {
        mImagePlay=new ImageView(mContext);
        RelativeLayout.LayoutParams imageParams=new RelativeLayout.LayoutParams(dip_40,dip_40);
        //该视图在上级视图布局的垂直居中
        imageParams.addRule(RelativeLayout.CENTER_VERTICAL);
        mImagePlay.setLayoutParams(imageParams);
        mImagePlay.setId(mBeginViewId);
        mImagePlay.setOnClickListener(this);
        mImagePlay.setEnabled(false);

        //初始化一个用于展示当前时间的文本视图
        mCurrentTime=newTextView(mContext,mBeginViewId+1);
        RelativeLayout.LayoutParams currentParams=(LayoutParams)mCurrentTime.getLayoutParams();
        currentParams.setMargins(dip_10,0,0,0);
        //视图位于暂停/恢复 图标的右边s
        currentParams.addRule(RelativeLayout.RIGHT_OF,mImagePlay.getId());
        mCurrentTime.setLayoutParams(currentParams);

        //初始化一个用于展示播放时长的文本视图
        mTotalTime=newTextView(mContext,mBeginViewId+2);
        RelativeLayout.LayoutParams totalParams=(LayoutParams) mTotalTime.getLayoutParams();
        //该视图与上级布局的右侧对齐
        totalParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mTotalTime.setLayoutParams(totalParams);

        //创建一个新的拖动条
        mSeekBar=new SeekBar(mContext);
        RelativeLayout.LayoutParams seekParams=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        totalParams.setMargins(dip_10,10, dip_10,10);
        seekParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        //位于当前时间的右边
        seekParams.addRule(RelativeLayout.RIGHT_OF,mCurrentTime.getId());
        //位于总时间的左边
        seekParams.addRule(RelativeLayout.LEFT_OF,mTotalTime.getId());

        //设置拖动条的布局参数
        mSeekBar.setLayoutParams(seekParams);
        //设置最大进度
        mSeekBar.setMax(100);
        //最小高度
        mSeekBar.setMinimumHeight(50);
        //设置当前进度的偏移量
        mSeekBar.setThumbOffset(0);
        //设置拖动条编号
        mSeekBar.setId(mBeginViewId+3);
        //设置监视器
        mSeekBar.setOnSeekBarChangeListener(this);
        mSeekBar.setEnabled(false);

    }

    public VideoController(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==mImagePlay.getId()){
            if(mVideoView.isPlaying()){//视频视图正在播放
                mVideoView.pause();//视频视图暂停播放
                isPaused=true;
            }else{// 视频视图不在播放
                // MovieView需要扩展至全屏播放
//                if (mCurrent == 0 && mVideoView instanceof MovieView) {
//                    ((MovieView) mVideoView).begin(null);
//                }
                mVideoView.start();
                isPaused=false;
            }
        }
        refresh();
    }


    //创建一个新的文本视图
    private TextView newTextView(Context context,int id){
        TextView tv=new TextView(context);
        tv.setId(id);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(14);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        //视图在上级布局的垂直居中
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        tv.setLayoutParams(params);
        return tv;
    }

    //重置播放控制条
    private void reset(){
        if(mCurrent==0||isPaused){
            //处于开头或者处于暂停位置
            mImagePlay.setImageResource(R.drawable.btn_play);
        }else{
            //处于非暂停位置
            mImagePlay.setImageResource(R.drawable.btn_pause);
        }

        //在文本上视图上显示时间
        mCurrentTime.setText(DateUtil.formatDate(mCurrent));
        //在文本视图上显示播放时长
        mTotalTime.setText(DateUtil.formatDate(mDuration));

        if(mDuration==0){
            mSeekBar.setProgress(0);
        }else{
            mSeekBar.setProgress((mCurrent==0)?0:(mCurrent*100/mDuration));
        }
        //显示拖动条的缓冲进度

        mSeekBar.setSecondaryProgress(mBuffer);
    }

    //刷新播放控制条
    private void refresh(){
        invalidate();//立刻刷新布局
        requestLayout();//立刻调整布局
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        removeAllViews();
        reset();//重新播放控制条
        addView(mImagePlay);
        addView(mCurrentTime);
        addView(mTotalTime);
        addView(mSeekBar);
    }

    // 在进度变更时触发。第三个参数为true表示用户拖动，为false表示代码设置进度
    // 如果是人为的改变进度（即用户拖动进度条），则令视频从指定时间点开始播放
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            int time=progress*mDuration/100;
            //拖动播放器的当前进度到指定位置
            mVideoView.seekTo(time);
        }
    }

    //定义一个拖动条变更监视器接口
    public interface OnSeekChangeListener{
        void onStartSeek();
        void onStopSeek();
    }

    //声明一个拖动条的变更的监听器对象
    private OnSeekChangeListener mSeekListener;

    private void setOnSeekChangeListener(OnSeekChangeListener listener){
        mSeekListener=listener;
    }

    //stop拖动进度时触发

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(mSeekListener!=null){
            mSeekListener.onStopSeek();
        }
    }

    //start拖动进度时触发
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if(mSeekListener!=null){
            mSeekListener.onStartSeek();
        }
    }


    //启动播放控制条
    public void enableController(){
        mImagePlay.setEnabled(true);
        mSeekBar.setEnabled(true);
    }

    //为视频控制条设置关联的视频视图,同时获取该视频的播放时长
    public void setVedioView(VideoView view){
        mVideoView=view;
        mDuration=mVideoView.getDuration();
    }

    //设置当前的播放时间
    public void setCurrentTime(int current_time,int buffer_time){
        mCurrent=current_time;
        mBuffer=buffer_time;
        refresh();
    }
}
