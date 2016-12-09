package com.joyue.tech.core.mvp.listener;

public interface OnLoadDataListListener<T> {
    void onSuccess(T data);

    void onFailure(Throwable e);
}
