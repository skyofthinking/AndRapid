package com.joyue.tech.core.utils;

import android.graphics.Bitmap;
import android.os.Environment;

import com.joyue.tech.core.RapidApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author JiangYH
 */
public class SDCardUtils {

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public File writeToSDFromInput(String filename, InputStream inputStream) {
        File file = null;
        OutputStream outputStream = null;
        try {
            file = createNewFile(filename);
            outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[5 * 1024];
            while ((inputStream.read(buffer)) != -1) {
                outputStream.write(buffer);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void saveBitmap(Bitmap b, String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(createNewFile(filename));
        if (null != fos) {
            b.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.flush();
            fos.close();
        }
    }

    public static boolean isAvailableSDCard() {
        // Android判断SdCard是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static boolean isWriteableSDCard() {
        boolean mExternalStorageWriteable;
        // 下面的语句是判断SDCard的权限状态
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageWriteable = false;
        }
        return mExternalStorageWriteable;
    }

    public static File createNewFile(String fileName) throws IOException {
        if (isAvailableSDCard() && isWriteableSDCard()) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            File saveFile = new File(sdCardDir, fileName);
            saveFile.createNewFile();
            return saveFile;
        }
        return null;
    }

    /**
     * @return 创建缓存目录
     */
    public static File getCacheDirectory(String name) {
        File file = new File(RapidApp.getContext().getExternalCacheDir(), name);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
