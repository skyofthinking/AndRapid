package com.joyue.tech.core.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * @author JiangYH
 */

public class PopupWindowUtils {

    private String title[] = {"1", "2", "3", "4", "5"};

    @SuppressWarnings("deprecation")
    public void showPopupWindow(Context ctx, View parent, int layoutPopup, ListView listView) {
        // 加载布局
        LinearLayout layout = (LinearLayout) LayoutInflater.from(ctx).inflate(layoutPopup, null);
        // 找到布局的控件
        // listView = (ListView) layout.findViewById(R.id.lv_dialog);
        // 设置适配器
        // listView.setAdapter(new ArrayAdapter<String>(ctx, R.layout.view_search_list_text, R.id.tv_text, title));
        // 实例化popupWindow
        PopupWindow popupWindow = new PopupWindow(layout, 300, 500);
        //控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        //设置popupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        // 获取xoff
        int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;
        // xoff yoff基于anchor的左下角进行偏移。
        popupWindow.showAsDropDown(parent, xpos, 0);
        //监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // 关闭PopupWindow
                // popupWindow.dismiss();
                // popupWindow = null;
            }
        });
    }
}
