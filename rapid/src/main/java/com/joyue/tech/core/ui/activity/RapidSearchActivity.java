package com.joyue.tech.core.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.SearchView;

import com.joyue.tech.core.R;

/**
 * @author JiangYH
 * @desc 搜索界面
 */
public class RapidSearchActivity extends RapidActivity {

    SearchView.SearchAutoComplete mEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
