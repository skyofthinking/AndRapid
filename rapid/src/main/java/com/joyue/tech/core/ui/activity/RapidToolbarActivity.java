package com.joyue.tech.core.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * @author JiangYH
 */
public abstract class RapidToolbarActivity extends RapidActivity {
    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mToolbar = (Toolbar) findViewById(getToolbarId());
        setSupportActionBar(mToolbar);
        initToolbar();
    }

    public abstract int getToolbarId();

    public void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}