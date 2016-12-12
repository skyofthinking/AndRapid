package com.joyue.tech.gankio.ui.fragment;

import android.os.Bundle;

import com.joyue.tech.core.ui.fragment.RapidTabFragment;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class CategoryFragment extends RapidTabFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public FragmentPagerItems onSetupTabs() {
        String[] categories = new String[]{"all", "Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App"};

        FragmentPagerItems mPages = FragmentPagerItems.with(mContext).create();
        for (int i = 0; i < categories.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("category", categories[i]);
            mPages.add(FragmentPagerItem.of(categories[i], CategoryTabFragment.class, bundle));
        }

        return mPages;
    }
}
