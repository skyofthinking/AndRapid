package com.joyue.tech.core.ui.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.joyue.tech.core.R;
import com.joyue.tech.core.widget.RapidWebView;

/**
 * @author JiangYH
 */
public class RapidWebViewActivity extends RapidToolbarActivity {

    WebView mWebView;
    WebSettings mSettings;
    RapidWebView mRapidWebView;

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

        mWebView = $(R.id.wv_rapid);

        mSettings = mWebView.getSettings();
        // WebView设置支持JavaScript
        mSettings.setJavaScriptEnabled(true);
        // 加载URL
        mWebView.loadUrl("http://www.baidu.com");
        // 加载URL
        mRapidWebView = new RapidWebView(this, mWebView);
        mWebView.setWebViewClient(mRapidWebView);
    }
}
