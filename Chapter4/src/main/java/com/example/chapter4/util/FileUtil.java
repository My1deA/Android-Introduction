package com.example.chapter4.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Locale;

public class FileUtil {

    public static void saveText(String path,String txt){
        try{
            FileOutputStream fos=new FileOutputStream(path);
            fos.write(txt.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String openText(String path){
        String readStr="";
        try{
            FileInputStream fis=new FileInputStream(path);
            byte[] b=new byte[fis.available()];
            fis.read(b);
            readStr=new String(b);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return readStr;
    }

    public static void saveImage(String path, Bitmap bitmap){
        try{
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(path));
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,bos);
            bos.flush();
            bos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Bitmap openImage(String path){
        Bitmap bitmap=null;
        try{
            BufferedInputStream bis=new BufferedInputStream(new FileInputStream(path));
            bitmap= BitmapFactory.decodeStream(bis);
            bis.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static ArrayList<File> getFileList(String path,String[] extendArrat){
        ArrayList<File> displayedContent=new ArrayList<File>();
        File[] files=null;
        File directory=new File(path);
        if(extendArrat!=null&&extendArrat.length>0){
            FilenameFilter filter=getTypeFilter(extendArrat);
            files=directory.listFiles(filter);
        }else{
            files=directory.listFiles();
        }

        if(files!=null){
            for(File f:files){
                if(!f.isDirectory()&&!f.isHidden()){
                    displayedContent.add(f);
                }
            }
        }
        return displayedContent;
    }

    private static FilenameFilter getTypeFilter(String[] extendArrat) {
        final ArrayList<String> fileExtensions=new ArrayList<String>();
        for(int i=0;i<extendArrat.length;i++){
            fileExtensions.add(extendArrat[i]);
        }
        FilenameFilter filenameFilter=new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                boolean matched=false;
                File f=new File(String.format("%s%s",dir.getAbsolutePath(),name));
                matched=f.isDirectory();
                if(!matched){
                    for(String s:fileExtensions){
                        s = String.format(".{0,}\\%s$", s);
                        s=s.toUpperCase(Locale.getDefault());
                        name=name.toUpperCase(Locale.getDefault());
                        matched=name.matches(s);
                        if(matched){
                            break;
                        }
                    }
                }

                return matched;
            }
        };
        return filenameFilter;

    }


}



















