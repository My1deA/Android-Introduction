package com.example.chapter9.widget;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter9.util.BitmapUtil;
import com.example.chapter9.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.jar.Attributes;
import java.util.zip.CRC32;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Camera2View extends TextureView {

    private static final String TAG="Camera2View";
    private Context mContext;
    private Handler mHandler;
    private HandlerThread mHandlerThread;
    private CaptureRequest.Builder mPreviewBuilder;//声明一个拍照请求构造器对象
    private CameraCaptureSession mCameraSession; // 声明一个相机拍照会话对象
    private CameraDevice mCameraDevice;//声明一个相机拍照会话对象
    private ImageReader mImageReader;//声明一个图像读取器对象
    private Size mPreViewSize;//预览画面尺寸
    private int mCameraType= CameraCharacteristics.LENS_FACING_FRONT;//摄像头类型
    private int mTakeType=0;//拍照类型

    public Camera2View(Context context) {
        this(context,null);
    }

    public Camera2View(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        mContext=context;
        mHandlerThread=new HandlerThread("camer2");
        mHandlerThread.start();
        mHandler=new Handler(mHandlerThread.getLooper());
    }

    public void open(int camera_type){
        mCameraType=camera_type;
        setSurfaceTextureListener(mSurfacetextlistener);
    }

    private SurfaceTextureListener mSurfacetextlistener=new SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamere();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            closeCarmera();
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };


    private String mPhotoPath;

    public String getPhotoPath(){
        return mPhotoPath;
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void takePicture(){
        Log.d(TAG,"正在拍照");
        mTakeType=0;
        try{
            CaptureRequest.Builder builder=mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //预览模式
            builder.addTarget(mImageReader.getSurface());
            //自动对焦
            builder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_AUTO);
            //自动曝光
            builder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            //开始对焦
            builder.set(CaptureRequest.CONTROL_AF_MODE, CameraMetadata.CONTROL_AF_TRIGGER_START);
            //设置照片的方向
            builder.set(CaptureRequest.JPEG_ORIENTATION,(mCameraType==CameraCharacteristics.LENS_FACING_FRONT)?0:180);
            //拍照回话
            mCameraSession.capture(builder.build(),null,mHandler);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<String> mShootingArray;

    public ArrayList<String> getShootingArray(){
        Log.d(TAG,"mShootingArray.size(): "+mShootingArray.size());
        return  mShootingArray;
    }

    public void startShooting(int duration){
        Log.d(TAG,"正在连拍");
        mTakeType=1;
        mShootingArray=new ArrayList<String>();
        try{
            //停止连拍
            mCameraSession.stopRepeating();
            //图像读取器添加到预览目标
            mPreviewBuilder.addTarget(mImageReader.getSurface());
            //设置连拍请求
            mCameraSession.setRepeatingRequest(mPreviewBuilder.build(),null,mHandler);
            if(duration>0){
                mHandler.postDelayed(mStop,duration);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void stopShooting(){
        try{
            mCameraSession.stopRepeating();
            mPreviewBuilder.removeTarget(mImageReader.getSurface());
            mCameraSession.setRepeatingRequest(mPreviewBuilder.build(),null,mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Toast.makeText(mContext, "已完成连拍，按返回键回到上页查看照片。", Toast.LENGTH_SHORT).show();
    }

    private Runnable mStop=new Runnable() {
        @Override
        public void run() {
            stopShooting();
        }
    };

    private void openCamere(){

        CameraManager cm=(CameraManager)mContext.getSystemService(Context.CAMERA_SERVICE);
        String cameraid=mCameraType+"";
        try{
            //获得可用相机列表
            CameraCharacteristics cc=cm.getCameraCharacteristics(cameraid);
            // 检查相机硬件的支持级别
            // CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL表示完全支持
            // CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED表示有限支持
            // CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY表示遗留的
           int level=cc.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
           StreamConfigurationMap map=cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
           Size largest= Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)),new CompareSizeArea());
           //获得预览画面的尺寸
           mPreViewSize=map.getOutputSizes(SurfaceTexture.class)[0];
           //创造一个JPEG格式的图像读取器
            mImageReader=ImageReader.newInstance(largest.getWidth(),largest.getHeight(),ImageFormat.JPEG,10);
           //设置图像读取监视器
           mImageReader.setOnImageAvailableListener(onImageAvailableListener,mHandler);
            // 开启摄像头
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            cm.openCamera(cameraid,mDeviceStateCallback,mHandler);
         } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private class CompareSizeArea implements Comparator<Size>{
        @Override
        public int compare(Size o1, Size o2) {
            return Long.signum((long)o1.getWidth()+o1.getHeight()-(long)o2.getWidth()*getHeight());
        }
    }

    private CameraDevice.StateCallback mDeviceStateCallback=new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice=camera;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected( CameraDevice camera) {
            camera.close();
            camera=null;
        }

        @Override
        public void onError( CameraDevice camera, int error) {
            camera.close();
            camera=null;
        }
    };

    private ImageReader.OnImageAvailableListener onImageAvailableListener=new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader reader) {
           Log.d(TAG,"onImageAviable");
           mHandler.post(new ImageSaver(reader.acquireLatestImage()));
        }
    };

    private class ImageSaver implements Runnable{

        private Image mImage;
        public ImageSaver(Image image){
            mImage=image;
        }

        @Override
        public void run() {
            String path=String.format("%s%s.jpg", BitmapUtil.getCachePath(mContext), DateUtil.getNowDateTime());
            Log.d(TAG, "正在保存图片 path："+path);
            BitmapUtil.saveBitmap(path,mImage.getPlanes()[0].getBuffer(),4,"JPEG",80);
            if(mImage!=null){
                mImage.close();
            }if(mTakeType==0){
                mPhotoPath=path;
            }else{
                mShootingArray.add(path);
            }
            Log.d(TAG, "完成保存图片 path："+path);

        }
    }


    private void closeCarmera(){
        if(null!=mCameraSession){
            mCameraSession.close();
            mCameraSession=null;
        }
        if(null!=mCameraDevice){
            mCameraDevice.close();
            mCameraDevice=null;
        }
        if(null!=mImageReader){
            mImageReader.close();;
            mImageReader=null;
        }
    }

    private void createCameraPreviewSession(){
        //获得纹理图
        SurfaceTexture texture=getSurfaceTexture();
        //设置纹理图默认尺寸
        texture.setDefaultBufferSize(mPreViewSize.getWidth(),mPreViewSize.getHeight());
        //创建一个表面纹理对象
        Surface surface=new Surface(texture);
        try{
            mPreviewBuilder=mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            //添加到预览目标
            mPreviewBuilder.addTarget(surface);
            //自动对焦
            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            //自动曝光
            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE,CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            //自动对焦
            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AF_TRIGGER_START);
            //设置图片的方向
            mPreviewBuilder.set(CaptureRequest.JPEG_ORIENTATION,(mCameraType==CameraCharacteristics.LENS_FACING_FRONT)?0:180);
            //创建讴歌相片捕获对象
            mCameraDevice.createCaptureSession(Arrays.asList(surface,mImageReader.getSurface()),
                    mSesionStateCallback,mHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private CameraCaptureSession.StateCallback mSesionStateCallback=new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                Log.d(TAG, "onConfigured");
                mCameraSession = session;
                // 设置连拍请求。此时预览画面只会发给手机屏幕
                mCameraSession.setRepeatingRequest(mPreviewBuilder.build(), null, mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };
}





















