package com.joyue.tech.gankio.mvp.category;

import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */
public class CategoryPresenterImpl implements CategoryContract.Presenter, OnLoadDataListListener<List<Result>> {
    private CategoryContract.View mView;
    private CategoryContract.Model mModel;
    private boolean isLoadMore;

    public CategoryPresenterImpl(CategoryContract.View mView) {
        this.mView = mView;
        this.mModel = new CategoryModelImpl();
        mView.showProgress();
    }


    @Override
    public void data(String category, int count, int page, boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        mModel.data(category, count, page, this);
    }

    @Override
    public void onSuccess(List<Result> data) {
        if (isLoadMore) {
            if (data.size() == 0) {
                mView.showLoadCompleteAllData();
            } else {
                //新增自动加载的的数据
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