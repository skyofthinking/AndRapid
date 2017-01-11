package com.joyue.tech.core.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @author JiangYH
 */

public class RecyclerViewUtils {

    public static void initRecyclerView(RecyclerView mRecyclerView, Context mContext, LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        // 解决滑动不流畅问题
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
    }

    public static void initRecyclerViewLinearLayout(RecyclerView mRecyclerView, Context mContext) {
        initRecyclerView(mRecyclerView, mContext, new LinearLayoutManager(mContext));
    }

    public static void initRecyclerViewGridLayout(RecyclerView mRecyclerView, Context mContext, int spanCount) {
        initRecyclerView(mRecyclerView, mContext, new GridLayoutManager(mContext, spanCount));
    }

    public static void initRecyclerViewAdapter(BaseQuickAdapter mQuickAdapter) {
        // 设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        // 设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(false);
    }

    public static void initRecyclerViewAdapter(BaseQuickAdapter mQuickAdapter, boolean loadMore) {
        // 设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        // 设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(loadMore);
    }
}
