package com.joyue.tech.core.utils;

import com.orhanobut.logger.Logger;

/**
 * @author JiangYH
 * @desc 日志打印
 */
public class TLog {

    private static boolean DEBUG = true;

    public static void setDebugMode(boolean configDebug) {
        DEBUG = configDebug;
    }

    public static void v(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).v(data);
    }

    public static void d(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).d(data);
    }

    public static void i(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).i(data);
    }

    public static void w(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).w(data);
    }

    public static void e(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).e(data);
    }

    public static void e(String tag, String data, Throwable e) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).e(e, data);
    }

    public static void wtf(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).wtf(data);
    }


    public static void json(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).json(data);
    }

    public static void xml(String tag, String data) {
        if (!DEBUG) {
            return;
        }
        Logger.t(tag).xml(data);
    }
}