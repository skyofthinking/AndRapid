package com.joyue.tech.gankio.mvp.history;

import com.joyue.tech.core.mvp.BasePresenter;
import com.joyue.tech.core.mvp.BaseView;
import com.joyue.tech.core.mvp.listener.OnLoadDataListListener;

import java.util.List;

/**
 * @author JiangYH
 */
public class HistoryContract {

    public interface View extends BaseView<Presenter> {
        void newDatas(List<String> newList);

        //添加更多数据
        void addDatas(List<String> addList);

        //显示已加载所有数据
        void showLoadCompleteAllData();
    }

    public interface Presenter extends BasePresenter {
        void history();
    }

    public interface Model {
        void history(final OnLoadDataListListener listener);
    }
}