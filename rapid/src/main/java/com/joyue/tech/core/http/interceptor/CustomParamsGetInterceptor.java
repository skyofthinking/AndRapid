package com.joyue.tech.core.http.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author JiangYH
 */
public class CustomParamsGetInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        // Get请求后面追加共同的参数
        HttpUrl httpUrl = request.url().newBuilder()
                .addQueryParameter("version", "version")
                .addQueryParameter("token", "token")
                .addQueryParameter("device", "Android").build();
        request = request.newBuilder().url(httpUrl).build();
        return chain.proceed(request);
    }

}