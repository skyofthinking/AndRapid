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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.joyue.tech.core.R;

/**
 * @author JiangYH
 * @desc 搜索界面
 */
public class SearchView extends ViewLinearLayout implements View.OnClickListener {

    View rootView;

    // 输入框
    EditText et_input;
    // 删除键
    ImageView iv_delete;
    // 返回按钮
    Button btn_back;
    // 上下文对象
    Context mContext;
    // 弹出列表
    ListView lv_hint;
    // 提示Adapter
    ArrayAdapter<String> mHintAdapter;
    // 自动补全adapter 只显示名字
    ArrayAdapter<String> mAutoCompleteAdapter;
    // 搜索回调接口
    private SearchViewListener mListener;

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
        initViews();
    }

    private void initViews() {
        et_input = $(R.id.et_input);
        iv_delete = $(R.id.iv_delete);
        btn_back = $(R.id.btn_back);
        lv_hint = $(R.id.lv_hint);

        lv_hint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Set Edit Text
                String text = lv_hint.getAdapter().getItem(i).toString();
                et_input.setText(text);
                et_input.setSelection(text.length());
                //Hint ListView Gone And Result ListView Show
                lv_hint.setVisibility(View.GONE);
                notifyStartSearching(text);
            }
        });

        iv_delete.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        et_input.addTextChangedListener(new EditChangedListener());
        et_input.setOnClickListener(this);
        et_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    lv_hint.setVisibility(GONE);
                    notifyStartSearching(et_input.getText().toString());
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
            mListener.onSearch(et_input.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 设置热搜版提示 adapter
     */
    public void setTipsHintAdapter(ArrayAdapter<String> adapter) {
        this.mHintAdapter = adapter;
        if (lv_hint.getAdapter() == null) {
            lv_hint.setAdapter(mHintAdapter);
        }
    }

    /**
     * 设置自动补全adapter
     */
    public void setAutoCompleteAdapter(ArrayAdapter<String> adapter) {
        this.mAutoCompleteAdapter = adapter;
    }

    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                iv_delete.setVisibility(VISIBLE);
                lv_hint.setVisibility(VISIBLE);
                if (mAutoCompleteAdapter != null && lv_hint.getAdapter() != mAutoCompleteAdapter) {
                    lv_hint.setAdapter(mAutoCompleteAdapter);
                }
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
                iv_delete.setVisibility(GONE);
                if (mHintAdapter != null) {
                    lv_hint.setAdapter(mHintAdapter);
                }
                lv_hint.setVisibility(GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.et_input) {
            lv_hint.setVisibility(VISIBLE);
        }
        if (id == R.id.iv_delete) {
            et_input.setText("");
            iv_delete.setVisibility(GONE);
        }
        if (id == R.id.btn_back) {
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

        /**
         * 提示列表项点击时回调方法 (提示/自动补全)
         */
        // void onTipsItemClick(String text);
    }

}