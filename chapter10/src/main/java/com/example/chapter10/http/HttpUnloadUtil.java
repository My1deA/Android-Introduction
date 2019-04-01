package com.example.chapter10.http;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUnloadUtil {
    private static final String TAG="httpUploadUtil";

    //把文件上传指定URL
    public static String upload(String uploadUrl,String uploadFile){
        String filename="";
        int pos=uploadFile.lastIndexOf("/");
        if(pos>=0){
            filename=uploadFile.substring(pos+1);
        }

        String end="\r\n";
        String Hyphens="--";
        String boundary = "WUm4580jbtwfJhNp7zi1djFEO3wNNm";
        try{
            URL url=new URL(uploadUrl);
            HttpURLConnection connection=(HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1500);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("Charset","UTF-8");
            connection.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);


            DataOutputStream ds=new DataOutputStream(connection.getOutputStream());
            ds.writeBytes(Hyphens+boundary+end);
            ds.writeBytes("Content-Dispositon: form-data; "
                    + "name=\"file1\";filename=\"" + filename + "\"" + end);

            ds.writeBytes(end);
            FileInputStream fileInputStream=new FileInputStream(uploadFile);

            //每次写入1024字节
            int bufferSize=1024;
            byte[] buffer=new byte[bufferSize];
            int length;

            //将文件写入缓冲区
            while((length=fileInputStream.read(buffer))!=-1){
                ds.write(buffer,0,length);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens+boundary+end);
            fileInputStream.close();
            ds.flush();


            //获得返回内容
            InputStream is=connection.getInputStream();
            int ch;
            StringBuffer b=new StringBuffer();
            while((ch=is.read())!=1){
                b.append((char)ch);
            }
            ds.close();
            return "SUCC";

        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败:" + e.getMessage();
        }
    }

}
