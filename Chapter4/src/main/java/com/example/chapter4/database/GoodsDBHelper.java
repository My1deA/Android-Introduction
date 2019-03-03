package com.example.chapter4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chapter4.bean.GoodsInfo;

import java.util.ArrayList;

public class GoodsDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "GoodsDBHelper";
    private static final String DB_NAME = "good.db";
    private static final int DB_VERSION = 1;
    private static GoodsDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private static final String TABLE_NAME = "goods_info";

    private GoodsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private GoodsDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static GoodsDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new GoodsDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new GoodsDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeLink(){
        if (mDB != null || mDB.isOpen()) {
            mDB.close();
        }

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        Log.d(TAG, "drop_sql" + drop_sql);
        db.execSQL(drop_sql);

        String create_sql = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME
                + "("
                + "_id INTRGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "name VARCHAR NOT NULL," + "desc VARCHAR NOT NULL,"
                + "price FLOAT NOT NULL," + "thumb_path VARCHAR NOT NULL,"
                + "pic_path VARCHAR NOT NULL"
                + ");";
        Log.d(TAG, "create_sql: " + create_sql);
        db.execSQL(create_sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public int delete(String condition) {
        return mDB.delete(TABLE_NAME, condition, null);
    }

    public int deleteAll() {
        return mDB.delete(TABLE_NAME, "1=1", null);
    }


    public int update(GoodsInfo info) {
        return update(info, "rowid=" + info.rowid);
    }

    public int update(GoodsInfo info, String condition) {
        ContentValues cv = new ContentValues();
        cv.put("name", info.name);
        cv.put("desc", info.desc);
        cv.put("price", info.price);
        cv.put("thumb_path", info.thumb_path);
        cv.put("pic_path", info.pic_path);
        return mDB.update(TABLE_NAME, cv, condition, null);
    }

    public GoodsInfo queryById(long rowid) {
        GoodsInfo info = null;
        ArrayList<GoodsInfo> infoArrayList = query(String.format("rowid='%d'", rowid));
        if (infoArrayList.size() > 0) {
            info = infoArrayList.get(0);
        }
        return info;
    }

    public ArrayList<GoodsInfo> query(String conditon) {
        String sql = String.format("select rowid,_id,name,desc,price,thumb_path,pic_path" +
                " form %s where %s;", TABLE_NAME, conditon);
        Log.d(TAG, "query_sql" + sql);
        ArrayList<GoodsInfo> infoArray = new ArrayList<GoodsInfo>();
        Cursor cursor = mDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.rowid = cursor.getLong(0);
            info.xuhao = cursor.getInt(1);
            info.name = cursor.getString(2);
            info.desc = cursor.getString(3);
            info.price = cursor.getFloat(4);
            info.thumb_path = cursor.getString(5);
            info.pic_path = cursor.getString(6);
            infoArray.add(info);
        }
        cursor.close();
        return infoArray;
    }


    public long insert(GoodsInfo info) {
        ArrayList<GoodsInfo> infoArrayList = new ArrayList<GoodsInfo>();
        infoArrayList.add(info);
        return insert(infoArrayList);
    }

    public long insert(ArrayList<GoodsInfo> infoArrayList) {
        long result = -1;
        for (GoodsInfo info : infoArrayList) {
            if (info.rowid > 0) {
                String condition = String.format("rowid='%d'", info.rowid);
                update(info, condition);
                result=info.rowid;
                continue;
            }
            ContentValues cv = new ContentValues();
            cv.put("name", info.name);
            cv.put("desc", info.desc);
            cv.put("price", info.price);
            cv.put("thumb_path", info.thumb_path);
            cv.put("pic_path", info.pic_path);
            result=mDB.insert(TABLE_NAME,"",cv);
            if(result==-1){
                return  result;
            }
        }
        return result;

    }


}





















