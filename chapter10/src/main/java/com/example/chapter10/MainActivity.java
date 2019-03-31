package com.example.chapter10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_message).setOnClickListener(this);
        findViewById(R.id.btn_progress_dialog).setOnClickListener(this);
        findViewById(R.id.btn_progress_text).setOnClickListener(this);
        findViewById(R.id.btn_progress_circle).setOnClickListener(this);
        findViewById(R.id.btn_async_task).setOnClickListener(this);
        findViewById(R.id.btn_intent_service).setOnClickListener(this);
        findViewById(R.id.btn_connect).setOnClickListener(this);
        findViewById(R.id.btn_json_parse).setOnClickListener(this);
        findViewById(R.id.btn_json_convert).setOnClickListener(this);
        findViewById(R.id.btn_http_request).setOnClickListener(this);
        findViewById(R.id.btn_http_image).setOnClickListener(this);
        findViewById(R.id.btn_download_apk).setOnClickListener(this);
        findViewById(R.id.btn_download_image).setOnClickListener(this);
        findViewById(R.id.btn_file_save).setOnClickListener(this);
        findViewById(R.id.btn_file_select).setOnClickListener(this);
        findViewById(R.id.btn_upload_http).setOnClickListener(this);
        findViewById(R.id.btn_net_address).setOnClickListener(this);
        findViewById(R.id.btn_socket).setOnClickListener(this);
        findViewById(R.id.btn_apk_info).setOnClickListener(this);
        findViewById(R.id.btn_app_store).setOnClickListener(this);
        findViewById(R.id.btn_fold_list).setOnClickListener(this);
        findViewById(R.id.btn_qqchat).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_progress_dialog){
            Intent intent = new Intent(this, ProgressDialogActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_intent_service){
            Intent intent=new Intent(this,IntentServiceActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_json_parse){
            Intent intent=new Intent(this,JsonParseActivity.class);
            startActivity(intent);
        }
    }
}
