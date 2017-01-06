package com.joyue.tech.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author JiangYH
 */
public class ViewLinearLayout extends LinearLayout {

    public ViewLinearLayout(Context context) {
        super(context);
    }

    public ViewLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ViewLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

}
