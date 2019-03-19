package com.example.chapter7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chapter7.adapter.RecyclerGridAdapter;
import com.example.chapter7.bean.GoodsInfo;
import com.example.chapter7.widget.SpacesItemDecoration;

public class RecyclerGridActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid);
        initRecyclerGrid();
    }

    private void initRecyclerGrid() {
        RecyclerView rv_grid=findViewById(R.id.rv_grid);
        GridLayoutManager manager=new GridLayoutManager(this,5);
        rv_grid.setLayoutManager(manager);
        RecyclerGridAdapter adapter=new RecyclerGridAdapter(this, GoodsInfo.getDefaultGrid());
        adapter.setOnItemClickListener(adapter);
        adapter.setOnItemLongClickListener(adapter);
        rv_grid.setItemAnimator(new DefaultItemAnimator());
        rv_grid.addItemDecoration(new SpacesItemDecoration(1));
    }
}
