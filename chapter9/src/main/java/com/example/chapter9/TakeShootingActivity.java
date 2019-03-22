package com.example.chapter9;

import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.chapter9.widget.Camera2View;

public class TakeShootingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG="TakeShootingActivity";
    private Camera2View camera2View;
    private int mTakeType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_shooting);

        // 获取前一个页面传来的摄像头类型
        int camera_type = getIntent().getIntExtra("type", CameraCharacteristics.LENS_FACING_FRONT);
        // 从布局文件中获取名叫camera2_view的二代相机视图
        camera2View = findViewById(R.id.camera2_view);
        // 设置二代相机视图的摄像头类型
        camera2View.open(camera_type);
        findViewById(R.id.btn_shutter).setOnClickListener(this);
        findViewById(R.id.btn_shooting).setOnClickListener(this);
    }

    public void onBackPressed(){
        Intent intent=new Intent();
        Bundle bundle=new Bundle();
        String photo_path=camera2View.getPhotoPath();
        bundle.putInt("type",mTakeType);
        if(photo_path==null&&mTakeType==0){
            bundle.putString("is_null","yes");
        }else{
            bundle.putString("is_null","no");
            if(mTakeType==0){
                bundle.putString("path",photo_path);
            }else if(mTakeType==1){
                bundle.putStringArrayList("path_list",camera2View.getShootingArray());
            }
        }
        //返回
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_shutter){
            mTakeType=0;
            camera2View.takePicture();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(TakeShootingActivity.this, "已完成拍照，按返回键回到上页查看照片。", Toast.LENGTH_SHORT).show();
                }
            },1500);
        }else if(v.getId()==R.id.btn_shooting){
            mTakeType=1;
            camera2View.startShooting(7000);
        }
    }
}
