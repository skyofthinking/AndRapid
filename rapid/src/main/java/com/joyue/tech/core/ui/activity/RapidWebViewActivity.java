package com.joyue.tech.core.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.joyue.tech.core.R;
import com.joyue.tech.core.constant.BaseConstant;
import com.joyue.tech.core.widget.RapidWebView;
import com.weavey.loading.lib.LoadingLayout;

/**
 * @author JiangYH
 */
public class RapidWebViewActivity extends RapidToolbarActivity {

    WebView mWebView;
    WebSettings mSettings;
    RapidWebView mRapidWebView;
    LoadingLayout loadinglayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        Intent mIntent = getIntent();
        String url = mIntent.getStringExtra(BaseConstant.IntentConst.URL);

        mWebView = $(R.id.wv_rapid);
        loadinglayout = $(R.id.loadinglayout);

        mSettings = mWebView.getSettings();

        mSettings.setJavaScriptEnabled(true); // 支持js
        mSettings.setUseWideViewPort(false); // 将图片调整到适合webview的大小
        mSettings.setSupportZoom(true); // 支持缩放
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 支持内容重新布局
        mSettings.supportMultipleWindows(); // 多窗口
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 关闭webview中缓存
        mSettings.setAllowFileAccess(true); // 设置可以访问文件
        mSettings.setNeedInitialFocus(true); // 当webview调用requestFocus时为webview设置节点
        mSettings.setBuiltInZoomControls(true); // 设置支持缩放
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        mSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片

        // 加载URL
        mWebView.loadUrl(url);
        // 加载URL
        mRapidWebView = new RapidWebView(this, mWebView, loadinglayout);
        mWebView.setWebViewClient(mRapidWebView);
    }
}
