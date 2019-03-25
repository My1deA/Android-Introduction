package com.example.chapter13;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.aqi00.lib.dialog.*;
import com.aqi00.lib.dialog.FileSelectFragment.FileSelectCallbacks;

import java.util.Map;


public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener,
        FileSelectFragment.FileSelectCallbacks, SeekBar.OnSeekBarChangeListener {

    private static final String TAG="VideoViewActivity";
    private VideoView vv_play;
    private SeekBar sb_play;
    private Handler mHandler=new Handler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        findViewById(R.id.btn_open).setOnClickListener(this);

        vv_play=findViewById(R.id.vv_content);
        sb_play=findViewById(R.id.sb_play);
        sb_play.setOnSeekBarChangeListener(this);
        sb_play.setEnabled(false);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_open){
            String[] videoExs=new String[]{"mp4","3gp","mkv","mov","avi"};
            //打开文件对话框
            FileSelectFragment.show(this,videoExs,null);
        }
    }


    //进度变更时触发 true 用户拖动 false 是自己设置
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    //开始拖动进度时触发
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    //停止拖动时触发
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int pos=seekBar.getProgress()*vv_play.getDuration()/100;
        //命令视频视图从指定位置开始播放
        vv_play.seekTo(pos);
    }

    @Override
    public void onConfirmSelect(String absolutePath, String fileName, Map<String, Object> map_param) {
        String file_path=absolutePath+"/"+fileName;
        Log.d(TAG,"file_path:"+file_path);
        vv_play.setVideoPath(file_path);
        vv_play.requestFocus();
        vv_play.start();
        sb_play.setEnabled(true);
        mHandler.post(mRefresh);
    }

    @Override
    public boolean isFileValid(String absolutePath, String fileName, Map<String, Object> map_param) {
        return true;
    }

    private Runnable mRefresh=new Runnable() {
        @Override
        public void run() {
            //通过播放时长与当前位置，计算视频已经播放的百分比
            int progress=100*vv_play.getCurrentPosition()/vv_play.getDuration();
            sb_play.setProgress(progress);
            mHandler.postDelayed(this,500);
        }
    };
}
