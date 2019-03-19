package com.example.chapter7.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.view.View;

import com.example.chapter7.util.Utils;

import java.util.ArrayList;
import java.util.List;
import com.example.chapter7.R;
public class BannerPager extends RelativeLayout implements View.OnClickListener {

    private static final String TAG="BannerPager";
    private Context mContext;
    private RadioGroup rg_indicator;
    private ViewPager vp_banner; // 声明一个翻页视图对象
    private List<ImageView> mViewList=new ArrayList<ImageView>();
    private int mInterval=2000;

    public BannerPager(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext=context;
        initView();
    }

    public BannerPager(Context context){
        this(context,null);
    }

    private void initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.banner_pager,null);
        vp_banner=view.findViewById(R.id.vp_banner);
        rg_indicator=view.findViewById(R.id.vp_banner);
        addView(view);

    }

    private void setButton(int position){
        ((RadioButton)rg_indicator.getChildAt(position)).setChecked(true);
    }

    public void start(){
        mHandler.postDelayed(mScroll,mInterval);
    }

    public void stop(){
        mHandler.removeCallbacks(mScroll);
    }

    public void setInterval(int interval){
        mInterval=interval;
    }

    public void setImage(ArrayList<Integer> imageList){
        int dip_15= Utils.dip2px(mContext,15);
        for(int i=0;i<imageList.size();i++){
            Integer imageID=imageList.get(i);
            ImageView iv=new ImageView(mContext);
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imageID);
            iv.setOnClickListener(this);
            mViewList.add(iv);
        }
        vp_banner.setAdapter(new ImageAdapter());
        vp_banner.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                setButton(position);
            }
        });

        for(int i=0;i<imageList.size();i++){
            RadioButton radioButton=new RadioButton(mContext);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(dip_15,dip_15));
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setButtonDrawable(R.drawable.indicator_selector);
            rg_indicator.addView(radioButton);
        }
        vp_banner.setCurrentItem(0);
        setButton(0);
    }


    private class ImageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        public Object instantiateItem(ViewGroup container,int position){
            container.addView(mViewList.get(position));
            return  mViewList.get(position);
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }


    }

    public interface BannerClickListener{
        void onBannerClick(int positon);
    }
    private BannerClickListener mListener;

    public void setOnBannerListener(BannerClickListener listener){
        mListener=listener;
    }

    @Override
    public void onClick(View v) {
        int position=vp_banner.getCurrentItem();
        mListener.onBannerClick(position);
    }

    private Handler mHandler=new Handler();
    private Runnable mScroll=new Runnable() {
        @Override
        public void run() {
            scrollToNext();
            mHandler.postDelayed(this,mInterval);
        }
    };

    public void scrollToNext(){
        int index=vp_banner.getCurrentItem()+1;
        if (index>=mViewList.size()){
            index=0;
        }
        vp_banner.setCurrentItem(index);
    }

}
