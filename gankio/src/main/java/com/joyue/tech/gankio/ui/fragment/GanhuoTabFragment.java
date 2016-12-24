package com.joyue.tech.gankio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joyue.tech.core.ui.fragment.RapidFragment;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.adapter.GanHuoAdapter;
import com.joyue.tech.gankio.domain.Result;
import com.joyue.tech.gankio.mvp.ganhuo.GanhuoContract;
import com.joyue.tech.gankio.mvp.ganhuo.GanhuoPresenter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.weavey.loading.lib.LoadingLayout;

import java.util.List;

import butterknife.BindView;

public class GanhuoTabFragment extends RapidFragment implements BaseQuickAdapter.RequestLoadMoreListener, SpringView.OnFreshListener, GanhuoContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;

    BaseQuickAdapter mQuickAdapter;

    GanhuoContract.Presenter present;
    String category = "all";
    int page = 1;
    int count = 10;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_view;
    }

    @Override
    public void initView(View rootView) {
        page = 1;

        Bundle bundle = getArguments();
        category = bundle.getString("category");

        // 设置下拉刷新监听
        springView.setListener(this);
        // 设置下拉刷新样式
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new DefaultHeader(mContext));
        // springView.setFooter(new RotationFooter(this)); mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用

        // 设置RecyclerView的显示模式 当前List模式
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        // 如果Item高度固定 增加该属性能够提高效率
        mRecyclerView.setHasFixedSize(true);

        // 设置页面为加载中
        loadinglayout.setStatus(LoadingLayout.Loading);

        // 设置适配器
        mQuickAdapter = new GanHuoAdapter(mContext, null);
        // 设置加载动画
        mQuickAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        // 设置是否自动加载以及加载个数
        mQuickAdapter.openLoadMore(10, true);
        // 将适配器添加到RecyclerView
        mRecyclerView.setAdapter(mQuickAdapter);

        // 请求网络数据
        setPresenter(new GanhuoPresenter(this));
        present.data(category, count, page, false);

        initListener();
    }

    private void initListener() {
        //设置自动加载监听
        mQuickAdapter.setOnLoadMoreListener(this);

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

    //自动加载
    @Override
    public void onLoadMoreRequested() {
        page++;
        present.data(category, count, page, true);
    }

    @Override
    public void newDatas(List<Result> newList) {
        //进入显示的初始数据或者下拉刷新显示的数据
        mQuickAdapter.setNewData(newList);//新增数据
        mQuickAdapter.openLoadMore(10, true);//设置是否可以下拉加载以及加载条数
        springView.onFinishFreshAndLoad();//刷新完成
    }

    @Override
    public void addDatas(List<Result> addList) {
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
                page = 1;
                present.data(category, count, page, false);
            }
        });
    }

    @Override
    public void showNoData() {
        //设置无数据显示页面
        loadinglayout.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void setPresenter(GanhuoContract.Presenter presenter) {
        this.present = presenter;
    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        page = 1;
        present.data(category, count, page, false);
    }

    // 上拉加载 mRecyclerView内部集成的自动加载 上拉加载用不上 在其他View使用
    @Override
    public void onLoadmore() {

    }

}
