package com.example.chapter9.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;

public class BitmapUtil {

    public static void saveBitmap(String path, Bitmap bitmap,String format,int quality){
        Bitmap.CompressFormat compressFormat=Bitmap.CompressFormat.JPEG;
        if(format.toUpperCase(Locale.getDefault()).equals("PNG")){
            compressFormat=Bitmap.CompressFormat.PNG;
        }
        try{
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(compressFormat,quality,bos);
            bos.flush();
            bos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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


    public static Bitmap openBitmap(String path){
        Bitmap bitmap=null;
        try{
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(path));
            bitmap=BitmapFactory.decodeStream(bis);
            bis.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return  bitmap;
    }

    public static Bitmap getRotateBitmap(Bitmap b,float rotateDegree){
        Matrix matrix=new Matrix();
        matrix.postRotate(rotateDegree);

        return Bitmap.createBitmap(b,0,0,b.getWidth(),b.getHeight(),matrix,false);
    }

    public static String getCachePath(Context context){
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString()+"/";
    }

    public static Bitmap zoomImage(Bitmap origImage,double newWidth,double newHeight){
        float width=origImage.getWidth();
        float height=origImage.getHeight();
        Matrix matrix=new Matrix();
        float scaleWidth = ((float) newWidth) / width;
        // 计算高度的缩放率
        float scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth,scaleHeight);
        return Bitmap.createBitmap(origImage,0,0,(int)width,(int)height,matrix,true);
    }

    public static void setPictureDegreeZero(String path){
        try{
            ExifInterface exifInterface=new ExifInterface(path);
            // 修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
            // 例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
            exifInterface.saveAttributes();

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
