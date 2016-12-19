package com.joyue.tech.gankio.mvp.ganhuo;

import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */
public class GanhuoPresenter implements GanhuoContract.Presenter, OnLoadDataListListener<List<Result>> {
    private GanhuoContract.View mView;
    private GanhuoContract.Model mModel;
    private boolean isLoadMore;

    public GanhuoPresenter(GanhuoContract.View mView) {
        this.mView = mView;
        this.mModel = new GanhuoModel();
        mView.showProgress();
    }

    @Override
    public void data(String category, int count, int page, boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        mModel.data(category, count, page, this);
    }

    @Override
    public void day(String year, String month, String day) {
        this.isLoadMore = false;
        mModel.day(year, month, day, this);
    }

    @Override
    public void onSuccess(List<Result> data) {
        if (isLoadMore) {
            if (data.size() == 0) {
                mView.showLoadCompleteAllData();
            } else {
                // 新增自动加载的的数据
                mView.addDatas(data);
            }
        } else {
            if (data.size() == 0) {
                mView.showNoData();
            } else {
                mView.newDatas(data);
            }
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