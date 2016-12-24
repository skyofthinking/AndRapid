package com.joyue.tech.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joyue.tech.core.constant.BaseConstant;
import com.joyue.tech.core.rx.Events;
import com.joyue.tech.core.rx.RxBus;
import com.joyue.tech.core.ui.UIManager;
import com.joyue.tech.core.ui.fragment.RapidFragment;
import com.joyue.tech.core.utils.SPUtils;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.adapter.HistoryAdapter;
import com.joyue.tech.gankio.mvp.history.HistoryContract;
import com.joyue.tech.gankio.mvp.history.HistoryPresenter;
import com.joyue.tech.gankio.rx.EventsWhat;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;

public class HistoryFragment extends RapidFragment implements SpringView.OnFreshListener, HistoryContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    BaseQuickAdapter mQuickAdapter;

    HistoryContract.Presenter present;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_view;
    }

    @Override
    public void initView(View rootView) {
        Bundle bundle = getArguments();

        // 设置下拉刷新监听
        springView.setListener(this);
        // 设置下拉刷新样式
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(mContext));
        // springView.setFooter(new RotationFooter(this)); mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用

        // 与ListView唯一的区别就是这里new GridLayoutManager(this,2) 数字代表列数
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, BaseConstant.Column.TWO));
        // 如果Item高度固定 增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);

        // 设置页面为加载中
        loadinglayout.setStatus(LoadingLayout.Loading);

        // 设置适配器
        mQuickAdapter = new HistoryAdapter(R.layout.item_history, null);
        // 设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        // 设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(false);
        // 将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mQuickAdapter);

        // 请求网络数据
        setPresenter(new HistoryPresenter(this));
        present.history();

        initListener();
    }

    private void initListener() {
        mQuickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Toast.makeText(mContext, "点击了" + position, Toast.LENGTH_SHORT).show();
                String date = (String) mQuickAdapter.getData().get(position);
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                UIManager.startActivityByFragmentName(mContext, ContentFragment.class.getName(), bundle);
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
        // 进入显示的初始数据或者下拉刷新显示的数据
        mQuickAdapter.setNewData(newList); // 新增数据
        mQuickAdapter.openLoadMore(false); // 设置是否可以下拉加载以及加载条数
        springView.onFinishFreshAndLoad(); // 刷新完成

        if (newList != null && newList.size() > 0) {
            String new_date = (String) newList.get(0);

            String def_day_date = SPUtils.getString("def_day_date");
            if (!new_date.equals(def_day_date)) {
                Events<String> events = Events.just(new_date);
                events.what = EventsWhat.SET_CUR_DATE;
                RxBus.getInstance().send(events);

                SPUtils.put("def_day_date", new_date);
            }
        }
    }

    @Override
    public void addDatas(List addList) {
        //新增自动加载的的数据
        mQuickAdapter.notifyDataChangedAfterLoadMore(addList, true);
    }

    @Override
    public void showLoadCompleteAllData() {
        //所有数据加载完成后显示
        mQuickAdapter.notifyDataChangedAfterLoadMore(false);
        View view = mActivity.getLayoutInflater().inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
        mQuickAdapter.addFooterView(view);
    }

    /**
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
                present.history();
            }
        });
    }

    @Override
    public void showNoData() {
        // 设置无数据显示页面
        loadinglayout.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        this.present = presenter;
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        present.history();
    }

    // 上拉加载 mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用
    @Override
    public void onLoadmore() {

    }

}
