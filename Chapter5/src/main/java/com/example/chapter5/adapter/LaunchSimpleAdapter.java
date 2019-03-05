package com.example.chapter5.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
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

import java.util.ArrayList;

public class LaunchSimpleAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<View> mViewList=new ArrayList<View>();

    public LaunchSimpleAdapter(Context context,int[] imageArray){
        mContext=context;
        for (int i=0;i<imageArray.length;i++){
            View view= LayoutInflater.from(context).inflate(R.layout.item_launch,null);
            ImageView iv_launch=view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate=view.findViewById(R.id.rg_indicate);
            Button btn_start=view.findViewById(R.id.btn_start);
            //iv_launch 设置图片
            iv_launch.setImageResource(imageArray[i]);

            //每张图片都分配一个对应的单选按钮RadioButton
            for(int j=0;j<imageArray.length;j++){
                RadioButton radioButton=new RadioButton(mContext);
                radioButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                radioButton.setButtonDrawable(R.drawable.launch_guide);
                radioButton.setPadding(10,10,10,10);
                rg_indicate.addView(radioButton);
            }
            //当前位置的单选按钮要高亮显示
            ((RadioButton)rg_indicate.getChildAt(i)).setChecked(true);

            if(i==imageArray.length-1){
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,"欢迎",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            mViewList.add(view);
        }
    }


    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    //从容器中销毁指定位置页面
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(mViewList.get(position));
    }

    //实例化指定位置的页面 并添加到容器中
    public Object instantiateItem(ViewGroup container,int position){
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }


}
