package com.joyue.tech.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joyue.tech.core.ui.fragment.RapidFragment;
import com.joyue.tech.core.utils.ImageLoader;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.adapter.ContentAdapter;
import com.joyue.tech.gankio.adapter.ContentSection;
import com.joyue.tech.gankio.constants.Constant;
import com.joyue.tech.gankio.domain.DayResult;
import com.joyue.tech.gankio.domain.Result;
import com.joyue.tech.gankio.mvp.ganhuo.GanhuoContract;
import com.joyue.tech.gankio.mvp.ganhuo.GanhuoPresenter;
import com.vlonjatg.progressactivity.ProgressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ContentFragment extends RapidFragment implements GanhuoContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.progress)
    ProgressActivity progress;
    @BindView(R.id.iv_meizi)
    ImageView iv_meizi;

    BaseQuickAdapter mQuickAdapter;

    GanhuoContract.Presenter present;

    String year = "";
    String month = "";
    String day = "";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    public void initView(View rootView) {
        Bundle bundle = getArguments();
        String date = bundle.getString("date");
        year = date.split("-")[0];
        month = date.split("-")[1];
        day = date.split("-")[2];

        //springView.setFooter(new RotationFooter(this)); mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //如果Item高度固定 增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);

        //设置页面为加载中
        progress.showLoading();

        //设置适配器
        mQuickAdapter = new ContentAdapter(R.layout.item_content_layout, R.layout.item_content_header, null);
        //设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(false);
        //将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mQuickAdapter);

        //请求网络数据
        setPresenter(new GanhuoPresenter(this));
        present.day(year, month, day);

        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mQuickAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                Toast.makeText(mContext, "长按了" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void newDatas(List newList) {
        Result result = (Result) newList.get(0);
        List<DayResult> dayResults = result.getMeizi();
        if (dayResults != null && dayResults.size() > 0) {
            DayResult item = dayResults.get(0);
            ImageLoader.with(iv_meizi, item.getUrl(), R.mipmap.monkey_nodata);
        }

        // 进入显示的初始数据或者下拉刷新显示的数据
        mQuickAdapter.setNewData(convertData(newList)); // 新增数据
        mQuickAdapter.openLoadMore(false); // 设置是否可以下拉加载以及加载条数
    }

    @Override
    public void addDatas(List addList) {
        // 新增自动加载的的数据
        mQuickAdapter.notifyDataChangedAfterLoadMore(convertData(addList), true);
    }

    @Override
    public void showLoadCompleteAllData() {
        //所有数据加载完成后显示
        mQuickAdapter.notifyDataChangedAfterLoadMore(false);
        View view = mActivity.getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
        mQuickAdapter.addFooterView(view);
    }

    /*
    * MVP模式的相关状态
    */
    @Override
    public void showProgress() {
        progress.showLoading();
    }

    @Override
    public void hideProgress() {
        progress.showContent();
    }

    @Override
    public void showLoadFailMsg() {
        //设置加载错误页显示
        progress.showError(getResources().getDrawable(R.mipmap.monkey_cry), Constant.ERROR_TITLE, Constant.ERROR_CONTEXT, Constant.ERROR_BUTTON, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.day(year, month, day);
            }
        });
    }

    @Override
    public void showNoData() {
        //设置无数据显示页面
        progress.showEmpty(getResources().getDrawable(R.mipmap.monkey_nodata), Constant.EMPTY_TITLE, Constant.EMPTY_CONTEXT);
    }

    @Override
    public void setPresenter(GanhuoContract.Presenter presenter) {
        this.present = presenter;
    }

    public static List<ContentSection> convertData(List<Result> data) {
        List<ContentSection> list = new ArrayList<>();
        Result result = data.get(0);
        // Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App

        List<DayResult> dayResults = new ArrayList();

        dayResults = result.getAndroid();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "Android"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        dayResults = result.getIos();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "iOS"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        dayResults = result.getVideo();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "休息视频"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        dayResults = result.getExpandres();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "拓展资源"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        dayResults = result.getFrontend();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "前端"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        dayResults = result.getRecommend();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "瞎推荐"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        dayResults = result.getApp();
        if (dayResults != null && dayResults.size() > 0) {
            list.add(new ContentSection(true, "App"));
            for (DayResult dayResult : dayResults) {
                dayResult.setItemType(DayResult.TEXT);
                list.add(new ContentSection(dayResult));
            }
        }

        return list;
    }
}