package com.example.chapter10.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.chapter10.MainApplication;
import com.example.chapter10.http.HttpRequestUtil;
import com.example.chapter10.http.tool.HttpReqData;
import com.example.chapter10.http.tool.HttpRespData;
import com.example.chapter10.util.BitmapUtil;
import com.example.chapter10.util.DateUtil;

public class GetImageCodeTask extends AsyncTask<Void,Void,String> {
    private final static String TAG="GetImageCodeTask";

    // 请求图片验证码的服务地址
    private String mImageCodeUrl = "http://222.77.181.14/ValidateCode.aspx?r=";
    //private String mImageCodeUrl = "http://220.160.54.47:82/JSPORTLET/radomImage?x=";

    public GetImageCodeTask(){
        super();
    }

    @Override
    protected String doInBackground(Void... params) {
        String url=mImageCodeUrl+ DateUtil.getNowDateTime();
        Log.d(TAG, "image url=" + url);

        HttpReqData req_data=new HttpReqData(url);
        HttpRespData resp_data= HttpRequestUtil.getImage(req_data);

        String path= BitmapUtil.getCachePath(MainApplication.getInstance())+DateUtil.getNowDateTime()+".jpg";

        BitmapUtil.saveBitmap(path,resp_data.bitmap,"jpg",100);
        Log.d(TAG, "image path=" + path);
        return path; // 返回验证码图片的本地路径
    }


    @Override
    protected void onPostExecute(String s) {
        mListener.onGetCode(s);
    }

    public interface OnImageCodeListener{
        void onGetCode(String path);
    }

    private OnImageCodeListener mListener;

    public void setOnImageCodeListener(OnImageCodeListener listener){
        mListener=listener;
    }

}
