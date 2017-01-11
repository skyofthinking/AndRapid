package com.joyue.tech.core.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.joyue.tech.core.R;

/**
 * @author JiangYH
 * @desc 搜索界面
 */
public class SearchView extends ViewLinearLayout implements View.OnClickListener {

    View rootView; // View
    EditText et_search_key; // 输入框
    ImageView iv_delete; // 删除键
    Button btn_cancel; // 返回按钮
    Context mContext; // 上下文对象
    View autoCompView;
    SearchViewListener mListener;  // 搜索回调接口

    /**
     * 设置搜索回调接口
     *
     * @param listener 监听者
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.view_search, this);
        initView();
    }

    private void initView() {
        et_search_key = $(R.id.et_search_key);
        iv_delete = $(R.id.iv_delete);
        btn_cancel = $(R.id.btn_cancel);

        iv_delete.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        et_search_key.addTextChangedListener(new EditChangedListener());
        et_search_key.setOnClickListener(this);
        et_search_key.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    notifyStartSearching(et_search_key.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     *
     * @param text
     */
    private void notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onSearch(et_search_key.getText().toString());
        }
        // 隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置自动补全Adapter
     */
    public void setAutoCompleteView(View autoCompView) {
        this.autoCompView = autoCompView;
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                iv_delete.setVisibility(VISIBLE);
                if (autoCompView != null) {
                    // autoCompView.setVisibility(VISIBLE);
                }
                //更新AutoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
                iv_delete.setVisibility(GONE);
                if (autoCompView != null) {
                    // autoCompView.setVisibility(GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_delete) {
            et_search_key.setText("");
            mListener.onRefreshAutoComplete("");
            iv_delete.setVisibility(GONE);
        }
        if (id == R.id.btn_cancel) {
            ((Activity) mContext).finish();
        }
    }

    /**
     * SearchView 回调方法
     */
    public interface SearchViewListener {
        /**
         * 更新自动补全内容
         *
         * @param text 传入补全后的文本
         */
        void onRefreshAutoComplete(String text);

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);
    }

}