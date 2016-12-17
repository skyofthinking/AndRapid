package com.joyue.tech.core.utils;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author JiangYH
 */

public class ImageUtils {


    private final static String TAG = "ImageUtils";

    /**
     * 图片旋转
     *
     * @param bmp    要旋转的图片
     * @param degree 图片旋转的角度，负值为逆时针旋转，正值为顺时针旋转
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bmp, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    /**
     * 图片缩放
     *
     * @param bm
     * @param scale 值小于则为缩小，否则为放大
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bm, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);

    }

    /**
     * 图片缩放
     *
     * @param bm
     * @param w  缩小或放大成的宽
     * @param h  缩小或放大成的高
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bm, int w, int h) {
        Bitmap BitmapOrg = bm;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
    }

    /**
     * 图片反转
     *
     * @param bmp
     * @param flag 0为水平反转，1为垂直反转
     * @return
     */
    public static Bitmap reverseBitmap(Bitmap bmp, int flag) {
        float[] floats = null;
        switch (flag) {
            case 0: // 水平反转
                floats = new float[]{-1f, 0f, 0f, 0f, 1f, 0f, 0f, 0f, 1f};
                break;
            case 1: // 垂直反转
                floats = new float[]{1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};
                break;
        }
        if (floats != null) {
            Matrix matrix = new Matrix();
            matrix.setValues(floats);
            return Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        }
        return null;
    }

    /**
     * 得到图片倒影
     *
     * @param originalBitmap
     * @return
     */
    private Bitmap createReflectedImage(Bitmap originalBitmap) {
        // 图片与倒影间隔距离
        final int reflectionGap = 4;

        // 图片的宽度
        int width = originalBitmap.getWidth();
        // 图片的高度
        int height = originalBitmap.getHeight();

        Matrix matrix = new Matrix();
        // 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
        matrix.preScale(1, -1);
        // 创建反转后的图片Bitmap对象，图片高是原图的一半。
        Bitmap reflectionBitmap = Bitmap.createBitmap(originalBitmap, 0, height / 2, width, height / 2, matrix, false);
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
        Bitmap withReflectionBitmap = Bitmap.createBitmap(width, (height + height / 2 + reflectionGap), Config.ARGB_8888);

        // 构造函数传入Bitmap对象，为了在图片上画图
        Canvas canvas = new Canvas(withReflectionBitmap);
        // 画原始图片
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        // 画间隔矩形
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);

        // 画倒影图片
        canvas.drawBitmap(reflectionBitmap, 0, height + reflectionGap, null);

        // 实现倒影效果
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalBitmap.getHeight(), 0, withReflectionBitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.MIRROR);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

        // 覆盖效果
        canvas.drawRect(0, height, width, withReflectionBitmap.getHeight(), paint);

        return withReflectionBitmap;
    }

    /**
     * Android水印效果
     * 第一，载入原始图片；第二，载入水印图片；第三，保存新的图片。
     *
     * @param mark
     * @param photo
     * @return
     */
    public static Bitmap addWatermark(Bitmap mark, Bitmap photo) {
        Bitmap photoMark = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(photoMark);
        canvas.drawBitmap(photo, 0, 0, null);
        canvas.drawBitmap(mark, photo.getWidth() - mark.getWidth(), photo.getHeight() - mark.getHeight(), null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return photoMark;
    }

    public static Bitmap addWatermark(Resources res, int sourceId, int markId) {
        Bitmap mark = BitmapFactory.decodeResource(res, markId);
        Bitmap photo = BitmapFactory.decodeResource(res, sourceId);
        return addWatermark(mark, photo);
    }

    /**
     * 获得圆角图片
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 获得圆角图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        final float roundPx = 12;
        return getRoundedCornerBitmap(bitmap, roundPx);
    }

    // 放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    /**
     * 获取图片缩略图
     *
     * @param url
     */
    public Bitmap getImageThumb(String url) {
        byte[] imageByte = getImageFromURL(url.trim());
        // 以下是把图片转化为缩略图再加载
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length); // 此时返回bitmap为空
        options.inJustDecodeBounds = false;
        int be = (int) (options.outHeight / (float) 200);
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        return BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options); // 返回缩略图
    }

    /**
     * 根据图片网络地址获取图片的byte[]类型数据
     *
     * @param urlPath 图片网络地址
     * @return 图片数据
     */
    public static byte[] getImageFromURL(String urlPath) {
        byte[] data = null;
        InputStream is = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            // conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            is = conn.getInputStream();
            if (conn.getResponseCode() == 200) {
                data = readInputStream(is);
            } else {
                data = null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.disconnect();
        }
        return data;
    }

    /**
     * 读取InputStream数据，转为byte[]数据类型
     *
     * @param is InputStream数据
     * @return 返回byte[]数据
     */
    public static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = -1;
        try {
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            baos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();
        try {
            is.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 根据视频Uri地址取得指定的视频缩略图
     *
     * @param resolver
     * @param uri 本地视频Uri标示
     * @return 返回bitmap类型数据
     */
    public static Bitmap getVideoThumbnail(ContentResolver resolver, Uri uri) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Config.ARGB_8888;
        Cursor cursor = resolver.query(uri, new String[]{MediaStore.Video.Media._ID}, null, null, null);

        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        cursor.moveToFirst();
        String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));

        if (videoId == null) {
            return null;
        }
        cursor.close();
        long videoIdLong = Long.parseLong(videoId);
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(resolver, videoIdLong, Images.Thumbnails.MICRO_KIND, options);

        return bitmap;
    }

    /**
     * 目前Android SDK定义的Tag有:
     * TAG_DATETIME 时间日期
     * TAG_FLASH 闪光灯
     * TAG_GPS_LATITUDE 纬度
     * TAG_GPS_LATITUDE_REF 纬度参考
     * TAG_GPS_LONGITUDE 经度
     * TAG_GPS_LONGITUDE_REF 经度参考
     * TAG_IMAGE_LENGTH 图片长
     * TAG_IMAGE_WIDTH 图片宽
     * TAG_MAKE 设备制造商
     * TAG_MODEL 设备型号
     * TAG_ORIENTATION 方向
     * TAG_WHITE_BALANCE 白平衡
     */
    public static ExifInterface getExifInfo(String imageFilename) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imageFilename);
        } catch (Exception e) {
            TLog.e(TAG, "getExifInfo() Exception", e);
        }
        return exif;
    }
}
