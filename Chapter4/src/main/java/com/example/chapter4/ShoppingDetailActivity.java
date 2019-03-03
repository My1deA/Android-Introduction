package com.example.chapter4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter4.bean.CartInfo;
import com.example.chapter4.bean.GoodsInfo;
import com.example.chapter4.database.CartDBHelper;
import com.example.chapter4.database.GoodsDBHelper;
import com.example.chapter4.util.DateUtil;
import com.example.chapter4.util.ShareUtil;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private int mCount;
    private long mGoodsId;
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartDBHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_datail);
        tv_title=findViewById(R.id.tv_title);
        tv_count=findViewById(R.id.tv_count);
        tv_goods_price=findViewById(R.id.tv_goods_price);
        tv_goods_desc=findViewById(R.id.tv_goods_desc);
        iv_goods_pic=findViewById(R.id.iv_goods_pic);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.tv_btn_add_cart).setOnClickListener(this);

        mCount=Integer.parseInt(ShareUtil.getInstance(this).readShared("count","0"));
        tv_count.setText(""+mCount);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_cart){
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.tv_btn_add_cart){
            addToCart(mGoodsId);
            Toast.makeText(this, "成功添加至购物车", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToCart(long mGoodsId) {
        mCount++;
        ShareUtil.getInstance(this).writeShared("count",""+mCount);
        CartInfo info=mCartDBHelper.queryByGoodsId(mGoodsId);
        if(info!=null){
            info.count++;
            info.update_time= DateUtil.getNowDateTime("");
            mCartDBHelper.update(info);
        }else{
            info = new CartInfo();
            info.goods_id = mGoodsId;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            // 往购物车数据库中添加一条新的商品记录
            mCartDBHelper.insert(info);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取商品数据库的帮助器对象
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        // 打开商品数据库的读连接
        mGoodsHelper.openReadLink();
        // 获取购物车数据库的帮助器对象
        mCartDBHelper = CartDBHelper.getInstance(this, 1);
        // 打开购物车数据库的写连接
        mCartDBHelper.openWriteLink();
        // 展示商品详情
        showDetail();
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 关闭商品数据库的数据库连接
        mGoodsHelper.closeLink();
        // 关闭购物车数据库的数据库连接
        mCartDBHelper.closeLink();
    }

    private void showDetail() {
        mGoodsId=getIntent().getLongExtra("goods_id",0l);
        if(mGoodsId>0){
            GoodsInfo info=mGoodsHelper.queryById(mGoodsId);
            tv_title.setText(info.name);
            tv_goods_desc.setText(info.desc);
            tv_goods_price.setText(""+info.price);
            Bitmap pic= BitmapFactory.decodeFile(info.pic_path);
            iv_goods_pic.setImageBitmap(pic);
        }

    }

}
