package com.joyue.tech.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.StrictMode;

import com.joyue.tech.core.http.HttpConst;
import com.joyue.tech.core.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;

/**
 * @author JiangYH
 * @desc App的基础类
 */
public class RapidApp extends Application {

    private static Context mContext;

    public static HttpConst mHttpConst = new HttpConst();

    public static boolean HTTP_DEBUG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        this.instance = this;

        // 异常恢复
        initRecovery();

        // 加载中、无网络、无数据、出错
        initLoading();

        // 初始化配置
        SPUtils.init(this);

        // 初始话日志
        initLog();

        // 初始化数据库
        initDB();

        // 初始化异常处理
        // initException();

        // 初始化Http
        initHttp();

        // 初始化内存泄漏错误堆栈 信息 LeakCanary
        // LeakCanary 1.4 版本有问题 暂时屏蔽
        // initLeakCanary();

        // 初始化推送
        initPush();

        // 初始化地图
        initMap();
    }

    public void initRecovery() {

    }

    public void initLoading() {

    }

    public void initException() {
        // CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        // crashHandler.init(mContext);
        // 发送以前没发送的报告(可选)
        // crashHandler.sendPreviousReportsToServer();
    }

    public void initLog() {
        // TLog.setDebugMode(BuildConfig.DEBUG);
    }

    public void initDB() {
    }

    public void initHttp() {
        mHttpConst.setBaseUrl("");
        mHttpConst.setConnectTimeout(30);
        mHttpConst.setReadTimeout(30);
        mHttpConst.setWriteTimeout(30);
    }

    public void initLeakCanary() {
        // if (LeakCanary.isInAnalyzerProcess(this)) {
        // This process is dedicated to LeakCanary for heap analysis.
        // You should not init your app in this process.
        //    return;
        //}
        //enabledStrictMode();
        //LeakCanary.install(this);
    }

    public void initPush() {
    }

    public void initMap() {
    }

    public void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    public static HttpConst getHttpConst() {
        return mHttpConst;
    }

    public static Resources getAppResources() {
        return mContext.getResources();
    }

    public static Context getContext() {
        return mContext;
    }

    public static PackageInfo getPackageInfo() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // 记录当前栈里所有activity
    private List<Activity> activities = new ArrayList<Activity>();
    // 记录需要一次性关闭的页面
    private List<Activity> activitys = new ArrayList<Activity>();

    /**
     * 应用实例
     **/
    private static RapidApp instance;

    /**
     * 获得实例
     *
     * @return
     */
    public static RapidApp getInstance() {
        return instance;
    }

    /**
     * 新建了一个activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            this.activities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    *给临时Activitys
    * 添加activity
    * */
    public void addTemActivity(Activity activity) {
        activitys.add(activity);
    }

    public void finishTemActivity(Activity activity) {
        if (activity != null) {
            this.activitys.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*
    * 退出指定的Activity
    * */
    public void exitActivitys() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 应用退出，结束所有的activity
     */
    public void exit() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        System.exit(0);
    }
}

