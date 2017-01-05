package com.joyue.tech.gankio.adapter;

import android.content.Context;

import com.blankj.utilcode.utils.TimeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.joyue.tech.core.utils.ImageLoader;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.domain.Result;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class GanHuoAdapter extends BaseMultiItemQuickAdapter<Result> {

    public static final SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());

    public GanHuoAdapter(Context context, List data) {
        super(data);
        addItemType(Result.TEXT, R.layout.item_ganhuo_text);
        addItemType(Result.IMG, R.layout.item_ganhuo_image);
    }

    @Override
    protected void convert(BaseViewHolder helper, Result item) {
        switch (helper.getItemViewType()) {
            case Result.TEXT:
                helper.setText(R.id.tv_title, item.getDesc());
                helper.setText(R.id.tv_people, item.getWho());
                helper.setText(R.id.tv_time, TimeUtils.date2String(TimeUtils.string2Date(item.getPublishedAt(), DEFAULT_SDF)));
                break;
            case Result.IMG:
                ImageLoader.with(helper.getView(R.id.iv_meizi), item.getUrl(), R.mipmap.img_nodata, true);
                break;
        }
    }
}
