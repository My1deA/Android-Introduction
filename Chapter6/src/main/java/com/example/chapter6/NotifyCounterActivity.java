package com.example.chapter6;

import android.annotation.TargetApi;
import android.app.Application;
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

public class NotifyCounterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_title;
    private EditText et_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_counter);
        et_title=findViewById(R.id.et_title);
        et_message=findViewById(R.id.et_message);
        findViewById(R.id.btn_send_counter).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_counter) {
            String title = et_title.getText().toString();
            String message = et_message.getText().toString();
            sendCounterNotify(title, message);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendCounterNotify(String title, String message) {
        Intent cancelIntent=new Intent(this,MainActivity.class);
        PendingIntent deleteIntent=PendingIntent.getActivity(this,R.string.app_name,
                cancelIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder=new Notification.Builder(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder=new Notification.Builder(this,getString(R.string.app_name));
        }
        builder.setDeleteIntent(deleteIntent);
        builder.setAutoCancel(true);
        builder.setUsesChronometer(true);
        builder.setProgress(100,60,false);
        builder.setNumber(99);
        builder.setSmallIcon(R.drawable.ic_app);
        builder.setTicker("提示消息来啦");
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_app));
        builder.setContentTitle(title);
        builder.setContentTitle(message);

        Notification notification=builder.build();
        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(R.string.app_name,notification);
    }
}
