package com.joyue.tech.core.exception;

import com.joyue.tech.core.http.base.BaseResp;

/**
 * @author JiangYH
 * @desc Api异常类
 */
public class ApiException extends BaseException {

    public ApiException(BaseResp baseResp) {
        this(getApiExceptionMessage(baseResp));
    }

    public ApiException(String message) {
        super(message);
    }

    /**
     * 对服务器接口传过来的错误信息进行统一处理
     * 免除在Activity的过多的错误判断
     */
    private static String getApiExceptionMessage(BaseResp baseResp) {
        String message = "";
        switch (baseResp.getCode()) {
            default:
                message = "错误代码: " + baseResp.getCode() + " 错误信息：" + baseResp.getMessage();

        }
        return message;
    }
}