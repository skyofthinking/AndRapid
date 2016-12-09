package com.joyue.tech.core.http.interceptor;

import com.joyue.tech.core.utils.TLog;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author JiangYH
 * @desc 请求的时候插入的头部信息
 */
public class HeaderInterceptor implements Interceptor {

    private String TAG = "HeaderInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder();
        builder.header("Accept-Language", Locale.getDefault().toString());
        builder.header("Connection", "Keep-Alive");
        builder.header("User-Agent", getUserAgent());
        Request request = builder.build();
        TLog.d(TAG, "访问网络地址: " + request.url().url());

        return chain.proceed(request);
    }

    public static String getUserAgent() {
        return new StringBuilder("APP应用")
                // .append('/' + AppManager.getPackageInfo().versionName + '_' + AppManager.getPackageInfo().versionCode)// APP版本信息
                .append("/Android")// 手机系统平台
                .append("/" + android.os.Build.VERSION.RELEASE)// 手机系统版本
                .append("/" + android.os.Build.MODEL) // 手机型号
                .append("/" + "WhenYouSawIt,Well!Bingo!!") // 客户端唯一标识
                .toString();
    }

}