package com.example.chapter10.http;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ListView;

import com.example.chapter10.http.tool.HttpReqData;
import com.example.chapter10.http.tool.HttpRespData;
import com.example.chapter10.http.tool.StreamTool;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

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
        if(!req_data.x_requested_with.equals("")){
            conn.setRequestProperty("X-Requested-With",req_data.x_requested_with);
        }
        if(!req_data.referer.equals("")){
            conn.setRequestProperty("Referer",req_data.referer);
        }
        if(!req_data.cookie.equals("")){
            conn.setRequestProperty("Cookie",req_data.cookie);
            Log.d(TAG, "setConnHeader cookie=" + req_data.cookie);
        }
    }

    private static String getRespCookie(HttpURLConnection connection,HttpReqData req_Data){
        String cookie="";
        Map<String, List<String>> headerFiields=connection.getHeaderFields();
        if(headerFiields!=null){
            List<String> cookies=headerFiields.get("Set-Cookie");
            if(cookies!=null){
                for(String cookie_item:cookies){
                    cookie=cookie+cookie_item+";";
                }
            }else{
                cookie=req_Data.cookie;
            }
        }else{
            cookie=req_Data.cookie;
        }
        Log.d(TAG,"cookie:"+cookie);
        return cookie;
    }

    //get文本数据
    public static HttpReqData getData(HttpReqData req_Data){
        HttpRespData resp_data=new HttpRespData();
        try{
            URL url=new URL(req_Data.url);
            //创建指定网络地址的HTTP连接
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            setConnHeader(connection,"GET",req_Data);
            connection.connect();
            //对输入流中数据进行解压
            resp_data.content= StreamTool.getUnzipStream(connection.getInputStream(),
                    connection.getHeaderField("Content-Encoding"),req_Data.charset);
            resp_data.cookie=connection.getHeaderField("Set-Cookie");
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg=e.getMessage();
        }
        return req_Data;
    }

    //get图片数据
    public static HttpRespData getImage(HttpReqData req_Data){
        HttpRespData resp_data=new HttpRespData();
        try{
            URL url=new URL(req_Data.url);
            //创建指定网络地址的HTTP连接
            HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            setConnHeader(conn,"GET",req_Data);
            conn.connect();
            //从Http连接中获得输入流
            InputStream is=conn.getInputStream();
            resp_data.bitmap= BitmapFactory.decodeStream(is);
            resp_data.cookie=conn.getHeaderField("Set-cookie");

            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
            resp_data.err_msg=e.getMessage();
        }
        return resp_data;
    }

    //post的内容放在url中
    public static HttpRespData postUrl(HttpReqData req_data){
        HttpRespData resp_data=new HttpRespData();
        String s_url=req_data.url;
        if(req_data.params!=null&&!req_data.params.toString().isEmpty()){
            s_url+="?"+req_data.params.toString();
        }
        Log.d(TAG, "s_url=" + s_url);
        try{
            URL url=new URL(s_url);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            setConnHeader(conn,"POST",req_data);
            conn.connect();
            conn.setDoOutput(true);
            resp_data.content=StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"),req_data.charset);
            resp_data.cookie=conn.getHeaderField("Set-Cookie");
            conn.disconnect();

        }catch (Exception e){
            e.printStackTrace();
            resp_data.err_msg=e.getMessage();
        }
        return resp_data;
    }

    //post的内容放在输出流中
    public static HttpRespData postData(HttpReqData req_data){
        req_data.content_type = "application/x-www-form-urlencoded";
        HttpRespData resp_data=new HttpRespData();
        String s_url=req_data.url;
        Log.d(TAG, "s_url=" + s_url + ", params=" + req_data.params.toString());
        try{
            URL url=new URL(s_url);
            //创建指定网络地址的http连接
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            setConnHeader(conn,"POST",req_data);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            PrintStream out=new PrintStream(conn.getOutputStream());
            out.print(req_data.params.toString());
            out.flush();

            //对输入流中数据进行解压
            resp_data.content=StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"),req_data.charset);
            resp_data.cookie=getRespCookie(conn,req_data);
            conn.disconnect();
        }catch (Exception e){
            e.printStackTrace();
            resp_data.err_msg=e.getMessage();
        }
        return resp_data;
    }

    //post 的内容分段传输
    public static HttpRespData postMultiData(HttpReqData req_data,Map<String,String> map){
        HttpRespData resp_data = new HttpRespData();
        String s_url = req_data.url;
        Log.d(TAG, "s_url=" + s_url);
        String end = "\r\n";
        String hyphens = "--";
        try {
            URL url = new URL(s_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            setConnHeader(conn, "POST", req_data);
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + req_data.boundary);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            StringBuilder buffer = new StringBuilder();
            Log.d(TAG, "map.size()=" + map.size());
            for (String str : map.keySet()) {
                buffer.append(hyphens + req_data.boundary + end);
                buffer.append("Content-Disposition: form-data; name=\"");
                buffer.append(str);
                buffer.append("\"" + end + end);
                buffer.append(map.get(str));
                buffer.append(end);
                Log.d(TAG, "key=" + str + ", value=" + map.get(str));
            }
            if (map.size() > 0) {
                buffer.append(hyphens + req_data.boundary + end);
                byte[] param_data = buffer.toString().getBytes(req_data.charset);
                OutputStream out = conn.getOutputStream();
                out.write(param_data);
                out.flush();
            }

            conn.connect(); // 开始连接
            // 对输入流中的数据进行解压，得到原始的应答字符串
            resp_data.content = StreamTool.getUnzipStream(conn.getInputStream(),
                    conn.getHeaderField("Content-Encoding"), req_data.charset);
            resp_data.cookie = conn.getHeaderField("Set-Cookie");
            conn.disconnect(); // 断开连接
        } catch (Exception e) {
            e.printStackTrace();
            resp_data.err_msg = e.getMessage();
        }
        return resp_data;
    }


}














