package com.joyue.tech.gankio.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.core.utils.ImageLoader;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */

public class MeiziAdapter extends BaseQuickAdapter<Result> {

    public MeiziAdapter(int layoutResId, List<Result> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result item) {
        ImageLoader.with(helper.getView(R.id.iv_meizi), item.getUrl(), R.mipmap.img_nodata, true);
    }
}