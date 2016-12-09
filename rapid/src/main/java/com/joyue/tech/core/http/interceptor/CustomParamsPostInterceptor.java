package com.joyue.tech.core.http.interceptor;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author JiangYH
 */
public class CustomParamsPostInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //post请求追加参数
        FormBody.Builder newBody = new FormBody.Builder();
        newBody.add("version", "version");
        newBody.add("token", "token");
        newBody.add("device", "Android").build();
        //拦截请求中用户传入的数据，把参数遍历放入新的body里面，然后进行一块提交
        FormBody oldBody = (FormBody) request.body();
        for (int i = 0; i < oldBody.size(); i++) {
            newBody.add(oldBody.encodedName(i), oldBody.encodedValue(i));
        }
        request = request.newBuilder().post(newBody.build()).build();
        return chain.proceed(request);
    }

}