package com.joyue.tech.core.ui.fragment;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joyue.tech.core.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

/**
 * @author JiangYH
 */
public abstract class RapidTabFragment extends RapidFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_smart_tab;
    }

    public abstract FragmentPagerItems onSetupTabs();

    @Override
    public void initView(View rootView) {
        ViewGroup mTab = (ViewGroup) rootView.findViewById(R.id.tab);
        ViewPager mViewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mTab.addView(LayoutInflater.from(mContext).inflate(R.layout.tab_top_layout, mTab, false));

        FragmentPagerItems mPages = onSetupTabs();

        FragmentPagerItemAdapter mAdapter = new FragmentPagerItemAdapter(mActivity.getSupportFragmentManager(), mPages);
        SmartTabLayout viewPagerTab = (SmartTabLayout) rootView.findViewById(R.id.viewpagertab);
        mViewpager.setAdapter(mAdapter);
        viewPagerTab.setViewPager(mViewpager);
    }

}
