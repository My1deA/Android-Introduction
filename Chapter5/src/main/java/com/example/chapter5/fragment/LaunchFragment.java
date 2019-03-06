package com.example.chapter5.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chapter5.R;

public class LaunchFragment extends Fragment {
    protected View mView;
    protected Context mContext;
    protected int mPosition;
    protected int mImageId;
    protected int mCount=4;

    public static LaunchFragment newInstance(int position,int image_id){
        LaunchFragment fragment=new LaunchFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putInt("image_id",image_id);
        fragment.setArguments(bundle);
        return  fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        mContext=getActivity();
        if(getArguments()!=null){
            mPosition=getArguments().getInt("position",0);
            mImageId=getArguments().getInt("image_id",0);
        }

        mView=inflater.inflate(R.layout.item_launch,container,false);
        ImageView iv_launch=mView.findViewById(R.id.iv_launch);
        RadioGroup rg_indicate=mView.findViewById(R.id.rg_indicate);
        Button btn_start=mView.findViewById(R.id.btn_start);

        iv_launch.setImageResource(mImageId);

        for(int j=0;j<mCount;j++){
            RadioButton radio=new RadioButton(mContext);
            radio.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            radio.setButtonDrawable(R.drawable.launch_guide);
            radio.setPadding(10,10,10,10);
            rg_indicate.addView(radio);
        }

        ((RadioButton)rg_indicate.getChildAt(mPosition)).setChecked(true);

        if(mPosition==mCount-1){
            btn_start.setVisibility(View.VISIBLE);
            btn_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "欢迎您开启美好生活", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return mView; // 返回该碎片的视图对象

    }
}











