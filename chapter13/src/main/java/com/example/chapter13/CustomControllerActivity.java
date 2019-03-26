package com.example.chapter13;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSelectFragment;
import com.example.chapter13.widget.VideoController;

public class CustomControllerActivity extends AppCompatActivity implements View.OnClickListener, FileSaveFragment.FileSaveCallbacks {

    private VideoView vv_content;
    private VideoController vc_play;
    private Handler mHandler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_controller);
        findViewById(R.id.btn_open).setOnClickListener(this);
        vv_content=findViewById(R.id.vv_content);
        vc_play=findViewById(R.id.vc_play);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_open){
            String[] videoExs=new String[]{"mp4","3gp","mkv","mov","avi"};
            //打开文件对话框
            FileSelectFragment.show(this,videoExs,null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCurrentPosition>0&&!vv_content.isPlaying()){
            vv_content.seekTo(mCurrentPosition);
            vv_content.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 暂停页面时保存当前的播放进度
        if (vv_content.isPlaying()) { // 视频视图正在播放

        }   // 获得视频视图当前的播放位置
        mCurrentPosition = vv_content.getCurrentPosition();
        // 视频视图暂停播放
        vv_content.pause();
    }


    @Override
    public boolean onCanSave(String absolutePath, String fileName) {
        return true;
    }

    private int mCurrentPosition=0;

    private Runnable mRefresh=new Runnable() {
        @Override
        public void run() {
            if(vv_content.isPlaying()){
                vc_play.setCurrentTime(vv_content.getCurrentPosition(),vv_content.getBufferPercentage());
            }
            mHandler.postDelayed(this,500);
        }
    };


    @Override
    public void onConfirmSave(String absolutePath, String fileName) {
        //拼接文件的完整路径
        String file_path=absolutePath+"/"+fileName;

        vv_content.setVideoPath(file_path);
        vv_content.requestFocus();

        vv_content.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //启动视频控制条
                vc_play.enableController();
                vc_play.setVedioView(vv_content);
                mHandler.post(mRefresh);
            }
        });

        vv_content.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vc_play.setCurrentTime(0,0);
            }
        });
        vv_content.start();
    }
}
