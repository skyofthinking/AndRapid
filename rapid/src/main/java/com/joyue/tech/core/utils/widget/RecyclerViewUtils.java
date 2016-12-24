package com.joyue.tech.core.utils.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView的设置
 *
 * @author JiangYH
 */
public class RecyclerViewUtils {

    public static void setDefRecyclerView(RecyclerView recyclerView, Context context) {
        // 设置RecyclerView的显示模式 当前List模式
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // 如果Item高度固定 增加该属性能够提高效率
        recyclerView.setHasFixedSize(true);
    }
}
