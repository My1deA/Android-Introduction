package com.example.space.socket;

import android.app.AppComponentFactory;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static android.content.ContentValues.TAG;

public class Register extends AppCompatActivity implements View.OnClickListener
{
    private EditText userAccount;
    private EditText newPassword;
    private EditText confirmPassword;
    private TextView empty;
    private Button Enroll;
    private String sendAccountMessage;
    private String sendNewPassWordMessage;
    private String sendConfirmPassWordMessage;
    private Socket socket;
    private boolean check=false;
    private String regex="^[A-Za-z0-9]+$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userAccount = (EditText)findViewById(R.id.userAccount);
        newPassword = (EditText)findViewById(R.id.newPassWord);
        confirmPassword=(EditText)findViewById(R.id.confirmPassWord);
        empty=(TextView)findViewById(R.id.empty);
        Enroll=(Button)findViewById(R.id.Enroll);

        Enroll.setOnClickListener(this);
        empty.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {

        if(v.getId()==R.id.Enroll)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
            builder.setTitle("消息提示框");     //设置对话框标题
            builder.setMessage("确认注册?");
            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    sendAccountMessage=userAccount.getText().toString();
                    sendNewPassWordMessage=newPassword.getText().toString();
                    sendConfirmPassWordMessage=confirmPassword.getText().toString();
                    check=Check(sendAccountMessage,sendNewPassWordMessage,sendConfirmPassWordMessage);
                    if(check==true)
                    {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    Looper.prepare();
                                    socket = new Socket("172.16.86.49", 12575);
                                    //向服务器发送数据
                                    PrintWriter sendAccount = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")));
                                    sendAccount.println(sendAccountMessage);
                                    sendAccount.flush();
                                    PrintWriter sendPassWord = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")));
                                    sendPassWord.println(sendNewPassWordMessage);
                                    sendPassWord.flush();
                                    //接受服务端数据
                                    BufferedReader recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                    String recvMsg = recv.readLine();
                                    if (recvMsg != null) {
//                          mRecvText.setText(recvMsg);
                                        Log.e(TAG, "返回Enroll的内容是:" + recvMsg);
                                    } else {
//                          mRecvText.setText("Cannot receive data correctly.");
                                    }
                                    if(recvMsg.equals("true"))
                                    {
                                        AlertDialog.Builder builder1=new AlertDialog.Builder(Register.this);
                                        builder1.setTitle("消息提示框");     //设置对话框标题
                                        builder1.setMessage("注册成功");
                                        builder1.setNegativeButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Register.this.finish();
                                            }
                                        });
                                        builder1.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
                                        AlertDialog dialog1 = builder1.create();  //创建对话框
                                        dialog1.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
                                        dialog1.show();

                                    }
                                    else
                                    {
                                        Toast.makeText(Register.this, "账号已存在", Toast.LENGTH_SHORT).show();
                                    }
                                    Looper.loop();


                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }).start();
                    }

                }
            });
            builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
            AlertDialog dialog = builder.create();  //创建对话框
            dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
            dialog.show();



        }
        else if(v.getId()==R.id.empty)
        {
            newPassword.setText("");
            confirmPassword.setText("");
        }
    }
    private boolean Check(String sendAccountMessage,String sendNewPassWordMessage,String sendConfirmPassWordMessage)
    {
        if(sendAccountMessage.equals(""))
        {
            Toast.makeText(this, "名称为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(sendNewPassWordMessage.equals(""))
        {
            Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(sendNewPassWordMessage.length()>15)
        {
            Toast.makeText(this, "名称过长（名称不可超过15个字符）", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(sendConfirmPassWordMessage.equals(""))
        {
            Toast.makeText(this, "请确认你的密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!sendNewPassWordMessage.equals(sendConfirmPassWordMessage))
        {
            Toast.makeText(this, "密码与确认密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            newPassword.setText("");
            confirmPassword.setText("");
            return false;
        }
        else if(sendNewPassWordMessage.length()<6||sendNewPassWordMessage.length()>12)
        {
            Toast.makeText(this, "密码过长或过短（密码不得少于6位或多于12位）", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!sendNewPassWordMessage.matches(regex))
        {
            Toast.makeText(this, "请输入正确的账号和密码（账号必须由数字组成密码必须由字母和数字组成）", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void showdialog(){


    }

}
