package com.example.z2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG="RegisterActivity";
    private EditText et_username;
    private EditText et_password;
    private TextView tv_result;
    private String port="8888";
    private String IP="172.16.86.194";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        tv_result=findViewById(R.id.tv_result);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_register){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Looper.prepare();
                        String username=et_username.getText().toString().trim();
                        String password=et_password.getText().toString().trim();

                        //构建一个socket
                        Socket socket=new Socket();
                        socket.connect(new InetSocketAddress(IP,Integer.parseInt(port)),3000);

                        //输出注册信息
                        OutputStream outputStream=socket.getOutputStream();
                        PrintWriter printWriter=new PrintWriter(outputStream,true);
                        printWriter.println("1#"+username+"#"+password);

                        //阻塞 获得服务器数据
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String row=bufferedReader.readLine();
                        String [] info=row.split("#");
                        if(info[1].equalsIgnoreCase("SUCC")){
                            Log.d(TAG, "Register Succ");
                            tv_result.setText("Register Succ");
                        }else {
                            Log.d(TAG, "Register Fail");
                            tv_result.setText("Register Fail 用户名已经存在");
                        }
                        Looper.loop();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
}
