package com.joyue.tech.core.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.joyue.tech.core.RapidApp;
import com.joyue.tech.core.ui.activity.RapidClickButtonActivity;

/**
 * @author JiangYH
 */
public class UIManager {

    public static void startActivityByFragmentName(Context context, String fragmentName) {
        Intent intent = new Intent(RapidApp.getContext(), RapidClickButtonActivity.class);
        intent.putExtra("fragmentName", fragmentName);
        context.startActivity(intent);
    }

    public static void startActivityByFragmentNameForResult(Fragment activity, String fragmentName, int requestCode) {
        Intent intent = new Intent(RapidApp.getContext(), RapidClickButtonActivity.class);
        intent.putExtra("fragmentName", fragmentName);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivity(Context context, Class activityClass) {
        Intent intent = new Intent(RapidApp.getContext(), activityClass);
        context.startActivity(intent);
    }

}
