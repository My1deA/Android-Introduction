package com.example.chapter10.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Locale;

public class BitmapUtil {

    //位图对象保存为指定路径的图片文件
    public static void saveBitmap(String path, Bitmap bitmap,String format,int quality){
        Bitmap.CompressFormat compressFormat=Bitmap.CompressFormat.JPEG;
        if(format.toUpperCase(Locale.getDefault()).equals("PNG")){
            compressFormat=Bitmap.CompressFormat.PNG;
        }

        try{
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(path));
            //位图压缩
            bitmap.compress(compressFormat,quality,bos);
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //把位图数据保存到指定路径的图片文件
    public static void saveBitmap(String path, ByteBuffer buffer,int sample_size,String format,int quality){
        try{
            byte[] buff=new byte[buffer.remaining()];
            buffer.get(buff);
            BitmapFactory.Options ontain=new BitmapFactory.Options();
            ontain.inSampleSize=sample_size;
            Bitmap bitmap=BitmapFactory.decodeByteArray(buff,0,buff.length,ontain);
            saveBitmap(path,bitmap,format,quality);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //从指定路径的图片文件中读取位图数据
    public static Bitmap openBitmap(String path){
        Bitmap bitmap=null;
        try{
            //指定文件路径构建缓存输入流对象
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(path));
            bitmap=BitmapFactory.decodeStream(bis);
            bis.close();


        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getCachePath(Context context){
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()+"/";
    }

    public static Bitmap zoomImage(Bitmap origImage,double newWidth,double newHeight){
        float width=origImage.getWidth();
        float height=origImage.getHeight();
        Matrix matrix=new Matrix();
        float scaleWidth=((float)newWidth)/width;
        float scaleHeight=((float)newHeight)/height;
        matrix.postScale(scaleWidth,scaleWidth);
        return Bitmap.createBitmap(origImage,0,0,(int)width,(int)height,matrix,true);
    }

}
