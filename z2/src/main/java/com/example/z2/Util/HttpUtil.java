package com.example.z2.Util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtil {
//    String Url="http://172.16.86.194:8080/MyWebTest/loginServlet";
    private final static String TAG="HttpUtil";

    public static String LoginByPost(String username,String password){
        String Baseurl="http://172.16.86.194:8080/MyWebTest/loginServlet";
        String msg=null;
        try{
            URL url=new URL(Baseurl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //超时信息
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            //设置允许输入
            connection.setDoInput(true);
            //设置允许输出
            connection.setDoOutput(true);
            //设置缓存
            connection.setUseCaches(false);

            //请求发送的数据
            String data="username="+ URLEncoder.encode(username,"UTF-8")+
                    "&password="+URLEncoder.encode(password,"UTF-8");

            //获得输出流
            OutputStream outputStream=connection.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.flush();;
            outputStream.close();
            connection.connect();

            if(connection.getResponseCode()==200){
                //相应输入流对象
                InputStream is=connection.getInputStream();
                //创建字节
                ByteArrayOutputStream message=new ByteArrayOutputStream();
                //定义读取长度
                int len=0;
                byte buffer[]=new byte[1024];

                while((len=is.read(buffer))!=-1){
                    message.write(buffer,0,len);
                }
                is.close();
                message.close();

                msg=new String(message.toByteArray());
                return msg;
            }

        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
