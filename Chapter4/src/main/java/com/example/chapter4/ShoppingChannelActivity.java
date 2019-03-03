package com.example.chapter4;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.*;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter4.bean.CartInfo;
import com.example.chapter4.bean.GoodsInfo;
import com.example.chapter4.database.CartDBHelper;
import com.example.chapter4.database.GoodsDBHelper;
import com.example.chapter4.util.DateUtil;
import com.example.chapter4.util.ShareUtil;
import com.example.chapter4.util.Utils;

import org.w3c.dom.Text;

import java.io.LineNumberReader;
import java.util.ArrayList;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    private LinearLayout ll_channel;
    private int mCount;
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);
        TextView tv_title=findViewById(R.id.tv_title);
        tv_count=findViewById(R.id.tv_count);
        ll_channel=findViewById(R.id.ll_channel);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        tv_title.setText("手机商城");
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_cart){
            Intent intent=new Intent(this,ShoppingCartActivity.class);
            startActivity(intent);
        }
    }

    private void addToCart(long goods_id){
        mCount++;
        tv_count.setText(""+mCount);
        //将购物车中的商品数量写入共享参数中
        ShareUtil.getInstance(this).writeShared("count",""+mCount);
        //根据商品编号查询购物车数据库的商品记录
        CartInfo info=mCartHelper.queryByGoodsId(goods_id);
        if(info!=null){
            info.count++;
            info.update_time= DateUtil.getNowDateTime("");
            mCartHelper.update(info);
        }else{
            info=new CartInfo();
            info.goods_id=goods_id;
            info.count=1;
            info.update_time=DateUtil.getNowDateTime("");
            mCartHelper.insert(info);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCount=Integer.parseInt(ShareUtil.getInstance(this).readShared("count","0"));
        tv_count.setText(""+mCount);
        mCartHelper=CartDBHelper.getInstance(this,1);
        mGoodsHelper=GoodsDBHelper.getInstance(this,1);

        mCartHelper.openWriteLink();
        mGoodsHelper.openReadLink();
        showGoods();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoodsHelper.closeLink();
        mCartHelper.closeLink();
    }


    private LayoutParams mFullParams, mHalfParams;

    private LinearLayout newLinearLayout(int orientation,int weight){
        LinearLayout ll_new=new LinearLayout(this);
        ll_new.setOrientation(orientation);
        ll_new.setLayoutParams((weight==0)?mFullParams:mHalfParams);
        return ll_new;

    }


    private void showGoods() {
        ll_channel.removeAllViews();
        mFullParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        mHalfParams=new LayoutParams(0,LayoutParams.WRAP_CONTENT,1);
        //给mHalfParams设置四周的空白距离
        mHalfParams.setMargins(Utils.dip2px(this,2),Utils.dip2px(this,2),
                Utils.dip2px(this,2),Utils.dip2px(this,2));
        //创建一行的线性布局
        LinearLayout ll_row=newLinearLayout(LinearLayout.HORIZONTAL,0);
        //查询商品数据库中所有商品的信息
        ArrayList<GoodsInfo> goodsInfos=mGoodsHelper.query("1=1");
        int i=0;
        for(;i<goodsInfos.size();i++){
            final GoodsInfo info = goodsInfos.get(i);
            // 创建一个商品项的垂直线性布局，从上到下依次列出商品标题、商品图片、商品价格
            LinearLayout ll_goods=newLinearLayout(LinearLayout.VERTICAL,1);
            ll_goods.setBackgroundColor(Color.WHITE);
            TextView tv_name=new TextView(this);
            tv_name.setLayoutParams(mFullParams);
            tv_name.setGravity(Gravity.CENTER);
            tv_name.setText(info.name);
            tv_name.setTextColor(Color.BLACK);
            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,17);
            ll_goods.addView(tv_name);

            // 添加商品小图
            ImageView iv_thumb=new ImageView(this);
            iv_thumb.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,Utils.dip2px(this,150)));
            iv_thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
            iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShoppingChannelActivity.this, ShoppingDetailActivity.class);
                    intent.putExtra("goods_id", info.rowid);
                    startActivity(intent);
                }
            });
            ll_goods.addView(iv_thumb);

            //添加商品价格
            LinearLayout ll_bottom=newLinearLayout(LinearLayout.HORIZONTAL,0);
            TextView tv_price=new TextView(this);
            tv_price.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,2));
            tv_price.setGravity(Gravity.CENTER);
            tv_price.setText(""+ (int) info.price);
            tv_price.setTextColor(Color.BLACK);
            tv_price.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            ll_bottom.addView(tv_price);
            // 添加购物车按钮
            Button btn_add=new Button(this);
            btn_add.setLayoutParams(new LayoutParams(0,LayoutParams.WRAP_CONTENT,3));
            btn_add.setGravity(Gravity.CENTER);
            btn_add.setText("加入购物车");
            btn_add.setTextColor(Color.BLACK);
            btn_add.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(info.rowid);
                    Toast.makeText(ShoppingChannelActivity.this,
                            "已添加一部" + info.name + "到购物车", Toast.LENGTH_SHORT).show();
                }
            });
            ll_bottom.addView(btn_add);
            ll_goods.addView(ll_bottom);

            //把商品项也添加到该行上
            ll_row.addView(ll_goods);

            if(i%2==1){
                ll_channel.addView(ll_row);
                ll_row=newLinearLayout(LinearLayout.HORIZONTAL,0);
            }
        }
        if(i%2==0){
            ll_row.addView(newLinearLayout(LinearLayout.VERTICAL,1));
            ll_channel.addView(ll_row);
        }

    }
}















