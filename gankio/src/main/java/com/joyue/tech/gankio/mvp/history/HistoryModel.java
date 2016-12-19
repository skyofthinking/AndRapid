package com.joyue.tech.gankio.mvp.history;

import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.api.GankApi;

import rx.Observer;

public class HistoryModel implements HistoryContract.Model {

    @Override
    public void history(OnLoadDataListListener listener) {
        GankApi.getInstance().history(new Observer<String[]>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                //设置页面为加载错误
                listener.onFailure(e);
            }

            @Override
            public void onNext(String[] data) {
                listener.onSuccess(data);
            }
        });
    }

}