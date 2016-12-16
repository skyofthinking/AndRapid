package com.joyue.tech.gankio.ui.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.joyue.tech.core.ui.activity.RapidToolbarActivity;
import com.joyue.tech.core.utils.ToastUtils;
import com.joyue.tech.gankio.R;


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
        boolean notify_priority = sharedPreferences.getBoolean("notify_priority", true);
        ToastUtils.show("notify_priority " + notify_priority);
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

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
