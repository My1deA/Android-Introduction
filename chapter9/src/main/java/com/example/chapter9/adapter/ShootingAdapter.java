package com.example.chapter9.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.chapter9.R;

import java.util.ArrayList;

public class ShootingAdapter extends BaseAdapter {
    private ArrayList<String> mPathList;
    private Context mContext;

    public ShootingAdapter(Context context,ArrayList<String> PathList){
        mContext=context;
        mPathList=PathList;
    }

    @Override
    public int getCount() {
        return mPathList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPathList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_shooting,null);
            holder.iv_shooting=convertView.findViewById(R.id.iv_shooting);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        String path=mPathList.get(position);
        holder.iv_shooting.setImageBitmap(BitmapFactory.decodeFile(path));


        return convertView;
    }

    public final class ViewHolder{
        public ImageView iv_shooting;
    }
}
