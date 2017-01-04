package com.joyue.tech.gankio.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

import com.joyue.tech.core.manager.DataCleanManager;
import com.joyue.tech.core.ui.activity.RapidToolbarActivity;
import com.joyue.tech.core.utils.FileUtils;
import com.joyue.tech.core.utils.ToastUtils;
import com.joyue.tech.gankio.R;

import java.io.File;


public class SettingsActivity extends RapidToolbarActivity {

    private SettingsFragment mSettingsFragment;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        if (savedInstanceState == null) {
            mSettingsFragment = new SettingsFragment();
            replaceFragment(R.id.settings_container, mSettingsFragment);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pref_notify_priority = sharedPreferences.getBoolean("pref_notify_priority", true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    /**
     * A placeholder fragment containing a settings view.
     */
    public static class SettingsFragment extends PreferenceFragment {

        Activity mActivity;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            mActivity = getActivity();

            Preference pref_clear_cache = getPreferenceScreen().findPreference("pref_clear_cache");
            pref_clear_cache.setSummary(DataCleanManager.getCacheSize(FileUtils.getCacheDir(mActivity)));
        }

        // 当任何一个preference控件被点击，都将触发该方法。可以通过preference.getKey()这个方法找到具体preference，因为每个preference的key都是唯一的。
        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            if ("pref_notify_priority".equals(preference.getKey())) {
                ToastUtils.show(preference.getKey() + preference.getSharedPreferences().getBoolean("pref_notify_priority", true));
            } else if ("pref_clear_cache".equals(preference.getKey())) {
                // 清除缓存目录
                File cacheDir = FileUtils.getCacheDir(mActivity);
                DataCleanManager.deleteFolderFile(cacheDir.getAbsolutePath(), false);
                // 重新创建RxCache目录
                FileUtils.getRxCacheDir();
                // 重新计算
                preference.setSummary(DataCleanManager.getCacheSize(cacheDir));
            }else if ("pref_check_update".equals(preference.getKey())) {
                ToastUtils.show(preference.getKey());
            }

            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }
    }

}
