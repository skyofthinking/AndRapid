package com.joyue.tech.core.base.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
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
public abstract class RapidTabActivity extends RapidActivity {
    Toolbar mToolbar;
    ViewGroup mTab;
    ViewPager mViewpager;
    protected FragmentPagerItemAdapter mAdapter;
    protected FragmentPagerItems mPages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPages = new FragmentPagerItems(this);
        onSetupTabs();
        afterSetupTabs();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_smart_tab;
    }

    public abstract void onSetupTabs();

    public void afterSetupTabs() {
        mAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), mPages);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        mViewpager.setAdapter(mAdapter);
        viewPagerTab.setViewPager(mViewpager);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTab = (ViewGroup) findViewById(R.id.tab);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);

        mTab.addView(LayoutInflater.from(this).inflate(R.layout.tab_top_layout, mTab, false));
    }

}
