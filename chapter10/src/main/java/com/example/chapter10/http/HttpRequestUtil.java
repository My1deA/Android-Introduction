package com.example.chapter10.http;

import com.example.chapter10.http.tool.HttpReqData;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpRequestUtil {
    private static final String TAG="HttpRequestUtil";

    //设置http连接的头部信息
    private static void setConnHeader(HttpURLConnection conn, String method, HttpReqData req_data) throws ProtocolException {
        //设置请求方法
        conn.setRequestMethod(method);
        //设置超连接时间
        conn.setConnectTimeout(5000);
        //设置读写超时间
        conn.setReadTimeout(100000);
        //设置数据格式
        conn.setRequestProperty("Accept","*/*");
        // IE使用
//        conn.setRequestProperty("Accept-Language", "zh-CN");
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.2; .NET4.0C)");
        // firefox使用
        // 设置文本语言

        conn.setRequestProperty("Accept_Language","zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
        //设置用户代理
        // 设置用户代理
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0");
        // 设置编码格式
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");

        if(!req_data.content_type.equals("")){
            conn.setRequestProperty("Content-Type",req_data.content_type);

        }


    }


}
