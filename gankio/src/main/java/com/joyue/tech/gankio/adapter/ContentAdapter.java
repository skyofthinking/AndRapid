package com.joyue.tech.gankio.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.domain.DayResult;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class ContentAdapter extends BaseSectionQuickAdapter<ContentSection> {

    public ContentAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final ContentSection item) {
        helper.setText(R.id.tv_header, item.header);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentSection item) {
        DayResult result = (DayResult) item.t;
        helper.setText(R.id.tv_title, result.getDesc());
    }
}
