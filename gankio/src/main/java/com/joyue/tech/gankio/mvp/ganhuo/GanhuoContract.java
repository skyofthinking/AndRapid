package com.joyue.tech.gankio.mvp.ganhuo;

import com.joyue.tech.core.mvp.BasePresenter;
import com.joyue.tech.core.mvp.BaseView;
import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */
public class GanhuoContract {

    public interface View extends BaseView<Presenter> {
        //加载新数据
        void newDatas(List<Result> newList);

        //添加更多数据
        void addDatas(List<Result> addList);

        //显示已加载所有数据
        void showLoadCompleteAllData();
    }

    public interface Presenter extends BasePresenter {
        void data(String category, int count, int page, boolean isLoadMore);

        void day(String year, String month, String day);
    }

    public interface Model {
        void data(String category, int count, int page, final OnLoadDataListListener listener);

        void day(String year, String month, String day, final OnLoadDataListListener listener);

    }
}