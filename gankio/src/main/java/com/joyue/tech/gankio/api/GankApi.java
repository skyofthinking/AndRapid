package com.joyue.tech.gankio.api;

import com.joyue.tech.core.http.APIRequest;
import com.joyue.tech.core.utils.FileUtil;
import com.joyue.tech.gankio.api.cache.CacheProviders;
import com.joyue.tech.gankio.domain.Result;

import java.io.File;
import java.util.List;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictDynamicKey;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import rx.Observable;
import rx.Observer;

/**
 * @author JiangYH
 */

public class GankApi extends BaseApi {

    public static File cacheDirectory = FileUtil.getcacheDirectory();
    public static final CacheProviders providers = new RxCache.Builder().persistence(cacheDirectory, new GsonSpeaker()).using(CacheProviders.class);
    private static final GankApi instance = new GankApi();
    private static final ApiService service = APIRequest.getInstance().create(ApiService.class);

    public static GankApi getInstance() {
        return instance;
    }

    public void data(String category, int count, int page, Observer<List<Result>> observer) {
        Observable observable = service.date(category, count, page).map(new GankApi.HttpResultFunc<List<Result>>());
        Observable observableCahce = providers.data(observable, new DynamicKey(category + count + page), new EvictDynamicKey(false)).map(new BaseApi.HttpResultFuncRxCache<List<Result>>());
        setSubscribe(observableCahce, observer);
    }
}
