package com.example.z2;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG="MainActivity";
//    private EditText et_username;
//    private EditText et_password;
//    private TextView tv_result;
//    private String port="8888";
//    private String IP="172.16.86.194";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        et_username=findViewById(R.id.et_username);
//        et_password=findViewById(R.id.et_password);
//        tv_result=findViewById(R.id.tv_result);
        findViewById(R.id.btn_in).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_in){
//            Intent intent=new Intent(this,AppMainActivity.class);
//            startActivity(intent);
//            Intent intent=new Intent(this,LoginActivity.class);
//            startActivity(intent);
            Intent intent=new Intent(this,LoginServletActivity.class);
            startActivity(intent);
        }
    }
}
