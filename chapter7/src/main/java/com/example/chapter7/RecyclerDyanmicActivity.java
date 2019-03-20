package com.example.chapter7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chapter7.adapter.LinearDynamicAdapter;
import com.example.chapter7.bean.GoodsInfo;
import com.example.chapter7.widget.RecyclerExtras;
import com.example.chapter7.widget.SpacesItemDecoration;

import java.util.ArrayList;

public class RecyclerDyanmicActivity extends AppCompatActivity implements View.OnClickListener,
        RecyclerExtras.OnItemClickListener, RecyclerExtras.OnItemLongClickListener, RecyclerExtras.OnItemDeleteClickListener {

    private RecyclerView rv_dynamic;
    private LinearDynamicAdapter adapter;
    private ArrayList<GoodsInfo> mPublicArray;
    private ArrayList<GoodsInfo> mAllArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reycler_dynamic);
        findViewById(R.id.btn_recycler_add).setOnClickListener(this);
        initRecyclerDynamic();
    }

    private void initRecyclerDynamic() {
        rv_dynamic=findViewById(R.id.rv_dynamic);
        LinearLayoutManager manager=new LinearLayoutManager(this, LinearLayout.VERTICAL,false);

        rv_dynamic.setLayoutManager(manager);
        mAllArray=GoodsInfo.getDefaultList();
        mPublicArray=GoodsInfo.getDefaultList();

        adapter=new LinearDynamicAdapter(this,mPublicArray);
        adapter.setmOnItemClickListener(this);
        adapter.setOnItemDeleteClickListener(this);
        adapter.setOnItemLongClickListener(this);

        rv_dynamic.setAdapter(adapter);
        rv_dynamic.setItemAnimator(new DefaultItemAnimator());
        rv_dynamic.addItemDecoration(new SpacesItemDecoration(1));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_recycler_add){
            int position=(int)(Math.random()*100%mAllArray.size());
            GoodsInfo old_item=mAllArray.get(position);
            GoodsInfo new_item=new GoodsInfo(old_item.pic_id,old_item.title,old_item.desc);
            mPublicArray.add(0,new_item);
            adapter.notifyItemInserted(0);
            rv_dynamic.scrollToPosition(0);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        String desc = String.format("您点击了第%d项，标题是%s", position + 1,
                mPublicArray.get(position).title);
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        GoodsInfo item=mPublicArray.get(position);
        item.bPressed=!item.bPressed;
        mPublicArray.set(position,item);
        adapter.notifyItemInserted(position);
    }

    @Override
    public void onItemDeleteClick(View view, int position) {
        mPublicArray.remove(position);
        adapter.notifyItemInserted(position);
    }
}
