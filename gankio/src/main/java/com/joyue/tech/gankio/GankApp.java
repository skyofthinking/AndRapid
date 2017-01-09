package com.joyue.tech.gankio;

import com.joyue.tech.core.RapidApp;
import com.joyue.tech.gankio.constants.Constant;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.weavey.loading.lib.LoadingLayout;

/**
 * @author JiangYH
 */

public class GankApp extends RapidApp {

    public void initHttp() {
        mHttpConst.setBaseUrl("http://gank.io/");
        mHttpConst.setConnectTimeout(30);
        mHttpConst.setReadTimeout(30);
        mHttpConst.setWriteTimeout(30);
    }


    public void initLoading() {
        LoadingLayout.getConfig()
                .setErrorText(Constant.ERROR_TITLE)
                .setEmptyText(Constant.EMPTY_TITLE)
                .setNoNetworkText(Constant.EMPTY_CONTEXT)
                .setErrorImage(R.mipmap.loading_error)
                .setEmptyImage(R.mipmap.loading_empty)
                .setNoNetworkImage(R.mipmap.loading_no_network)
                .setAllTipTextColor(R.color.gray)
                .setAllTipTextSize(14)
                .setReloadButtonText(Constant.ERROR_BUTTON)
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.gray)
                .setReloadButtonWidthAndHeight(150, 40)
                .setLoadingPageLayout(R.layout.view_loading);
    }

    @Override
    public void initDB() {
        // FlowManager.init(new FlowConfig.Builder(this).build());
        FlowManager.init(new FlowConfig.Builder(this).openDatabasesOnInit(true).build());

    }
}
