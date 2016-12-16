package com.joyue.tech.gankio.ui.activity;

import android.os.Bundle;

import com.joyue.tech.core.ui.activity.RapidToolbarActivity;
import com.joyue.tech.gankio.R;

public class AboutActivity extends RapidToolbarActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

}
