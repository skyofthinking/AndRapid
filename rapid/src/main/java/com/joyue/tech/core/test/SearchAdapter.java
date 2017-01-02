package com.joyue.tech.core.test;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.core.R;

import java.util.List;

/**
 * @author JiangYH
 */
public class SearchAdapter extends BaseQuickAdapter<Bean> {

    public SearchAdapter(int layoutResId, List<Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Bean item) {
        helper.setText(R.id.item_search_tv_title, item.getTitle())
                .setText(R.id.item_search_tv_content, item.getContent())
                .setText(R.id.item_search_tv_comments, item.getComments());
    }

}