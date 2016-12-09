package com.joyue.tech.core.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.joyue.tech.core.RapidApp;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @desc Retrofit ApiRequest 网络请求
 * @author JiangYH
 */
public class APIRequest {
    // Sington
    private static APIRequest instance;
    // Retrofit
    private Retrofit retrofit;

    private APIRequest() {
        HttpConst httpConst = RapidApp.getHttpConst();

        // 打印请求响应信息
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(RapidApp.getContext().getCacheDir(), cacheSize);

        // Cookie 持久化
        // CookieJar cookieJar = CookieManager.getInstance(context)

        // 必须登录请求后
        CookieJar cookieJar = new JavaNetCookieJar(cookieManager);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(logging)
                // .authenticator(new TokenAuthenticator())
                // .addNetworkInterceptor(new TokenInterceptor())
                .cache(cache)
                .connectTimeout(httpConst.getConnectTimeout(), TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(httpConst.getReadTimeout(), TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(httpConst.getWriteTimeout(), TimeUnit.SECONDS) // 设置写入超时时间
                .build();

        Gson mGson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new JsonDeserializer<List<?>>() {
            // 处理不规范的返回List的信息
            @Override
            public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (json.isJsonArray()) {
                    JsonArray array = json.getAsJsonArray();
                    Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                    List list = new ArrayList<>();
                    for (int i = 0; i < array.size(); i++) {
                        JsonElement element = array.get(i);
                        if (element.isJsonObject()) {
                            Object item = context.deserialize(element, itemType);
                            list.add(item);
                        } else {
                            return new ArrayList();
                        }
                    }
                    return list;
                } else {
                    //和接口类型不符，返回空List
                    return new ArrayList();
                }
            }
        }).setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        retrofit = new Retrofit.Builder()
                .baseUrl(httpConst.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .client(client)
                .build();
    }

    public static APIRequest getInstance() {
        if (instance == null) {
            instance = new APIRequest();
        }
        return instance;
    }

    public <T> T create(Class<T> service) {
        return retrofit.create(service);
    }
}