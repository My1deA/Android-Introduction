package com.example.chapter10;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chapter10.widget.TextProgressCircle;

import java.util.HashMap;
import java.util.Queue;

public class DownloadImageActivity extends AppCompatActivity {
    private Spinner sp_image_url;
    private ImageView iv_image_url;
    private TextProgressCircle tpc_progress;
    private boolean isFristSelect;
    private TextView tv_image_result;
    private String mImagePath;
    private DownloadManager mDowmloadManager;
    private long mDownloadId=0;
    private static HashMap<Integer,String> mStatus=new HashMap<Integer, String>();

    private String[] imageDescArray={
            "洱海公园", "丹凤亭", "宛在堂", "满庭芳", "玉带桥",
            "眺望洱海", "洱海女儿", "海心亭", "洱海岸边", "烟波浩渺"
    };

    private String[] imageUrlArray = {
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/nYJcslMIrGeDrujE5KZF2xBW8rjXMIVetZfrOAlSamM!/b/dPwxB5iaEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/Adcl9XVS.RBED4D8shjceYHOhhR*6mcNyCcq24kJG2k!/b/dPwxB5iYEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/bg*X6nT03YUReoJ97ked266WlWG3IzLjBdwHpKqkhYY!/b/dOg5CpjZEAAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/JOPAKl9BO1wragCEIVzXLlHwj83qVhb8uNuHdmVRwP4!/b/dPwxB5iSEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/7hHOgBEOBshH*7YAUx7RP0JzPuxRBD727mblw9TObhc!/b/dG4WB5i2EgAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/m4Rjx20D9iFL0D5emuYqMMDji*HGQ2w2BWqv0zK*tRk!/b/dGp**5dYEAAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/swfCMVl7Oefv8xgboV3OqkrahEs33KO7XwwH6hh7bnY!/b/dECE*5e9EgAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b256.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/tpRlB0oozaD9PyBtCmf3pQ5QY0keJJxYGX93I7n5NwQ!/b/dAyVmZiVEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b256.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/wMX2*LM6y.mBsFIYu8spAa7xXWUkPD.GHyazd.vMmYA!/b/dGYwoZjREQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
            "http://b255.photo.store.qq.com/psb?/V11ZojBI0Zz6pV/2vl1n0KmKTPCv944MVJgLxKAhMiM*sqajIFQ43c*9DM!/b/dPaoCJhuEQAA&bo=IANYAgAAAAABB1k!&rf=viewer_4",
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);
        iv_image_url=findViewById(R.id.iv_image_url);
        tpc_progress=findViewById(R.id.tpc_progress);
        tv_image_result=findViewById(R.id.tv_image_result);
        mDowmloadManager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        mStatus.put(DownloadManager.STATUS_FAILED,"失败");
        mStatus.put(DownloadManager.STATUS_PAUSED,"暂停");
        mStatus.put(DownloadManager.STATUS_PENDING,"挂起");
        mStatus.put(DownloadManager.STATUS_RUNNING,"运行");
        mStatus.put(DownloadManager.STATUS_FAILED,"失败");
        initImageSpinner();
    }

    private void initImageSpinner() {

    }

    class ImageUrlSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(isFristSelect){
                isFristSelect=false;
                return;
            }
            sp_image_url.setEnabled(false);
            //清空视图
            iv_image_url.setImageURI(null);
            //设置文本进度圈的进度0 -100
            tpc_progress.setProgress(0,100);
            tpc_progress.setVisibility(View.VISIBLE);

            Uri uri=Uri.parse(imageUrlArray[position]);

            DownloadManager.Request dowm=new DownloadManager.Request(uri);

            dowm.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE| DownloadManager.Request.NETWORK_WIFI);
            dowm.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            dowm.setVisibleInDownloadsUi(false);
            dowm.setDestinationInExternalFilesDir(DownloadImageActivity.this,
                    Environment.DIRECTORY_DCIM,position+".jpg");
            mDownloadId=mDowmloadManager.enqueue(dowm);
            mHandler.postDelayed(mRefresh,100);


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private Handler mHandler=new Handler();

    private Runnable mRefresh=new Runnable() {
        @Override
        public void run() {
            boolean isFinished=false;

            DownloadManager.Query down_Query=new DownloadManager.Query();
            down_Query.setFilterById(mDownloadId);
            Cursor cursor=mDowmloadManager.query(down_Query);

            while(cursor.moveToNext()){
                int nameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                int uriIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                int mediaTypeIdx = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE);
                int totalSizeIdx = cursor.getColumnIndex(
                        DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                int nowSizeIdx = cursor.getColumnIndex(
                        DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                int statusIdx = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                // 根据总大小和已下载大小，计算当前的下载进度
                int progress = (int) (100 * cursor.getLong(nowSizeIdx) / cursor.getLong(totalSizeIdx));
                if (cursor.getString(uriIdx) == null) {
                    break;
                }
                // 设置文本进度圈的当前进度
                tpc_progress.setProgress(progress, 100);
                // Android7.0之后提示COLUMN_LOCAL_FILENAME已废弃
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    mImagePath = cursor.getString(nameIdx);
                } else {
                    // 所以7.0之后要先获取文件的Uri，再根据Uri获取文件路径
                    String fileUri = cursor.getString(uriIdx);
                    mImagePath = Uri.parse(fileUri).getPath();
                }
                if (progress == 100) { // 下载完毕
                    isFinished = true;
                }
                // 获得实际的下载状态
                int status = isFinished ? DownloadManager.STATUS_SUCCESSFUL : cursor.getInt(statusIdx);
                String desc = "";
                // 以下拼接图片下载任务的下载详情
                desc = String.format("%s文件路径：%s\n", desc, mImagePath);
                desc = String.format("%s媒体类型：%s\n", desc, cursor.getString(mediaTypeIdx));
                desc = String.format("%s文件总大小：%d\n", desc, cursor.getLong(totalSizeIdx));
                desc = String.format("%s已下载大小：%d\n", desc, cursor.getLong(nowSizeIdx));
                desc = String.format("%s下载进度：%d%%\n", desc, progress);
                desc = String.format("%s下载状态：%s\n", desc, mStatus.get(status));
                tv_image_result.setText(desc);
            }
            cursor.close(); // 关闭数据库游标
            if (!isFinished) { // 未完成，则继续刷新
                // 延迟100毫秒后再次启动下载进度的刷新任务
                mHandler.postDelayed(this, 100);
            } else { // 已完成，则显示图片
                sp_image_url.setEnabled(true);
                // 隐藏文本进度圈
                tpc_progress.setVisibility(View.INVISIBLE);
                // 把指定路径的图片显示在图像视图上面
                iv_image_url.setImageURI(Uri.parse(mImagePath));
            }

            }
    };


}
