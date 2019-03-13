package com.example.chapter6.util;

import android.app.Activity;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MeasureUtil {
    //获得指定文本的宽度
    public static float getTextWidth(String text,float textSize){
        if(TextUtils.isEmpty(text)){
            return 0;
        }
        Paint paint=new Paint();
        paint.setTextSize(textSize);
        return paint.measureText(text);
    }

    //获得指定文本的高度
    public static float getTextHeight(String text,float textSize){
        if(TextUtils.isEmpty(text)){
            return 0;
        }
        Paint paint=new Paint();
        paint.setTextSize(textSize);
        Paint.FontMetrics fm=paint.getFontMetrics();
        return fm.descent-fm.ascent;
    }

    //根据资源编号获得线性布局的实际高度
    public static float getRealHeight(Activity act,int resid){
        LinearLayout llayout=act.findViewById(resid);
        return getRealHeight(llayout);
    }

    //计算指定线性布局的实际高度
    public static float getRealHeight(View child){
        LinearLayout lllayout=(LinearLayout)child;
        ViewGroup.LayoutParams params=lllayout.getLayoutParams();
        if(params==null){
            params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //布局参数的宽度规格
        int widthSpec=ViewGroup.getChildMeasureSpec(0,0,params.width);
        int heightSpec;
        if(params.height>0){
            heightSpec=View.MeasureSpec.makeMeasureSpec(params.height, View.MeasureSpec.EXACTLY);
        }else{
            heightSpec=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }

        lllayout.measure(widthSpec,heightSpec);
        return  lllayout.getMeasuredHeight();
    }
}
