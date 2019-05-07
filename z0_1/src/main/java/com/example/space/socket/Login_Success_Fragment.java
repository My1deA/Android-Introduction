package com.example.space.socket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Success_Fragment extends Fragment implements View.OnClickListener
{
    private MainActivity mActivity;
    private TextView setUserName;
    private Button button;
    private Button exit;
    private Login_Fragment login_fragment;

    private String userName="";
    boolean isExit=false;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_success, container, false);
        mActivity=(MainActivity)getActivity();
        setUserName=(TextView)view.findViewById(R.id.userName);
        setUserName.setText(userName);
        button=(Button)view.findViewById(R.id.button);
        exit=(Button)view.findViewById(R.id.exit);
        button.setOnClickListener(this);
        exit.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button)
        {
            Intent i=new Intent(getActivity(),OKhttp.class);
            startActivity(i);
        }
        else if(v.getId()==R.id.exit)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("消息提示框");     //设置对话框标题
            builder.setMessage("确认退出？");
            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mActivity.switchFragment(login_fragment);
                    login_fragment.getUserPassWord().setText("");
                    isExit=true;
                    login_fragment.setLogin(false);
                }
            });
            builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
            AlertDialog dialog = builder.create();  //创建对话框
            dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
            dialog.show();

        }
    }

    public boolean getExit()
    {
        return isExit;
    }
    public void setExit(boolean isExit)
    {
        this.isExit=isExit;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setLogin_fragment(Login_Fragment login_fragment)
    {
        this.login_fragment=login_fragment;
    }
}
