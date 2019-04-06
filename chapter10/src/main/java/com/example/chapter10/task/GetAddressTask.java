package com.example.chapter10.task;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.chapter10.http.HttpRequestUtil;
import com.example.chapter10.http.tool.HttpReqData;
import com.example.chapter10.http.tool.HttpRespData;

import org.json.JSONObject;

public class GetAddressTask extends AsyncTask<Location,Void,String > {
    private final static String TAG = "GetAddressTask";
    // 谷歌地图从2019年开始必须传入密钥才能根据经纬度获取地址，所以把查询接口改成了国内的天地图
    //private String mAddressUrl = "http://maps.google.cn/maps/api/geocode/json?latlng={0},{1}&sensor=true&language=zh-CN";
    private String mAddressUrl = "http://api.tianditu.gov.cn/geocoder?postStr={'lon':%f,'lat':%f,'ver':1}&type=geocode&tk=145897399844a50e3de2309513c8df4b";

    public GetAddressTask(){
        super();
    }


    //线程后台处理
    @Override
    protected String doInBackground(Location... locations) {
        Location location=locations[0];
        //把经度和纬度带入URl 地址
        String url=String.format(mAddressUrl,location.getLongitude(),location.getLatitude());
        //创建一个http对象
        HttpReqData req_data=new HttpReqData(url);
        //发送http请求 得到http返回对象
        HttpRespData resp_data= HttpRequestUtil.getData(req_data);
        Log.d(TAG,"return json:"+resp_data.content);

        String address="未知的";

        if(resp_data.err_msg.length()<=0){
            try{
                JSONObject obj=new JSONObject(resp_data.content);
                JSONObject result=obj.getJSONObject("result");
                address=result.getString("formatted_address");
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        Log.d(TAG, "address :"+address);
        return address;
    }

    @Override
    protected void onPostExecute(String s) {
        mListener.onFindAddress(s);
    }

    public interface OnAddressListener{
        void onFindAddress(String address);
    }

    private OnAddressListener mListener;

    public void setOnAddressListener(OnAddressListener listener){
        mListener=listener;
    }
}
