package com.example.chapter9;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter9.util.DateUtil;
import com.example.chapter9.util.SwtichUtil;


public class LocationActivity extends AppCompatActivity {
    private final static String TAG="LocationActivity";
    private TextView tv_location;
    private String mLocation="";
    private LocationManager mLocationMgr;
    private Criteria mCriteria=new Criteria();
    private Handler mHandler=new Handler();
    private boolean isLocationEnable=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        tv_location=findViewById(R.id.tv_location);
        SwtichUtil.checkGpsIsOpen(this,"需要打开定位功能才能查看定位结果");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.removeCallbacks(mRefresh);
        initLocation();
        mHandler.postDelayed(mRefresh,100);
    }

    private void initLocation(){
        mLocationMgr= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        mCriteria.setAltitudeRequired(true);
        mCriteria.setBearingRequired(true);
        mCriteria.setCostAllowed(true);
        mCriteria.setPowerRequirement(Criteria.ACCURACY_LOW);

        String bestProvider=mLocationMgr.getBestProvider(mCriteria,true);
        if(mLocationMgr.isProviderEnabled(bestProvider)){
            tv_location.setText("正在获取"+bestProvider+"定位对象");
            mLocation=String.format("定位类型=%s"+bestProvider);
            isLocationEnable=true;
        }else{
            tv_location.setText("\n" + bestProvider + "定位不可用");
            isLocationEnable = false;
        }
    }

    //设置定位结果文本
    private  void setLocationText(Location location){
        if (location != null) {
            String desc = String.format("%s\n定位对象信息如下： " +
                            "\n\t其中时间：%s" + "\n\t其中经度：%f，纬度：%f" +
                            "\n\t其中高度：%d米，精度：%d米",
                    mLocation, DateUtil.getNowDateTimeFormat(),
                    location.getLongitude(), location.getLatitude(),
                    Math.round(location.getAltitude()), Math.round(location.getAccuracy()));
            Log.d(TAG, desc);
            tv_location.setText(desc);
        } else {
            tv_location.setText(mLocation + "\n暂未获取到定位对象");
        }
    }

    //开始定位
    private void beginLocation(String method){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"请授予权限并开启定位功能",Toast.LENGTH_SHORT).show();
            return;
        }
        mLocationMgr.requestLocationUpdates(method,300,0,mLocationListener);
        Location location=mLocationMgr.getLastKnownLocation(method);
        setLocationText(location);
    }

    private LocationListener mLocationListener=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            setLocationText(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private Runnable mRefresh=new Runnable() {
        @Override
        public void run() {
            if(!isLocationEnable){
                initLocation();
                mHandler.postDelayed(this,1000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mLocationMgr!=null){
            mLocationMgr.removeUpdates(mLocationListener);
        }
        super.onDestroy();
    }
}





















