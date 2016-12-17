package com.joyue.tech.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author JiangYH
 * @date 2015/11/11
 */
public class GsonUtils {

    public static Gson getGson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        return gson;
    }
}
