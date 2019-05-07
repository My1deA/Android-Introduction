package com.example.z2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OKhttp extends AppCompatActivity implements View.OnClickListener
{
    public static final MediaType JSON
            =MediaType.parse("application/json; charset=utf-8");

    private static final int GET=1;
    private static final int POST=2;
    private static final String TAG=OKhttp.class.getSimpleName();
    private Button Upload;
    private TextView textShow;
    private ProgressBar progressBar;

    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(50, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET:
                    textShow.setText((String) msg.obj);
                    break;
                case POST:
                    textShow.setText((String) msg.obj);
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload);
        Upload=(Button)findViewById(R.id.Upload);
        textShow=(TextView)findViewById(R.id.textShow);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        Upload.setOnClickListener(this);
    }

    /**
     *
     */
    String path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileChooseUtil fileChooseUtil=new FileChooseUtil();
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())){//使用第三方应用打开9-
                path = uri.getPath();
                textShow.setText(path);
                Toast.makeText(this,path+"11111",Toast.LENGTH_SHORT).show();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = fileChooseUtil.getPath(this, uri);
                textShow.setText(path);
                Toast.makeText(this,path,Toast.LENGTH_SHORT).show();
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
                textShow.setText(path);
                Toast.makeText(OKhttp.this, path+"222222", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }
    /**
     *
     */
    public void multiFileUpload()
    {
        String mBaseUrl="http://172.16.86.194:8080/MyWebTest/uploadServlet";
        String str=textShow.getText().toString();
        String name=str.substring(str.lastIndexOf("/")+1);
        File file=new File(str);
        //Log.e(TAG,str);
        //File file = new File(Environment.getExternalStorageDirectory(), "snow.jpg");
        //File file2 = new File(Environment.getExternalStorageDirectory(), "钢琴.txt");
        //Log.e(TAG,Environment.getExternalStorageDirectory()+"/钢琴.txt");
        if (!file.exists()||!file.exists())
        {
            return;
        }
        Map<String, String> params = new HashMap<>();
        String url = mBaseUrl;

        Log.e(TAG,Calendar.getInstance()+name);

        OkHttpUtils.post()//
                .addFile("mFile", System.currentTimeMillis()+name, file)//
//                .addFile("mFile", "2.txt", file2)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }

    private void checkPermission() {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(OKhttp.this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(OKhttp.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
            permission=ActivityCompat.checkSelfPermission(OKhttp.this,"android.permission.READ_EXTERNAL_STORAGE");
            if(permission!=PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(OKhttp.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void downloadFile()
    {
        checkPermission();
        String url = "http://vfx.mtime.cn/Video/2019/04/10/mp4/190410081607863991.mp4";//"http://172.16.86.49:8001/upload/1.jpg";//
        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-test1.mp4")//
                {
                    @Override
                    public void onBefore(Request request, int id)
                    {
                    }
                    @Override
                    public void inProgress(float progress, long total, int id)
                    {
                        progressBar.setProgress((int) (100 * progress));
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }
                    @Override
                    public void onResponse(File file, int id)
                    {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }

    public class MyStringCallback extends StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id)
        {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            textShow.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            textShow.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
                    Toast.makeText(OKhttp.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(OKhttp.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            progressBar.setProgress((int) (100 * progress));
        }
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.Upload)
        {
            checkPermission();
            FileChooseUtil fileChooseUtil=new FileChooseUtil();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");//选择图片
            intent.setType("audio/*"); //选择音频
            intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
            intent.setType("video/*;image/*");//同时选择视频和图片
            intent.setType("*/*");//无类型限制
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent,1);
            AlertDialog.Builder builder = new AlertDialog.Builder(OKhttp.this);
            builder.setTitle("消息提示框");     //设置对话框标题
            builder.setMessage("确认上传?");
            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    multiFileUpload();
                }
            });
            builder.setCancelable(true);   //设置按钮是否可以按返回键取消,false则不可以取消
            AlertDialog dialog = builder.create();  //创建对话框
            dialog.setCanceledOnTouchOutside(true);      //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
            dialog.show();
        }
    }

}

class FileChooseUtil {

    private Context context;
    private static FileChooseUtil util = null;

    public FileChooseUtil(Context context) {
        this.context = context;
    }

    public FileChooseUtil() {

    }

    public  static FileChooseUtil getInstance(Context context) {
        if (util == null) {
            util = new FileChooseUtil(context);
        }
        return util;
    }

    /**
     * 对外接口  获取uri对应的路径
     *
     * @param uri
     * @return
     */
    public String getChooseFileResultPath(Uri uri) {
        String chooseFilePath = null;
        if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
            chooseFilePath = uri.getPath();
            Toast.makeText(context, chooseFilePath, Toast.LENGTH_SHORT).show();
            return chooseFilePath;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
            chooseFilePath = getPath(context, uri);
        } else {//4.4以下下系统调用方法
            chooseFilePath = getRealPathFromURI(uri);
        }
        return chooseFilePath;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);

            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);

            }

        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);

        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            uri.getPath();

        }
        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}