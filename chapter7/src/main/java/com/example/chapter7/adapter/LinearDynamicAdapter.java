package com.example.chapter7.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chapter7.bean.GoodsInfo;
import com.example.chapter7.widget.RecyclerExtras;

import java.util.ArrayList;
import com.example.chapter7.R;
import com.example.chapter7.widget.RecyclerExtras.OnItemClickListener;
import com.example.chapter7.widget.RecyclerExtras.OnItemDeleteClickListener;
import com.example.chapter7.widget.RecyclerExtras.OnItemLongClickListener;

public class LinearDynamicAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener, View.OnLongClickListener {

    private final static String TAG="LinearDynamicAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mPublicArray;

    public LinearDynamicAdapter(Context context,ArrayList<GoodsInfo> publicArray){
        mContext=context;
        mPublicArray=publicArray;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item_linear,viewGroup,false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GoodsInfo item=mPublicArray.get(i);
        ItemHolder holder=(ItemHolder) viewHolder;
        holder.iv_pic.setImageResource(item.pic_id);
        holder.tv_title.setText(item.pic_id);
        holder.tv_desc.setText(item.desc);
        holder.tv_delete.setVisibility((item.bPressed)?View.VISIBLE:View.GONE);
        holder.tv_delete.setId(item.id*10+DELETE);
        holder.tv_delete.setOnClickListener(this);
        holder.ll_item.setId(item.id*10+CLICK);

        holder.ll_item.setOnClickListener(this);
        holder.ll_item.setOnLongClickListener(this);
    }

    @Override
    public int getItemCount(){
        return mPublicArray.size();
    }

    private int getPosition(int item_id){
        int pos=0;
        for(int i=0;i<mPublicArray.size();i++){
            if (mPublicArray.get(i).id==item_id){
                pos=i;
                break;
            }
        }
        return pos;
    }

    public int CLICK=0;
    public int DELETE=1;


    @Override
    public void onClick(View v) {
        int position=getPosition((int)v.getId()/10);

        int type=(int)v.getId()%10;
        if(type==CLICK){
            if (mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(v,position);
            }else if(type==DELETE){
                if(mOnItemDeleteClickListener!=null){
                    mOnItemDeleteClickListener.onItemDeleteClick(v,position);
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int position=getPosition((int)v.getId()/10);
        if(mOnItemLongClickListener!=null){
            mOnItemLongClickListener.onItemLongClick(v,position);
        }
        return true;
    }

    public int getItemView(int position){
        return  0;
    }

    public long getItemId(int position){
        return position;
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        public LinearLayout ll_item;
        public ImageView iv_pic;
        public TextView tv_title;
        public TextView tv_desc;
        public TextView tv_delete;

        public ItemHolder(@NonNull View v) {
            super(v);
            ll_item = v.findViewById(R.id.ll_item);
            iv_pic = v.findViewById(R.id.iv_pic);
            tv_title = v.findViewById(R.id.tv_title);
            tv_desc = v.findViewById(R.id.tv_desc);
            tv_delete = v.findViewById(R.id.tv_delete);
        }
    }

    private OnItemClickListener mOnItemClickListener;
    public void setmOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener=listener;
    }

    // 声明列表项的长按监听器对象
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    // 声明列表项的删除监听器对象
    private OnItemDeleteClickListener mOnItemDeleteClickListener;

    public void setOnItemDeleteClickListener(OnItemDeleteClickListener listener) {
        this.mOnItemDeleteClickListener = listener;
    }

}
