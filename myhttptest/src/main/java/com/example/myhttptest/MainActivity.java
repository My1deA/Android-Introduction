package com.example.myhttptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG="My_TestHttp";
    private TextView tv_result;
    private ProgressBar pb_circle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt_Http).setOnClickListener(this);
        findViewById(R.id.bt_Get).setOnClickListener(this);
        findViewById(R.id.bt_Post).setOnClickListener(this);
        findViewById(R.id.bt_Text).setOnClickListener(this);
        findViewById(R.id.bt_Picture).setOnClickListener(this);
        findViewById(R.id.bt_Video).setOnClickListener(this);
        findViewById(R.id.bt_Download).setOnClickListener(this);
        tv_result=findViewById(R.id.tv_result);
        pb_circle=findViewById(R.id.pb_circle);
    }


    @Override
    public void onClick(View v) {

    }

    public void getDataByOkhttpUtils()
    {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
//        url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url="http://http://api.m.mtime.cn/PagesubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
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
           tv_result.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            tv_result.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            pb_circle.setProgress((int) (100 * progress));
        }
    }
}
