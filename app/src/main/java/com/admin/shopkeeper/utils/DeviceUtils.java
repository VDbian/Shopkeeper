package com.admin.shopkeeper.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 设备工具箱，提供与设备硬件相关的工具方法
 */
public class DeviceUtils {

    /**
     * 获取屏幕尺寸
     */
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;

		/*if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
            return new Point(display.getWidth(), display.getHeight());
		}else{
			Point point = new Point();
			display.getSize(point);
			return point;
		}*/
    }

    public static String getimage(String srcPath, int kb) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        if (w != 0 && (h / w) <= 3) {
            //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;//这里设置高度为800f
            float ww = 800f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0) {
                be = 1;
            }
            newOpts.inSampleSize = be;//设置缩放比例
            //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        }

        newOpts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, kb);//压缩好比例大小后再进行质量压缩
    }

    public static String compressImage(Bitmap image, int kb) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        if (baos.toByteArray().length / 1024 > 2048) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 35, baos);
            options = 35;
        } else if (baos.toByteArray().length / 1024 > 1024) {
            image.compress(Bitmap.CompressFormat.JPEG, 25, baos);
            options = 50;
        }
        while (baos.toByteArray().length / 1024 > kb && options > 1) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            if (options >= 35) {
                options -= 15;// 每次都减少10
            } else if (options > 4) {
                options -= 4;
            } else {
                options = 1;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
        return saveBitmap(bitmap, System.currentTimeMillis() + ".jpg");
    }

    public static String saveBitmap(Bitmap bmp, String fileName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "shopkeeper");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        if (file.exists()) {
            return file.getAbsolutePath();
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }


}