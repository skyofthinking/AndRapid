package com.joyue.tech.gankio.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.joyue.tech.core.constant.BaseConstant;
import com.joyue.tech.core.ui.activity.RapidActivity;
import com.joyue.tech.core.utils.RecyclerViewUtils;
import com.joyue.tech.core.utils.StrKit;
import com.joyue.tech.core.utils.TLog;
import com.joyue.tech.core.utils.ToastUtils;
import com.joyue.tech.core.widget.SearchView;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.adapter.HistoryAdapter;
import com.joyue.tech.gankio.db.SearchKey;
import com.raizlabs.android.dbflow.list.FlowQueryList;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.weavey.loading.lib.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author JiangYH
 * @desc 搜索界面
 */
public class RapidSearchActivity extends RapidActivity implements SearchView.SearchViewListener {

    @BindView(R.id.rv_search_hot)
    RecyclerView rv_search_hot; // 搜索热搜列表
    @BindView(R.id.rv_search_his)
    RecyclerView rv_search_his; // 搜索历史列表
    @BindView(R.id.rv_search_hint)
    RecyclerView rv_search_hint; // 搜索提示列表
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.ll_input_clear)
    LinearLayout ll_input_clear;
    @BindView(R.id.ll_input_enter)
    LinearLayout ll_input_enter;

    @BindView(R.id.sv_search)
    SearchView sv_search; // 搜索框

    BaseQuickAdapter<String> mHotAdapter; // 热搜列表Adapter
    BaseQuickAdapter<String> mHisAdapter; // 历史列表Adapter
    BaseQuickAdapter<String> mHintAdapter; // 自动补全列表Adapter

    List<SearchKey> mHotList; // 热搜列表
    List<SearchKey> mHisList; // 历史列表
    List<SearchKey> mHintList; // 自动补全列表

    List<String> mHotData;
    List<String> mHisData;
    List<String> mHintData;


    List<String> autoCompleteData; // 搜索过程中自动补全数据

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initData();
        initView();
        initListener();

        loadinglayout.setStatus(LoadingLayout.Success);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        // 设置监听
        sv_search.setSearchViewListener(this);
        sv_search.setAutoCompleteView(rv_search_hint);

        RecyclerViewUtils.initRecyclerViewGridLayout(rv_search_hot, mContext, BaseConstant.Column.TWO);
        rv_search_hot.setAdapter(mHotAdapter);

        RecyclerViewUtils.initRecyclerViewLinearLayout(rv_search_his, mContext);
        rv_search_his.setAdapter(mHisAdapter);

        RecyclerViewUtils.initRecyclerViewLinearLayout(rv_search_hint, mContext);
        rv_search_hint.setAdapter(mHintAdapter);
    }

    private void initListener() {
        mHotAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.show("position " + position);
            }
        });
        mHotAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                ToastUtils.show("position " + position);
                return true;
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        initHotData();

        initHisData();

        initHintData();
    }

    // 热搜
    private void initHotData() {
        mHotData = new ArrayList<>();
        for (int i = 0; i < BaseConstant.Search.DEFAULT_HOT_SIZE; i++) {
            mHotData.add("热搜" + i + "：Android自定义View");
        }
        mHotAdapter = new HistoryAdapter(R.layout.item_search, null);
        mHotAdapter.setNewData(mHotData);
    }

    // 历史
    private void initHisData() {
        FlowQueryList<SearchKey> searchKeys = SQLite.select().from(SearchKey.class).orderBy(OrderBy.fromString("id").descending()).flowQueryList();
        mHisData = new ArrayList<>();
        for (SearchKey searchKey : searchKeys) {
            mHisData.add(searchKey.getKey());
        }
        TLog.i(TAG, "mHisData " + mHisData.size() + " " + mHisData.toString());
        mHisAdapter = new HistoryAdapter(R.layout.item_search, null);
        mHisAdapter.setNewData(mHisData);
    }

    // 提示
    private void initHintData() {
        mHintData = new ArrayList<>();
        for (int i = 0; i < BaseConstant.Search.DEFAULT_HIS_SIZE; i++) {
            mHintData.add("提示" + i + "：Android自定义View");
        }
        mHintAdapter = new HistoryAdapter(R.layout.item_search, null);
        mHintAdapter.setNewData(mHintData);
    }

    /**
     * 获取自动补全
     */
    private void getAutoCompleteData(String text) {
        if (mHintList == null) {
            // 初始化
            mHintList = new ArrayList<>();
        } else {
            // 根据text 获取auto data
            mHintList.clear();
        }
        autoCompleteData = new ArrayList();
        for (int i = 0; i < 3; i++) {
            autoCompleteData.add("提示" + i);
        }
        if (mHintAdapter == null) {
            mHintAdapter = new HistoryAdapter(R.layout.item_search, null);
            mHintAdapter.setNewData(autoCompleteData);
        } else {
            mHintAdapter.setNewData(autoCompleteData);
        }

        if (!StrKit.isEmpty(text)) {
            ll_input_clear.setVisibility(View.GONE);
            ll_input_enter.setVisibility(View.VISIBLE);
        } else {
            ll_input_clear.setVisibility(View.VISIBLE);
            ll_input_enter.setVisibility(View.GONE);
        }
    }

    /**
     * 当搜索框文本改变时触发的回调 更新自动补全数据
     *
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        // 更新数据
        getAutoCompleteData(text);

        initHisData();
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        SearchKey searchKey = new SearchKey();
        searchKey.key = text;
        searchKey.count = 1;
        searchKey.save();

        initHisData();

        rv_search_his.setAdapter(mHisAdapter);
    }
}
