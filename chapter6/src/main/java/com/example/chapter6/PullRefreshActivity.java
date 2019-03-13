package com.example.chapter6;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chapter6.util.MeasureUtil;

public class PullRefreshActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG="PullRefreshActivity";
    private LinearLayout ll_header;
    private Button btn_pull;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_refresh);
        ll_header=findViewById(R.id.ll_headr);
        btn_pull=findViewById(R.id.btn_pull);
        ll_header.setVisibility(View.GONE);
        btn_pull.setOnClickListener(this);
    }

    private boolean isStarted=false;
    private Handler mHandler=new Handler();
    private int mOffset=0;

    private Runnable mRefresh=new Runnable() {
        @Override
        public void run() {
            if(mOffset<=0){
                ll_header.setPadding(0,mOffset,0,0);
                ll_header.setVisibility(View.VISIBLE);
                mOffset+=8;
                mHandler.postDelayed(this,80);
            }else{
                btn_pull.setText("恢复页面");
                btn_pull.setEnabled(true);
            }
        }
    };

    @Override
    public void onClick(View v) {
        int height=(int) MeasureUtil.getRealHeight(ll_header);
        if(v.getId()==R.id.btn_pull){
            if(!isStarted){
                mOffset=-height;
                btn_pull.setEnabled(false);
                mHandler.post(mRefresh);
            }else{
                btn_pull.setText("开始下拉");
                ll_header.setVisibility(View.GONE);
            }
            isStarted=!isStarted;
        }
    }
}
