package com.joyue.tech.core.constant;

/**
 * @author JiangYH
 */

public class BaseConstant {

    public static final class Http {
        private Http() {
        }

        public static String SUCCESS = "0";
    }

    public static final class ViewLayout {
        public static String ERROR_TITLE = "网络连接异常";
        public static String ERROR_CONTEXT = "请检查网络后重试";
        public static String ERROR_BUTTON = "重试";

        public static String EMPTY_TITLE = "没有找到数据";
        public static String EMPTY_CONTEXT = "换个条件试试";
    }


    public static final class Column {
        private Column() {
        }

        public static int TWO = 2;
    }

    public static final class IntentConst {
        private IntentConst() {
        }

        public static String URL = "URL";
    }

}
