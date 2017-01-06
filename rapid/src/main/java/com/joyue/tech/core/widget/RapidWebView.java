package com.joyue.tech.core.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.joyue.tech.core.utils.TLog;
import com.weavey.loading.lib.LoadingLayout;

/**
 * @author JiangYH
 */
public class RapidWebView extends WebViewClient {
    String TAG = getClass().getSimpleName();
    Context mContext;
    WebView mWebView;
    LoadingLayout loadinglayout;

    /**
     * 构造方法
     *
     * @param mContext
     * @param mWebView
     */
    public RapidWebView(Context mContext, WebView mWebView, LoadingLayout loadinglayout) {
        super();
        this.mContext = mContext;
        this.mWebView = mWebView;
        this.loadinglayout = loadinglayout;
    }

    /**
     * 打开链接前的事件 为了避免再次按的时候加载的是系统自带的浏览器 点击链接由自己处理
     * 这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url != null) {
            mWebView.loadUrl(url);
            TLog.d(TAG, "RapidWebView shouldOverrideUrlLoading");
        }
        return true;
    }

    /**
     * 接收到Http请求的事件
     */
    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    /**
     * 载入页面开始的事件
     * 这个事件就是开始载入页面调用的，通常我们可以在这设定一个loading的页面，告诉用户程序在等待网络响应。
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        // 设置加载
        loadinglayout.setStatus(LoadingLayout.Loading);
        TLog.d(TAG, "RapidWebView onPageStarted");
    }

    /**
     * 载入页面完成的事件
     * 同样道理，我们知道一个页面载入完成，于是我们可以关闭loading条，切换程序动作。
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        loadinglayout.setStatus(LoadingLayout.Success);
        TLog.d(TAG, "RapidWebView onPageFinished");
    }

    /**
     * 当浏览器访问制定的网址发生错误时会通知我们应用程序，比如网络错误。
     */
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        loadinglayout.setStatus(LoadingLayout.Error);
    }

}
