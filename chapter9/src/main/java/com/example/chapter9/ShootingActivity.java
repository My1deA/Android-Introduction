package com.example.chapter9;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chapter9.adapter.ShootingAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class ShootingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ShootingActivity";
    private FrameLayout fl_content;
    private ImageView iv_photo;
    private GridView gv_shooting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooting);
        fl_content = findViewById(R.id.fl_content);
        iv_photo = findViewById(R.id.iv_photo);
        findViewById(R.id.btn_catch_front).setOnClickListener(this);
        findViewById(R.id.btn_catch_behind).setOnClickListener(this);
    }


    private boolean checkCamera(String[] ids, String type) {
        boolean result = false;
        for (String id : ids) {
            if (id.equals(type)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void fillBitmap(Bitmap bitmap) {
        Log.d(TAG, "fillBitmap width=" + bitmap.getWidth() + ",height=" + bitmap.getHeight());
        if (bitmap.getHeight() > fl_content.getMeasuredHeight()) {
            ViewGroup.LayoutParams layoutParams = iv_photo.getLayoutParams();
            layoutParams.height = fl_content.getMeasuredHeight();
            layoutParams.width = bitmap.getWidth() * fl_content.getMeasuredHeight() / bitmap.getHeight();

            iv_photo.setLayoutParams(layoutParams);
        }
        iv_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv_photo.setImageBitmap(bitmap);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "onActivityResult. requestCode=" + requestCode + ", resultCode=" + resultCode);
        Bundle resp = intent.getExtras();//取回快递
        String isZ_null = resp.getString("is_null");
        if (!TextUtils.isEmpty(isZ_null) && !isZ_null.equals("yes")) {
            int type = resp.getInt("type");
            Log.d(TAG, "type=" + type);
            if (type == 0) {
                iv_photo.setVisibility(View.VISIBLE);
                gv_shooting.setVerticalSpacing(View.GONE);
                String path = resp.getString("path");
                fillBitmap(BitmapFactory.decodeFile(path, null));
            } else if (type == 1) {
                iv_photo.setVisibility(View.GONE);
                gv_shooting.setVisibility(View.VISIBLE);
                ArrayList<String> pathList = resp.getStringArrayList("path_list");
                Log.d(TAG, "pathList.size()=" + pathList.size());
                ShootingAdapter adapter = new ShootingAdapter(this, pathList);
                gv_shooting.setAdapter(adapter);
            }

        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        android.hardware.camera2.CameraManager cm = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String[] ids;
        try {
            ids = cm.getCameraIdList();
        } catch (CameraAccessException e) {
            e.printStackTrace();
            return;
        }

        if(v.getId()==R.id.btn_catch_behind){
            if(checkCamera(ids, CameraCharacteristics.LENS_FACING_FRONT+"")){
                Intent intent=new Intent(this,TakeShootingActivity.class);
                intent.putExtra("type",CameraCharacteristics.LENS_FACING_FRONT);
                startActivityForResult(intent,1);
            }else {
                Toast.makeText(this, "当前设备不支持后置摄像头", Toast.LENGTH_SHORT).show();
            }

        }else if(v.getId()==R.id.btn_catch_front){
            if(checkCamera(ids,CameraCharacteristics.LENS_FACING_BACK+"")){
                Intent intent=new Intent(this,TakeShootingActivity.class);
                intent.putExtra("type",CameraCharacteristics.LENS_FACING_BACK);
                startActivityForResult(intent,1);
            }else {
                Toast.makeText(this, "当前设备不支持前置摄像头", Toast.LENGTH_SHORT).show();
            }
        }
    }
}