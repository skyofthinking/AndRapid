package com.joyue.tech.core.mvp;

/**
 * @author JiangYH
 */
public interface BaseView<P> {
    // 显示加载页
    void showProgress();

    // 关闭加载页
    void hideProgress();

    // 显示加载失败
    void showLoadFailMsg();

    // 显示无数据
    void showNoData();

    // 设置Presenter
    void setPresenter(P presenter);
}
