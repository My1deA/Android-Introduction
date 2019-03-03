package com.example.chapter4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ContextThemeWrapper;

import com.example.chapter4.bean.CartInfo;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CartDBHelper extends SQLiteOpenHelper {

    private static final String TAG="CartDBHelper";
    private static final String DB_NAME="cart.db";
    private static final int DB_VERSION=1;
    private static CartDBHelper mHelper=null;
    private SQLiteDatabase mdb=null;
    private static final String TABLE_NAME="cart_info";

    private CartDBHelper(Context context){
        super(context, TABLE_NAME,null,DB_VERSION);
    }

    private CartDBHelper(Context context,int version){
        super(context,TABLE_NAME,null,version);
    }

    public static CartDBHelper getInstance(Context context,int version){
        if(version>0&&mHelper==null){
            mHelper=new CartDBHelper(context,version);
        }else{
            mHelper=new CartDBHelper(context);
        }
        return  mHelper;
    }

    public SQLiteDatabase openReadLink(){
        if(mdb==null&&!mdb.isOpen()){
            mdb=mHelper.getReadableDatabase();
        }
        return  mdb;
    }

    public SQLiteDatabase openWriteLink(){
        if(mdb==null&&!mdb.isOpen()){
            mdb=mHelper.getWritableDatabase();
        }
        return mdb;
    }

    public void closeLink(){
        if(mdb!=null&&mdb.isOpen()){
            mdb.close();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate");
        String drop_sql="DROP TABLE IF EXISTS "+TABLE_NAME+";";
        Log.d(TAG,"drop_sql:"+drop_sql);
        db.execSQL(drop_sql);

        String create_sql="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                +"goods_id LONG NOT NULL,"+"count INTEGER NOT NULL,"
                +"update_time VARCHAR NOT NULL"
                +");";
        Log.d(TAG,"create_sql:"+create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int delete(String condition){
        return  mdb.delete(TABLE_NAME,condition,null);
    }

    public int deleteAll(){
        return mdb.delete(TABLE_NAME,"1=1",null);
    }

    public long insert(CartInfo info){
        ArrayList<CartInfo> cartInfos=new ArrayList<CartInfo>();
        cartInfos.add(info);
        return  insert(cartInfos);

    }

    public long insert(ArrayList<CartInfo> cartInfos){
        long result=-1;
        for(CartInfo info:cartInfos){
            Log.d(TAG,"goods_id="+info.goods_id+",count="+info.count);
            if(info.rowid>0){
                String condtion=String.format("rowid='%d",info.rowid);
                update(info,condtion);
                result=info.rowid;
                continue;
            }

            ContentValues cv=new ContentValues();
            cv.put("goods_id",info.goods_id);
            cv.put("count",info.count);
            cv.put("update_time",info.update_time);
            result=mdb.insert(TABLE_NAME,null,cv);
            if(result==-1){
                return result;
            }
        }
        return  result;
    }

    public int update(CartInfo info){
        return update(info,"rowid="+info.rowid);
    }

    public int update(CartInfo cartInfo,String condtion){
        ContentValues cv=new ContentValues();
        cv.put("goods_id",cartInfo.goods_id);
        cv.put("count",cartInfo.count);
        cv.put("update_time",cartInfo.update_time);
        return mdb.update(TABLE_NAME,cv,condtion,null);
    }

    public ArrayList<CartInfo> query(String condition){
        String sql=String.format("select rowid,_id,goods_id,count,update_time"+
                " from %s where %s",TABLE_NAME,condition);
        Log.d(TAG,"query_sql:"+sql);
        ArrayList<CartInfo> cartInfos=new ArrayList<CartInfo>();
        Cursor cursor=mdb.rawQuery(sql,null);
        while(cursor.moveToNext()){
            CartInfo cartInfo=new CartInfo();
            cartInfo.rowid=cursor.getLong(0);
            cartInfo.xuhao=cursor.getInt(1);
            cartInfo.goods_id=cursor.getLong(2);
            cartInfo.count=cursor.getInt(3);
            cartInfo.update_time=cursor.getString(4);
            cartInfos.add(cartInfo);
        }
        cursor.close();
        return cartInfos;

    }

    public CartInfo queryById(long rowid){
        CartInfo cartInfo=null;
        ArrayList<CartInfo>cartInfos=new ArrayList<CartInfo>();
        cartInfos=query(String.format("rowid = '%d'",rowid));
        if(cartInfos.size()>0){
            cartInfo=cartInfos.get(0);
        }
        return cartInfo;
    }

    public CartInfo queryByGoodsId(long goods_id){
        CartInfo info=null;
        ArrayList<CartInfo> cartInfos=new ArrayList<CartInfo>();
        cartInfos=query(String.format(" goods_id = '%d'",goods_id));
        if(cartInfos.size()>0){
            info=cartInfos.get(0);
        }
        return  info;
    }

}













