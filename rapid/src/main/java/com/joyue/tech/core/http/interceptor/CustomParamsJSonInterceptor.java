package com.joyue.tech.core.http.interceptor;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;

import com.google.gson.Gson;
import com.joyue.tech.core.utils.TLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author JiangYH
 * @desc 传送JSON格式
 */
public class CustomParamsJSonInterceptor implements Interceptor {

    public String TAG = this.getClass().getSimpleName();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        ArrayMap<String, String> paramsMap = new ArrayMap<String, String>();
        paramsMap.put("version", "1.0");
        paramsMap.put("token", "");
        paramsMap.put("device", "Android");
        if (request.body() instanceof FormBody) {
            FormBody oldBody = (FormBody) request.body();
            for (int i = 0; i < oldBody.size(); i++) {
                paramsMap.put(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
        }
        Gson gson = new Gson();
        TLog.i(TAG, "Gson参数格式 :" + gson.toJson(paramsMap));

        RequestBody body = RequestBody.create(JSON, gson.toJson(paramsMap));
        request = request.newBuilder().post(body).build();
        return chain.proceed(request);
    }

}