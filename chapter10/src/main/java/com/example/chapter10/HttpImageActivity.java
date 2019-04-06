package com.example.chapter10;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.chapter10.task.GetImageCodeTask;

public class HttpImageActivity extends AppCompatActivity implements View.OnClickListener, GetImageCodeTask.OnImageCodeListener {

    private ImageView iv_iamge_Code;
    private boolean isRunning =false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_image);
        iv_iamge_Code=findViewById(R.id.iv_image_code);
        iv_iamge_Code.setOnClickListener(this);
        getImageCode();
    }

    private void getImageCode() {
        if(!isRunning){
            isRunning=true;
            GetImageCodeTask getImageCodeTask=new GetImageCodeTask();
            getImageCodeTask.setOnImageCodeListener(this);
            getImageCodeTask.execute();
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_image_code) {
            getImageCode(); // 获取图片验证码
        }
    }

    @Override
    public void onGetCode(String path) {
        iv_iamge_Code.setImageURI(Uri.parse(path));
        isRunning=false;
    }
}
