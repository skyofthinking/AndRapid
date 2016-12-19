package com.joyue.tech.gankio.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.gankio.R;

import java.util.List;

/**
 * @author JiangYH
 */
public class HistoryAdapter extends BaseQuickAdapter<String> {

    public HistoryAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
    }
}