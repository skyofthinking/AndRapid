package com.joyue.tech.gankio.ui.fragment;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.core.utils.ImageLoader;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.domain.Result;

import java.util.List;

/**
 * @author JiangYH
 */

public class BenefitAdapter extends BaseQuickAdapter<Result> {

    public BenefitAdapter(int layoutResId, List<Result> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result item) {
        ImageLoader.with((ImageView) helper.getView(R.id.iv_meizi), item.getUrl(), R.mipmap.monkey_nodata);
    }
}