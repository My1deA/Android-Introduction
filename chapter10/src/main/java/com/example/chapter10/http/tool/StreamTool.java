package com.example.chapter10.http.tool;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;

public class StreamTool {

    private static final String TAG="StreamTool";

    public static byte[] readInputStream(InputStream inputStream)throws Exception{
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while ((len=inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        byte[] data=outputStream.toByteArray();
        outputStream.close();
        inputStream.close();;
        return data;
    }

    public static String getUnzipStream(InputStream inputStream,String Content_encoding,String charset){
        String resp_content="";
        GZIPInputStream gzipInputStream=null;
        if(Content_encoding!=null&&!Content_encoding.equals("")){
            if(Content_encoding.contains("gizp")){
                try{
                    Log.d(TAG,"Content_encoding:"+Content_encoding);
                    gzipInputStream=new GZIPInputStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try{
            if(gzipInputStream==null){
                resp_content=new String(readInputStream(inputStream),charset);
            }else{
                resp_content=new String(readInputStream(gzipInputStream),charset);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp_content;
    }
}
