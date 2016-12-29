package com.joyue.tech.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joyue.tech.core.rx.Events;
import com.joyue.tech.core.rx.RxBus;
import com.joyue.tech.core.ui.fragment.RapidFragment;
import com.joyue.tech.core.utils.ImageLoader;
import com.joyue.tech.core.utils.SPUtils;
import com.joyue.tech.core.utils.StrKit;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.adapter.ContentAdapter;
import com.joyue.tech.gankio.adapter.ContentSection;
import com.joyue.tech.gankio.domain.DayResult;
import com.joyue.tech.gankio.domain.Result;
import com.joyue.tech.gankio.mvp.ganhuo.GanhuoContract;
import com.joyue.tech.gankio.mvp.ganhuo.GanhuoPresenter;
import com.joyue.tech.gankio.rx.EventsWhat;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.weavey.loading.lib.LoadingLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class HomeFragment  extends RapidFragment implements GanhuoContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
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

        String date = SPUtils.getString("def_day_date");
        if (StrKit.isEmpty(date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.format(new Date());
        }
        year = date.split("-")[0];
        month = date.split("-")[1];
        day = date.split("-")[2];

        // springView.setFooter(new RotationFooter(this)); mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // 解决滑动不流畅问题
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);

        // 设置页面为加载中
        loadinglayout.setStatus(LoadingLayout.Loading);

        // 设置适配器
        mQuickAdapter = new ContentAdapter(R.layout.item_content_layout, R.layout.item_content_header, null);
        // 设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        // 设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(false);
        // 将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mQuickAdapter);


        // 请求网络数据
        setPresenter(new GanhuoPresenter(this));
        present.day(year, month, day);

        initListener();

        initSubscribers();
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
        loadinglayout.setStatus(LoadingLayout.Loading);
    }

    @Override
    public void hideProgress() {
        loadinglayout.setStatus(LoadingLayout.Success);
    }

    @Override
    public void showLoadFailMsg() {
        // 设置加载错误页显示
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                present.day(year, month, day);
            }
        });
    }

    @Override
    public void showNoData() {
        // 设置无数据显示页面
        loadinglayout.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void setPresenter(GanhuoContract.Presenter presenter) {
        this.present = presenter;
    }

    public List<ContentSection> convertData(List<Result> data) {
        List<ContentSection> list = new ArrayList<>();
        Result result = data.get(0);
        // Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App

        List<DayResult> dayResults = new ArrayList();

        dayResults = result.getAndroid();
        addContentSection(list, dayResults, "Android");

        dayResults = result.getIos();
        addContentSection(list, dayResults, "iOS");

        dayResults = result.getVideo();
        addContentSection(list, dayResults, "休息视频");

        dayResults = result.getExpandres();
        addContentSection(list, dayResults, "拓展资源");

        dayResults = result.getFrontend();
        addContentSection(list, dayResults, "前端");

        dayResults = result.getRecommend();
        addContentSection(list, dayResults, "瞎推荐");

        dayResults = result.getApp();
        addContentSection(list, dayResults, "App");

        return list;
    }

    public void addContentSection(List csList, List<DayResult> dayResults, String section) {
        if (dayResults != null && dayResults.size() > 0) {
            csList.add(new ContentSection(true, section));
            for (DayResult dayResult : dayResults) {
                csList.add(new ContentSection(dayResult));
            }
        }
    }


    public void initSubscribers() {
        RxBus.with(this).setEvent(EventsWhat.SET_CUR_DATE).setEndEvent(FragmentEvent.DESTROY).onNext(new Action1<Events<?>>() {
            @Override
            public void call(Events<?> events) {
                if (events.what == EventsWhat.SET_CUR_DATE) {
                    String cur_date = events.getMessage();

                    year = cur_date.split("-")[0];
                    month = cur_date.split("-")[1];
                    day = cur_date.split("-")[2];

                    present.day(year, month, day);
                }
            }
        }).create();
    }
}