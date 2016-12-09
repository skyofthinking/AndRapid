package com.joyue.tech.core.http;

import com.joyue.tech.core.RapidApp;
import com.joyue.tech.core.http.interceptor.CustomParamsPostInterceptor;
import com.joyue.tech.core.utils.NetWorkUtils;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author JiangYH
 */
public class OkHttpUtils {

    private static OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {
        if (null == mOkHttpClient) {
            initOkHttp();
        }
        return mOkHttpClient;
    }

    /**
     * OkHttp的初始化，设置缓存目录，设置请求最大时间，请求失败重连
     */
    public static void initOkHttp() {
        HttpConst httpConst = RapidApp.getHttpConst();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        CustomParamsPostInterceptor paramsInterceptor = new CustomParamsPostInterceptor();
        builder.addInterceptor(paramsInterceptor);
        if (RapidApp.HTTP_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(RapidApp.getContext().getCacheDir(), "/NetCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetWorkUtils.isNetConnected(RapidApp.getContext())) {
                    request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                }
                Response response = chain.proceed(request);
                if (NetWorkUtils.isNetConnected(RapidApp.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
                }
                return response;
            }
        };

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        // Cookie 持久化
        // CookieJar cookieJar = CookieManager.getInstance(context)
        // 必须登录请求后
        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);
        builder.cookieJar(cookieJar);

        builder.cache(cache).addInterceptor(cacheInterceptor);
        // 设置超时
        builder.connectTimeout(httpConst.getConnectTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(httpConst.getReadTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(httpConst.getWriteTimeout(), TimeUnit.SECONDS);
        // 错误重连
        builder.retryOnConnectionFailure(true);

        // builder.authenticator(new TokenAuthenticator());
        // builder.addNetworkInterceptor(new TokenInterceptor());

        mOkHttpClient = builder.build();
    }
}
