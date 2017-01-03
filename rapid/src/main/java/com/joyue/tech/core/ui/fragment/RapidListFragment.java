package com.joyue.tech.core.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joyue.tech.core.R;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.weavey.loading.lib.LoadingLayout;

public abstract class RapidListFragment extends RapidFragment implements SpringView.OnFreshListener {

    public RecyclerView mRecyclerView;
    public SpringView springView;
    public LoadingLayout loadinglayout;

    public BaseQuickAdapter mQuickAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_rapid_list;
    }

    @Override
    public void initView(View rootView) {
        mRecyclerView = $(rootView, R.id.rv_list);
        springView = $(rootView, R.id.springview);
        loadinglayout = $(rootView, R.id.loadinglayout);

        // 设置下拉刷新监听
        springView.setListener(this);
        // 设置下拉刷新样式
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(mContext));
        // springView.setFooter(new RotationFooter(this)); mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用

        mRecyclerView.setLayoutManager(getLayoutManager());
        // 如果Item高度固定 增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);

        // 设置适配器
        mQuickAdapter = getQuickAdapter();
        // 设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        // 设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(false);
        // 将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mQuickAdapter);

        // 设置页面为加载中
        loadinglayout.setStatus(LoadingLayout.Loading);
    }

    public abstract BaseQuickAdapter getQuickAdapter();

    public abstract LayoutManager getLayoutManager();

}
