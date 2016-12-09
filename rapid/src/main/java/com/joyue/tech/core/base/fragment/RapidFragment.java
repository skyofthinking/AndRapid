package com.joyue.tech.core.base.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author JiangYH
 * @desc 基础的Fragment 所有的Fragment都要继承这个Fragment
 */
public abstract class RapidFragment extends Fragment {
    public String TAG = this.getClass().getSimpleName();

    public Context mContext;
    public FragmentActivity mActivity;
    public Resources resources;
    public View rootView;
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        
        mContext = getActivity();
        mActivity = getActivity();
        resources = mContext.getResources();
        
        unbinder = ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    public abstract int getLayoutId();

    public abstract void initView(View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
