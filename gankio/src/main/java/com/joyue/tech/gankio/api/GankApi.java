package com.joyue.tech.gankio.api;

import com.joyue.tech.core.http.APIRequest;
import com.joyue.tech.core.utils.FileUtils;
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

    public static File rxCacheDir = FileUtils.getRxCacheDir();
    public static final CacheProviders providers = new RxCache.Builder().persistence(rxCacheDir, new GsonSpeaker()).using(CacheProviders.class);
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

    public void history(Observer<String[]> observer) {
        Observable observable = service.history().map(new GankApi.HttpResultFunc<String[]>());
        Observable observableCahce = providers.history(observable, new DynamicKey("history"), new EvictDynamicKey(false)).map(new BaseApi.HttpResultFuncRxCache<String[]>());
        setSubscribe(observableCahce, observer);
    }

    public void day(String year, String month, String day, Observer<Result> observer) {
        Observable observable = service.day(year, month, day).map(new GankApi.HttpResultFunc<Result>());
        Observable observableCahce = providers.day(observable, new DynamicKey("day" + year + month + day), new EvictDynamicKey(false)).map(new BaseApi.HttpResultFuncRxCache<Result>());
        setSubscribe(observableCahce, observer);
    }
}
