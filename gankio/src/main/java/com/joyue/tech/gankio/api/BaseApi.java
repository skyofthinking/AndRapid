package com.joyue.tech.gankio.api;


import com.joyue.tech.gankio.domain.BaseResp;

import io.rx_cache.Reply;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author JiangYH
 */
public class BaseApi {

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<BaseResp<T>, T> {
        @Override
        public T call(BaseResp<T> baseResp) {
            //if (BaseConstant.Http.SUCCESS.equals(baseResp.getCode())) {
            //    throw new ApiException(baseResp);
            //}

            if (baseResp.getError() != "1") {
            }
            return baseResp.getResults();
        }
    }

    /**
     * 插入观察者
     */
    public static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(observer);
    }

    /**
     * 用来统一处理RxCache的结果
     */
    public class HttpResultFuncRxCache<T> implements Func1<Reply<T>, T> {
        @Override
        public T call(Reply<T> httpResult) {
            return httpResult.getData();
        }
    }
}
