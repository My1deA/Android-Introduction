package com.example.z2.Util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUtil {
//    String Url="http://172.16.86.194:8080/MyWebTest/loginServlet";
    private static String Baseurl;
    String result;
    private final static String TAG="HttpUtil";

    public static void LoginByPost(String username,String password){
        Baseurl="http://172.16.86.194:8080/MyWebTest/loginServlet";
        try{
            URL url=new URL(Baseurl);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //超时信息
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
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
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String row=bufferedReader.readLine();
                Log.d(TAG, "LoginByPost: row"+ row);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
