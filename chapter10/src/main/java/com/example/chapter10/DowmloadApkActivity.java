package com.example.chapter10;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chapter10.bean.packageInfo;
import com.example.chapter10.util.DateUtil;

import org.w3c.dom.Text;

public class DowmloadApkActivity extends AppCompatActivity {
    private static final String TAG="DowmloadApkActivity";
    private static Spinner sp_apk_url;
    private static TextView tv_apk_result;
    private boolean isFirstSelect=true;//是否首次选择
    private DownloadManager mDowmloadManager;//声明一个下载管理器对象
    private static long mDownloadID=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_apk);
        tv_apk_result=findViewById(R.id.tv_apk_result);

        mDowmloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        initApkSpinner();
    }

    private void initApkSpinner() {


    }

    class ApkUrlSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(isFirstSelect){
                //打开页面是不需要执行下载动作
                isFirstSelect=false;
                return;
            }
            sp_apk_url.setEnabled(false);
            tv_apk_result.setText("正在下载:"+ packageInfo.mNameArray[position]+"的安装包，请到通知栏查看下载进度");

            Uri uri=Uri.parse(packageInfo.mUrlArray[position]);
            DownloadManager.Request dowm=new DownloadManager.Request(uri);

            dowm.setTitle(packageInfo.mNameArray[position]+"下载信息");
            dowm.setDescription(packageInfo.mNameArray+"安装包正在下载");
            dowm.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
            dowm.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            dowm.setVisibleInDownloadsUi(true);

            dowm.setDestinationInExternalFilesDir(DowmloadApkActivity.this, Environment.DIRECTORY_DOWNLOADS,position+".apk");
            mDownloadID=mDowmloadManager.enqueue(dowm);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    public static class DownloadCompleteReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)&&tv_apk_result!=null){
                long downId=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);

                Log.d(TAG, " download complete! id : " + downId + ", mDownloadId=" + mDownloadID);

                tv_apk_result.setVisibility(View.VISIBLE);
                tv_apk_result.setText(DateUtil.getNowDateTime()+" 编号"+downId+"的下载已经完成");
                sp_apk_url.setEnabled(true);
            }
        }
    }

    public static class NotificationClickReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, " NotificationClickReceiver onReceive");
            if(intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)&&tv_apk_result!=null){
                long[] downIds=intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                for(long downid:downIds){
                    Log.d(TAG, " notify click! id : " + downid + ", mDownloadId=" + mDownloadID);
                    if(downid==mDownloadID){
                        tv_apk_result.setText(DateUtil.getNowDateTime() + " 编号"
                                + downid + "的下载进度条被点击了一下");
                    }
                }
            }
        }
    }


    private DownloadCompleteReceiver completeReceiver;
    private NotificationClickReceiver clickReceiver;

    @Override
    protected void onStart() {
        super.onStart();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.P){
            completeReceiver=new DownloadCompleteReceiver();
            registerReceiver(completeReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            clickReceiver=new NotificationClickReceiver();
            registerReceiver(completeReceiver,new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // 注销广播接收器，注销之后就不再接收广播
                unregisterReceiver(completeReceiver);
                unregisterReceiver(clickReceiver);
        }
    }
}
