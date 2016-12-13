package com.joyue.tech.gankio.ui.fragment;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */

public class CategoryAdapter extends BaseQuickAdapter<Result> {

    public CategoryAdapter(int layoutResId, List<Result> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result item) {
        helper.setText(R.id.tv_desc, item.getDesc());
    }
}