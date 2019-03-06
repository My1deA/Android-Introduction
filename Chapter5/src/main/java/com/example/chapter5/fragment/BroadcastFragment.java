package com.example.chapter5.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.chapter5.R;

public class BroadcastFragment extends Fragment {
    private final static String TAG="BroadcastFragment";
    public final static String EVENT = "com.example.Chapter5.fragment.BroadcastFragment";
    protected View mView;
    protected Context mContext;
    private int mPosition;
    private int mImageId;
    private String mDesc;
    private int mColorSeq=0;
    private Spinner sp_bg;
    private boolean bFrist=true;

    public static BroadcastFragment newInstance(int position,int image_id,String desc){
        BroadcastFragment fragment=new BroadcastFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putInt("image_id",image_id);
        bundle.putString("desc",desc);
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        mContext=getActivity();
        if(getArguments()!=null){
            mPosition=getArguments().getInt("position",0);
            mImageId=getArguments().getInt("image_id",0);
            mDesc=getArguments().getString("desc");
        }

        mView=inflater.inflate(R.layout.fragment_broadcast,container,false);
        ImageView iv_pic=mView.findViewById(R.id.iv_pic);
        TextView tv_desc=mView.findViewById(R.id.tv_desc);
        iv_pic.setImageResource(mImageId);
        tv_desc.setText(mDesc);
        return mView;
    }

    private String[] mColorNameArray={"白色","黄色","绿色","青色","蓝色"};
    private int[] mColorIdArray={Color.WHITE,Color.YELLOW,Color.GREEN,Color.CYAN,Color.BLUE};

    //初始背景色的下拉框
    private void initSpinner(){
        ArrayAdapter<String> diviverAdapter=new ArrayAdapter<String>(mContext,R.layout.item_select,mColorNameArray);
        sp_bg=mView.findViewById(R.id.sp_bg);
        sp_bg.setAdapter(diviverAdapter);
        sp_bg.setPrompt("请设置背景色");
        sp_bg.setSelection(mColorSeq);
        sp_bg.setOnItemSelectedListener(new ColorSelectedListener());
    }

    class ColorSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(!bFrist||mColorSeq!=position){
                mColorSeq=position;
                Intent intent=new Intent(BroadcastFragment.EVENT);
                intent.putExtra("seq",position);
                intent.putExtra("color",mColorIdArray[position]);
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
            bFrist=false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { }
    }

    private BgChangeReceiver bgChangeReceiver;
    private  class BgChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent!=null){
                mColorSeq=intent.getIntExtra("seq",0);
                sp_bg.setSelection(mColorSeq);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initSpinner();
        bgChangeReceiver=new BgChangeReceiver();
        IntentFilter filter=new IntentFilter(BroadcastFragment.EVENT);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(bgChangeReceiver,filter);
    }

    @Override
    public void onStop() {
        // 注销广播接收器，注销之后就不再接收广播
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(bgChangeReceiver);
        super.onStop();
    }
}














