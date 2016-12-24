package com.joyue.tech.gankio;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joyue.tech.core.rx.Events;
import com.joyue.tech.core.rx.RxBus;
import com.joyue.tech.core.ui.UIManager;
import com.joyue.tech.core.ui.activity.RapidToolbarActivity;
import com.joyue.tech.core.utils.FragmentUtils;
import com.joyue.tech.core.utils.SPUtils;
import com.joyue.tech.core.utils.StrKit;
import com.joyue.tech.gankio.mvp.history.HistoryContract;
import com.joyue.tech.gankio.mvp.history.HistoryPresenter;
import com.joyue.tech.gankio.rx.EventsWhat;
import com.joyue.tech.gankio.ui.activity.AboutActivity;
import com.joyue.tech.gankio.ui.activity.SettingsActivity;
import com.joyue.tech.gankio.ui.fragment.GanhuoFragment;
import com.joyue.tech.gankio.ui.fragment.HistoryFragment;
import com.joyue.tech.gankio.ui.fragment.HomeFragment;
import com.joyue.tech.gankio.ui.fragment.MeiziFragment;

import java.util.List;

public class MainActivity extends RapidToolbarActivity implements NavigationView.OnNavigationItemSelectedListener, HistoryContract.View {

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;

    HistoryContract.Presenter present;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();

        // 请求网络数据
        String def_day_date = SPUtils.getString("def_day_date");
        setPresenter(new HistoryPresenter(this));
        if (StrKit.isEmpty(def_day_date)) {
            present.history();
        } else {
            present.history();

            replaceFragment(HomeFragment.class);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            replaceFragment(HomeFragment.class);
        } else if (id == R.id.nav_category) {
            replaceFragment(GanhuoFragment.class);
        } else if (id == R.id.nav_meizi) {
            replaceFragment(MeiziFragment.class);
        } else if (id == R.id.nav_history) {
            replaceFragment(HistoryFragment.class);
        } else if (id == R.id.nav_settings) {
            UIManager.startActivity(this, SettingsActivity.class);
        } else if (id == R.id.nav_aboutme) {
            UIManager.startActivity(this, AboutActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Class<? extends Fragment> newFragment) {
        mCurrentFragment = FragmentUtils.switchFragment(mFragmentManager, R.id.frame_container, mCurrentFragment, newFragment, new Bundle(), false);
    }

    private void replaceFragment(Class<? extends Fragment> newFragment, Bundle bundle) {
        mCurrentFragment = FragmentUtils.switchFragment(mFragmentManager, R.id.frame_container, mCurrentFragment, newFragment, bundle, false);
    }

    @Override
    public void newDatas(List newList) {
        if (newList != null && newList.size() > 0) {
            String new_date = (String) newList.get(0);
            String def_day_date = SPUtils.getString("def_day_date");

            SPUtils.put("def_day_date", new_date);

            if(StrKit.isEmpty(def_day_date)) {
                replaceFragment(HomeFragment.class);
            } else if (!new_date.equals(def_day_date)) {
                SPUtils.put("def_day_date", new_date);

                Events<String> events = Events.just(new_date);
                events.what = EventsWhat.SET_CUR_DATE;
                RxBus.getInstance().send(events);
            }
        }
    }

    @Override
    public void addDatas(List addList) {
    }

    @Override
    public void showLoadCompleteAllData() {
    }

    /**
     * MVP模式的相关状态
     */
    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showLoadFailMsg() {
    }

    @Override
    public void showNoData() {
    }

    @Override
    public void setPresenter(HistoryContract.Presenter presenter) {
        this.present = presenter;
    }
}
