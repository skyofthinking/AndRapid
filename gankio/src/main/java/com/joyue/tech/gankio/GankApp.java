package com.joyue.tech.gankio;

import com.joyue.tech.core.RapidApp;

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
}
