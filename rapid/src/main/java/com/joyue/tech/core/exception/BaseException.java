package com.joyue.tech.core.exception;

/**
 * @author JiangYH
 * @desc 基础异常类
 */
public class BaseException extends RuntimeException {

    public BaseException() {
        throw new RuntimeException("BaseException");
    }

    public BaseException(String detailMessage) {
        throw new RuntimeException("BaseException");
    }

    public BaseException(String detailMessage, Throwable throwable) {
        throw new RuntimeException("BaseException");
    }

    public BaseException(Throwable throwable) {
        throw new RuntimeException("BaseException");
    }
}
