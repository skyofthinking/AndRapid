package com.joyue.tech.gankio.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.joyue.tech.core.base.fragment.RapidFragment;
import com.joyue.tech.core.utils.ToastUtils;
import com.joyue.tech.gankio.R;
import com.joyue.tech.gankio.api.GankApi;
import com.joyue.tech.gankio.domain.Result;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;

import java.util.List;

import butterknife.BindView;
import rx.Observer;

public class HomeFragment extends RapidFragment {

    @BindView(R.id.tv_test1)
    TextView tv_test;

    @BindView(R.id.btn_test)
    Button btn_test;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_tab;
    }

    @Override
    public void initView(View rootView) {
        int position = FragmentPagerItem.getPosition(getArguments());
        tv_test.setText("Home" + String.valueOf(position));

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // UIManager.startActivityByFragmentName(mContext, CategoryTabFragment.class.getName());
                GankApi.getInstance().data("all", 10, 1, new Observer<List<Result>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        //设置页面为加载错误
                    }

                    @Override
                    public void onNext(List<Result> data) {
                        ToastUtils.show("GankIO : " + data.get(0).getDesc());
                    }
                });
            }
        });
    }
}
