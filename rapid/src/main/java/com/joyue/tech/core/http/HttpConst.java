package com.joyue.tech.core.http;

/**
 * @author JiangYH
 */
public class HttpConst {

    // 请求URL
    public String baseUrl = "http://127.0.0.1/";
    public long connectTimeout = 30;
    public long readTimeout = 30;
    public long writeTimeout = 30;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
