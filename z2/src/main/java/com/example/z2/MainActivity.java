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
    private EditText et_username;
    private EditText et_password;
    private TextView tv_result;
    private String port="8888";
    private String IP="172.16.86.194";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username=findViewById(R.id.et_username);
        et_password=findViewById(R.id.et_password);
        tv_result=findViewById(R.id.tv_result);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_login){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Looper.prepare();
                        String username=et_username.getText().toString().trim();
                        String password=et_password.getText().toString().trim();
                        Log.d(TAG, "btn_login username:"+username+"  password:"+password);

                        //输出信息 登录密码信息
                        Socket socket=new Socket();
                        socket.connect(new InetSocketAddress(IP,Integer.parseInt(port)),3000);

                        OutputStream outputStream=socket.getOutputStream();
                        PrintWriter printWriter=new PrintWriter(outputStream,true);
                        printWriter.println("0#"+username+"#"+password);

                        //得到线程信息 看是否登录成功
                        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String row=bufferedReader.readLine();
                        String [] info=row.split("#");
                        if(info[1].equalsIgnoreCase("SUCC")){
//                            Toast.makeText(MainActivity.this,"Login Succ",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Login Succ");
                            tv_result.setText("Login Succ");
                        }else{
//                            Toast.makeText(MainActivity.this,"Login Fail",Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Login Fail");
                            tv_result.setText("Login Fail");
                        }
                        Looper.loop();

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }
}
