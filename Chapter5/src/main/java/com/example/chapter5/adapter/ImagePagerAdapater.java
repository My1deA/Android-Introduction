package com.example.chapter5.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.chapter5.bean.GoodsInfo;

import java.util.ArrayList;

public class ImagePagerAdapater extends PagerAdapter {

    private Context mContext;//声明一个上下文对象
    //声明一个 图像视图队列
    private ArrayList<ImageView> mViewList=new ArrayList<ImageView>();
    //声明一个商品信息队列
    private ArrayList<GoodsInfo> mGoodsList=new ArrayList<GoodsInfo>();

    //图像翻页适配器的构造函数 传入上下文的商品的信息队列
    public ImagePagerAdapater(Context context,ArrayList<GoodsInfo> goodsInfos){
        mContext=context;
        mGoodsList=goodsInfos;
        for(int i=0;i<mGoodsList.size();i++){
            ImageView view=new ImageView(mContext);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            view.setImageResource(mGoodsList.get(i).pic);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
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

    //从容器中销毁指定位置的页面
    public void destroyItem(ViewGroup container,int position,Object object){
        container.removeView(mViewList.get(position));
    }

    //实例化指定位置的页面 并将其添加到容器中
    public Object instantiateItem(ViewGroup container,int position){
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    //获得指定页面的标题文本
    public CharSequence gatPageTitle(int position){
        return mGoodsList.get(position).name;
    }


}














