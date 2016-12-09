package com.joyue.tech.gankio.mvp.category;

import com.joyue.tech.core.mvp.BasePresenter;
import com.joyue.tech.core.mvp.BaseView;
import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */
public class CategoryContract {

    public interface View extends BaseView<Presenter> {
        //加载新数据
        void newDatas(List<Result> newsList);

        //添加更多数据
        void addDatas(List<Result> addList);

        //显示已加载所有数据
        void showLoadCompleteAllData();
    }

    public interface Presenter extends BasePresenter {
        void data(String category, int count, int page, boolean isLoadMore);
    }

    public interface Model {
        void data(String category, int count, int page, final OnLoadDataListListener listener);
    }
}