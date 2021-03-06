package com.joyue.tech.core.ui.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author JiangYH
 * @desc 基础的Fragment 所有的Fragment都要继承这个Fragment
 */
public abstract class RapidFragment extends RxFragment {
    public String TAG = this.getClass().getSimpleName();

    public Context mContext;
    public FragmentActivity mActivity;
    public Resources resources;
    public View rootView = null;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean isInitView = false;

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);

            isInitView = true;
        }

        // 缓存的RootView需要判断是否已经被加过Parent，
        // 如果有Parent需要从Parent删除，要不然会发生这个RootView已经有Pparent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        mContext = getActivity();
        mActivity = getActivity();
        resources = mContext.getResources();

        unbinder = ButterKnife.bind(this, rootView);

        if (isInitView) {
            initView(rootView);
        }

        return rootView;
    }

    public abstract int getLayoutId();

    public abstract void initView(View rootView);

    protected <T extends View> T $(View rootView, int id) {
        return (T) rootView.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
