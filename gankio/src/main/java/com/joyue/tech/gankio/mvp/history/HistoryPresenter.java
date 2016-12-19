package com.joyue.tech.gankio.mvp.history;

import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JiangYH
 */
public class HistoryPresenter implements HistoryContract.Presenter, OnLoadDataListListener<String[]> {
    private HistoryContract.View mView;
    private HistoryContract.Model mModel;

    public HistoryPresenter(HistoryContract.View mView) {
        this.mView = mView;
        this.mModel = new HistoryModel();
        mView.showProgress();
    }

    @Override
    public void history() {
        mModel.history(this);
    }

    @Override
    public void onSuccess(String[] data) {
        if (data.length == 0) {
            mView.showNoData();
        } else {
            //新增自动加载的的数据
            List newList = new ArrayList();
            for (String day : data) {
                newList.add(day);
            }
            mView.newDatas(newList);
        }

        mView.hideProgress();
    }

    @Override
    public void onFailure(Throwable e) {
        mView.showLoadFailMsg();
    }

    @Override
    public void start() {

    }

}