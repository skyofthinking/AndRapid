package com.joyue.tech.core.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.joyue.tech.core.R;
import com.joyue.tech.core.utils.FragmentUtils;

/**
 * 所有能点击的按钮全部跳转到这个页面
 */
public class RapidClickButtonActivity extends RapidToolbarActivity {

    FragmentManager mFragmentManager;
    public Intent intent;
    public FragmentTransaction ft;
    public String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_click_button;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        // 获取传递过来的资源id值
        intent = getIntent();

        String fragmentName = intent.getStringExtra("fragmentName");
        // 这里需要传递其他值可以自己定义
        id = intent.getStringExtra("id");

        // 根据传递过来的不同的资源id值设置不同的fragment
        mFragmentManager = getSupportFragmentManager();

        FragmentUtils.replaceFragment(mFragmentManager, R.id.frame_container, Fragment.instantiate(this, fragmentName, intent.getExtras()).getClass(), new Bundle(), false);
    }


}
