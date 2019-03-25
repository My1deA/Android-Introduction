package com.example.chapter13;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chapter13.util.PermisssionUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_gallery).setOnClickListener(this);
        findViewById(R.id.btn_recycler_view).setOnClickListener(this);
        findViewById(R.id.btn_image_switcher).setOnClickListener(this);
        findViewById(R.id.btn_card_view).setOnClickListener(this);
        findViewById(R.id.btn_palette).setOnClickListener(this);
        findViewById(R.id.btn_ring_tone).setOnClickListener(this);
        findViewById(R.id.btn_sound_pool).setOnClickListener(this);
        findViewById(R.id.btn_audio_track).setOnClickListener(this);
        findViewById(R.id.btn_video_view).setOnClickListener(this);
        findViewById(R.id.btn_video_controller).setOnClickListener(this);
        findViewById(R.id.btn_media_controller).setOnClickListener(this);
        findViewById(R.id.btn_custom_controller).setOnClickListener(this);
        findViewById(R.id.btn_split_screen).setOnClickListener(this);
        findViewById(R.id.btn_pic_in_pic).setOnClickListener(this);
        findViewById(R.id.btn_float_window).setOnClickListener(this);
        findViewById(R.id.btn_screen_capture).setOnClickListener(this);
        findViewById(R.id.btn_screen_record).setOnClickListener(this);
        findViewById(R.id.btn_orientation).setOnClickListener(this);
        findViewById(R.id.btn_movie_player).setOnClickListener(this);
        findViewById(R.id.btn_spannable).setOnClickListener(this);
        findViewById(R.id.btn_music_player).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_image_switcher){
            Intent intent=new Intent(this,ImageSwitcherAcitivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_video_view){

            if(PermisssionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    R.id.btn_video_view%4096)){
                PermisssionUtil.goActivity(this,VideoViewActivity.class);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        if(requestCode==R.id.btn_video_view){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                PermisssionUtil.goActivity(this,VideoViewActivity.class);
            }else{
                Toast.makeText(this,"需要允许SD卡权限才能观看视频",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
