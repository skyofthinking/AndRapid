package com.joyue.tech.gankio.mvp.ganhuo;

import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.api.GankApi;
import com.joyue.tech.gankio.domain.Result;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

public class GanhuoModel implements GanhuoContract.Model {

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
                for (Result result : data) {
                    if ("福利".equals(result.getType())) {
                        result.setItemType(Result.IMG);
                    } else {
                        result.setItemType(Result.TEXT);
                    }
                }
                listener.onSuccess(data);
            }
        });
    }

    @Override
    public void day(String year, String month, String day, OnLoadDataListListener listener) {
        GankApi.getInstance().day(year, month, day, new Observer<Result>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                //设置页面为加载错误
                listener.onFailure(e);
            }

            @Override
            public void onNext(Result data) {
                List newList = new ArrayList();
                newList.add(data);
                listener.onSuccess(newList);
            }
        });
    }

}