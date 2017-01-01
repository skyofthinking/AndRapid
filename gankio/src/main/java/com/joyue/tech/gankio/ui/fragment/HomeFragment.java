package com.joyue.tech.gankio.ui.fragment;

import android.view.View;

import com.joyue.tech.core.rx.Events;
import com.joyue.tech.core.rx.RxBus;
import com.joyue.tech.core.utils.SPUtils;
import com.joyue.tech.core.utils.StrKit;
import com.joyue.tech.gankio.rx.EventsWhat;
import com.trello.rxlifecycle.android.FragmentEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

import rx.functions.Action1;

public class HomeFragment extends ContentFragment {

    @Override
    public void initView(View rootView) {
        super.initView(rootView);

        initSubscribers();
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

    public String getHisDate() {
        String date = SPUtils.getString("def_day_date");
        if (StrKit.isEmpty(date)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.format(new Date());
        }
        return date;
    }
}