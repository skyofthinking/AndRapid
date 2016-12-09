package com.joyue.tech.gankio.mvp.category;

import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.api.GankApi;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

import rx.Observer;

public class CategoryModelImpl implements CategoryContract.Model {

    public void data(String category, int count, int page, final OnLoadDataListListener listener) {
        GankApi.getInstance().data(category, count, page, new Observer<List<Result>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                //设置页面为加载错误
                listener.onFailure(e);
            }

            @Override
            public void onNext(List<Result> data) {
                listener.onSuccess(data);
            }
        });
    }

}