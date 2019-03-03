package com.example.chapter4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter4.bean.CartInfo;
import com.example.chapter4.bean.GoodsInfo;
import com.example.chapter4.database.CartDBHelper;
import com.example.chapter4.database.GoodsDBHelper;
import com.example.chapter4.util.FileUtil;
import com.example.chapter4.util.ShareUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartActivity extends Activity implements View.OnClickListener {

    private final  static String TAG="ShoppingCartActivity";
    private ImageView iv_menu;
    private TextView tv_count;
    private TextView tv_total_price;
    private LinearLayout ll_content;
    private LinearLayout ll_cart;
    private LinearLayout ll_emtry;
    private int mCount;
    private GoodsDBHelper mGoodsHelper;
    private CartDBHelper mCartHelper;

    private HashMap<Integer, CartInfo> mCartGoods=new HashMap<Integer, CartInfo>();//根据商品视图编号寻找商品信息的映射
    private ArrayList<CartInfo> mCartArray=new ArrayList<CartInfo>();//购物车商品信息
    private HashMap<Long,GoodsInfo> mGoodsMap=new HashMap<Long,GoodsInfo>();//根据商品编号寻找
    private View mContextView;//引发上下文踩点的视图对象
    private int mBeginViewID=0x7F24FFF0;// 声明一个起始的视图编号

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shopping_cart);
        iv_menu=findViewById(R.id.iv_menu);
        TextView tv_title=findViewById(R.id.tv_title);
        tv_count=findViewById(R.id.tv_count);
        tv_total_price=findViewById(R.id.tv_total_price);
        ll_content=findViewById(R.id.ll_content);
        ll_cart=findViewById(R.id.ll_cart);
        ll_emtry=findViewById(R.id.ll_empty);
        iv_menu.setOnClickListener(this);

        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);
        iv_menu.setVisibility(View.VISIBLE);
        tv_title.setText("购物车");
    }


    private void showCount(int count){
        mCount=count;
        tv_count.setText(""+mCount);
        if(mCount==0){
            ll_content.setVisibility(View.GONE);
            ll_cart.removeAllViews();
            ll_emtry.setVisibility(View.VISIBLE);
        }else{
            ll_content.setVisibility(View.VISIBLE);
            ll_emtry.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_menu){
            openOptionsMenu();
        }else if(v.getId()==R.id.btn_shopping_channel){
            Intent intent=new Intent(this,ShoppingChannelActivity.class);
            startActivity(intent);
        }else if(v.getId()==R.id.btn_settle){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("结算商品");
            builder.setMessage("客官抱歉，支付功能尚未开通，请下次再来");
            builder.setPositiveButton("我知道了", null);
            builder.create().show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu){
        //从menu_cart.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_shopping){
            Intent intent = new Intent(this, ShoppingChannelActivity.class);
            startActivity(intent);
        }else if(id==R.id.menu_clear){
            mCartHelper.deleteAll();
            ll_cart.removeAllViews();
            ShareUtil.getInstance(this).writeShared("count","0");
            showCount(0);
            mCartGoods.clear();
            mGoodsMap.clear();
            Toast.makeText(this, "购物车已清空", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.menu_return){
            finish();
        }
        return true;
    }


    //跳转到商品详情页面
    private void goDetail(long rowid){
        Intent intent=new Intent(this,ShoppingDetailActivity.class);
        intent.putExtra("goods_id",rowid);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCount=Integer.parseInt(ShareUtil.getInstance(this).readShared("count","0"));
        showCount(mCount);

        mGoodsHelper=GoodsDBHelper.getInstance(this,1);
        mGoodsHelper.openWriteLink();

        mCartHelper=CartDBHelper.getInstance(this,1);
        mCartHelper.openWriteLink();
        dowmloadGoods();
        showCart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoodsHelper.closeLink();
        mCartHelper.closeLink();
    }


    private void refreshTotalPrice(){
        int total_price=0;
        for(CartInfo info:mCartArray){
            GoodsInfo goods=mGoodsMap.get(info.goods_id);
            total_price += goods.price * info.count;
        }
        tv_total_price.setText("" + total_price);
    }

//    // 声明一个起始的视图编号
//    private int mBeginViewId = 0x7F24FFF0;
//    // 声明一个购物车中的商品信息队列
//    private ArrayList<CartInfo> mCartArray = new ArrayList<CartInfo>();
//    // 声明一个根据商品编号查找商品信息的映射
//    private HashMap<Long, GoodsInfo> mGoodsMap = new HashMap<Long, GoodsInfo>();


    private void showCart() {
        mCartArray=mCartHelper.query("1=1");
        Log.d(TAG,"mCartArray.size():"+mCartArray.size());
        if(mCartArray==null&&mCartArray.size()<=0){
            return;
        }

        ll_cart.removeAllViews();
        LinearLayout ll_row=newLinearLayout(LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll_row.addView(newTextView(0,2,Gravity.CENTER,"图片",Color.BLACK,15));
        ll_row.addView(newTextView(0,3,Gravity.CENTER,"名称",Color.BLACK,15));
        ll_row.addView(newTextView(0,1,Gravity.CENTER,"数量",Color.BLACK,15));
        ll_row.addView(newTextView(0,1,Gravity.CENTER,"单价",Color.BLACK,15));
        ll_row.addView(newTextView(0,1,Gravity.CENTER,"总价",Color.BLACK,15));
        //添加到购物车中
        ll_cart.addView(ll_row);
        for(int i=0;i<mCartArray.size();i++){
            final CartInfo info=mCartArray.get(i);
            // 根据商品编号查询商品数据库中的商品记录
            final GoodsInfo goods=mGoodsHelper.queryById(info.goods_id);
            Log.d(TAG, "name=" + goods.name + ",price=" + goods.price + ",desc=" + goods.desc);
            mGoodsMap.put(info.goods_id,goods);

            //创建商品行的水平线性视图 从左到右依次为商品小图 商品名称 商品数量 商品单价，商品总价
            ll_row=newLinearLayout(LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_row.setId(mBeginViewID+i);
            //添加商品小图
            ImageView iv_thumb=new ImageView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,2);
            iv_thumb.setLayoutParams(params);
            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.goods_id));
            ll_row.addView(iv_thumb);

            //添加商品名称和描述
            LinearLayout ll_name=new LinearLayout(this);
            LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,3);
            ll_name.setLayoutParams(params);
            ll_name.setOrientation(LinearLayout.VERTICAL);
            ll_name.addView(newTextView(-3,1,Gravity.LEFT,goods.name,Color.BLACK,17));
            ll_name.addView(newTextView(-3,1,Gravity.LEFT,goods.desc,Color.GRAY,12));
            ll_row.addView(ll_name);

            //添加商品数量 单价 总价
            ll_row.addView(newTextView(1,1,Gravity.CENTER,""+info.count,Color.BLACK,17));
            ll_row.addView(newTextView(1,1,Gravity.RIGHT,""+(int)goods.price,Color.BLACK,15));
            ll_row.addView(newTextView(1,1,Gravity.RIGHT,""+(int)(info.count*goods.price),Color.RED,17));

            ll_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDetail(info.goods_id);
                }
            });
            //给商品行注册上下文菜单 防止重复
            unregisterForContextMenu(ll_row);
            registerForContextMenu(ll_row);
            mCartGoods.put(ll_row.getId(),info);
            ll_cart.addView(ll_row);
        }

        // 重新计算购物车中的商品总金额
        refreshTotalPrice();

    }







    private LinearLayout newLinearLayout(int orientation, int height) {
        LinearLayout ll_new=new LinearLayout(this);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        ll_new.setLayoutParams(params);
        ll_new.setOrientation(orientation);
        ll_new.setBackgroundColor(Color.WHITE);
        return ll_new;
    }

    private TextView newTextView(int height,float weight,int gravity,String text,int textColor,int textSize){
        TextView tv_new =new TextView(this);
        if(height==-3){
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,weight);

        }else{
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,(height==0)? LinearLayout.LayoutParams.WRAP_CONTENT:
                    LinearLayout.LayoutParams.MATCH_PARENT,weight);
            tv_new.setLayoutParams(params);
        }
        tv_new.setText(text);
        tv_new.setTextColor(textColor);
        tv_new.setTextSize(TypedValue.COMPLEX_UNIT_DIP,textSize);
        tv_new.setGravity(Gravity.CENTER|gravity);
        return  tv_new;

    }

    private String mFirst = "true"; // 是否首次打开

    private void dowmloadGoods() {
        mFirst=ShareUtil.getInstance(this).readShared("frist","true");
        String path=MainApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS.toString())+"/";
        if(mFirst.equals("true")){
            ArrayList<GoodsInfo> goodsInfos=GoodsInfo.getDefaultList();
            for(int i=0;i<goodsInfos.size();i++){// 往商品数据库插入一条该商品的记录
                GoodsInfo info=goodsInfos.get(i);
                long rowid=mGoodsHelper.insert(info);
                info.rowid=rowid;

                // 往全局内存写入商品小图
                Bitmap thumb= BitmapFactory.decodeResource(getResources(),info.thumb);
                MainApplication.getInstance().mIconMap.put(rowid,thumb);
                String thumb_path=path+rowid+"_s.jpg";
                FileUtil.saveImage(thumb_path,thumb);
                info.thumb_path=thumb_path;

                //往SD卡保存商品大图
                Bitmap pic=BitmapFactory.decodeResource(getResources(),info.pic);
                String pic_path=path+rowid+".jpg";
                FileUtil.saveImage(pic_path,pic);
                pic.recycle();
                info.pic_path=pic_path;
                mGoodsHelper.update(info);

            }

        }else {
            ArrayList<GoodsInfo> goodsInfos=mGoodsHelper.query("1=1");
            for(int i=0;i<goodsInfos.size();i++){
                GoodsInfo info=goodsInfos.get(i);
                Bitmap bitmap=BitmapFactory.decodeFile(info.thumb_path);
                MainApplication.getInstance().mIconMap.put(info.rowid,bitmap);
            }
        }
        ShareUtil.getInstance(this).writeShared("first","false");

    }

}


















