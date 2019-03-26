package com.example.chapter13;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.aqi00.lib.dialog.FileSaveFragment;
import com.aqi00.lib.dialog.FileSelectFragment;


public class MediaControllActivity extends AppCompatActivity implements View.OnClickListener,
        FileSaveFragment.FileSaveCallbacks{

    private static final String TAG="MediaControllerActicity";
    private LinearLayout ll_play;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_controller);
        ll_play=findViewById(R.id.ll_play);
        findViewById(R.id.btn_open).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_open){
            String[] videoExs=new String[]{"mp4","3gp","mkv","mov","avi"};
            //打开文件选择对话框
            FileSelectFragment.show(this,videoExs,null);
        }
    }

    @Override
    public boolean onCanSave(String absolutePath, String fileName) {
        return true;
    }

    @Override
    public void onConfirmSave(String absolutePath, String fileName) {
        //拼接文件完整路径
        String file_path=absolutePath+"/"+fileName;
        Log.d(TAG,"file_path: "+file_path);
        //创建一个视频视图
        VideoView vv_content=new VideoView(this);
        //设置视频视频路径
        vv_content.setVideoPath(file_path);
        //获取焦点
        vv_content.requestFocus();
        //创建一个媒体控制条
        MediaController mc_play=new MediaController(this);
        //给媒体控制条绑定主视图 vv_content
        mc_play.setAnchorView(vv_content);
        //保持屏幕常亮
        mc_play.setKeepScreenOn(true);
        //给视频视图设置相关联的媒体控制条
        vv_content.setMediaController(mc_play);

        ll_play.addView(vv_content);
        vv_content.start();
    }
}
