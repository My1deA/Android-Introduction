package com.example.chapter6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class NotifySimpleActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_simple);
        et_title=findViewById(R.id.et_title);
        et_message=findViewById(R.id.et_message);
        findViewById(R.id.btn_send_simple).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_simple) {
            String title = et_title.getText().toString();
            String message = et_message.getText().toString();
            sendSimpleNotify(title, message);
        }
    }

    private void sendSimpleNotify(String title,String message){
        Intent clickIntent=new Intent(this,MainActivity.class);
        PendingIntent contentIntent=PendingIntent.getActivity(this,R.string.app_name,clickIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder=new Notification.Builder(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder=new Notification.Builder(this,getString(R.string.app_name));
        }
        builder.setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_app)
                .setTicker("提示消息来啦")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_app))
                .setContentTitle(title)
                .setContentText(message);

        Notification notification=builder.build();
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(R.string.app_name,notification);
    }
}
