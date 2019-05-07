package com.example.space.socket;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.net.Socket;

import static android.content.ContentValues.TAG;

public class Login_Fragment extends Fragment implements View.OnClickListener
{
    public MainActivity mActivity = (MainActivity) getActivity();;
    private EditText userAccount;


    private EditText userPassWord;
    private Button Login;
    private TextView Enroll;
    private String sendAccountMessage;
    private String sendPassWordMessage;
    private Socket socket;
    private Login_Success_Fragment login_success_fragment;
    private boolean isLogin=false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View login = inflater.inflate(R.layout.login, container, false);
        mActivity = (MainActivity) getActivity();
        userAccount = (EditText) login.findViewById(R.id.Account);
        userPassWord = (EditText) login.findViewById(R.id.PassWord);
        Login = (Button) login.findViewById(R.id.Login);
        Enroll=(TextView)login.findViewById(R.id.register);
        Enroll.setOnClickListener(this);
        Login.setOnClickListener(this);
        return login;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.Login)
        {
            sendAccountMessage = userAccount.getText().toString();
            sendPassWordMessage = userPassWord.getText().toString();
            if(sendAccountMessage.equals("")||sendPassWordMessage.equals(""))
            {
                Toast.makeText(mActivity, "请输入账号或密码", Toast.LENGTH_SHORT).show();
            }
            else
            {

                mActivity.switchFragment(login_success_fragment);
                isLogin=true;
                login_success_fragment.setUserName(sendAccountMessage);
                login_success_fragment.setExit(false);
//                new Thread(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        try {
//                            Looper.prepare();
//                            socket = new Socket("172.16.86.49", 12574);
//                            //向服务器发送数据
//                            PrintWriter sendAccount = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")));
//                            sendAccount.println(sendAccountMessage);
//                            sendAccount.flush();
//                            PrintWriter sendPassWord = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8")));
//                            sendPassWord.println(sendPassWordMessage);
//                            sendPassWord.flush();
//                            //接受服务端数据
//                            BufferedReader recv = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                            String recvMsg = recv.readLine();
//                            if (recvMsg != null)
//                            {
////                          mRecvText.setText(recvMsg);
//                                Log.e(TAG, "返回Login的内容是:" + recvMsg);
//                            }
//                            else
//                            {
////                          mRecvText.setText("Cannot receive data correctly.");
//                            }
//
//                            if(recvMsg.equals("true"))
//                            {
//                                mActivity.switchFragment(login_success_fragment);
//                                isLogin=true;
//                                login_success_fragment.setUserName(sendAccountMessage);
//                                login_success_fragment.setExit(false);
//                            }
//                            else
//                            {
//                                Toast.makeText(mActivity, "账号不存在或密码错误", Toast.LENGTH_SHORT).show();
//                            }
//                            sendAccount.close();
//                            sendPassWord.close();
//                            recv.close();
//                            socket.close();
//                            Looper.loop();
//                        }
//                        catch (Exception ex)
//                        {
//                            ex.printStackTrace();
//                        }
//                    }
//
//                }).start();

            }

        }
        else if(view.getId()==R.id.register)
        {
            Intent i=new Intent(getActivity(),Register.class);
            startActivity(i);
        }

    }
    public boolean getLogin()
    {
        return isLogin;
    }
    public void setLogin(boolean isLogin)
    {
        this.isLogin=isLogin;
    }
    public EditText getUserPassWord() {
        return userPassWord;
    }

    public void setLogin_success_fragment(Login_Success_Fragment login_success_fragment)
    {
        this.login_success_fragment=login_success_fragment;
    }




}



