package com.joyue.tech.core.rx;


import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 使用RxJava代替EventBus的方案
 *
 * @author JiangYH
 **/
public class RxBus {

    private static RxBus rxBus;
    private final Subject<Events<?>, Events<?>> _bus = new SerializedSubject<>(PublishSubject.create());

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxBus == null) {
            synchronized (RxBus.class) {
                if (rxBus == null) {
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }

    public void send(Events<?> o) {
        _bus.onNext(o);
    }

    public void send(Events sendType, Events receiveType) {
        Events<Object> event = new Events<>();
        event.message = receiveType;
        send(event);
    }

    public void send(Events sendType, Events receiveType, int what) {
        Events<Object> event = new Events<>();
        event.what = what;
        event.message = receiveType;
        send(event);
    }

    public Observable<Events<?>> toObservable() {
        return _bus;
    }

    public static SubscriberBuilder with(LifecycleProvider provider) {
        return new SubscriberBuilder(provider);
    }


    public static class SubscriberBuilder {
        LifecycleProvider mLifecycleProvider;
        private FragmentEvent mFragmentEndEvent;
        private ActivityEvent mActivityEndEvent;
        private int event;
        private Action1<? super Events<?>> onNext;
        private Action1<Throwable> onError;

        public SubscriberBuilder(LifecycleProvider provider) {
            this.mLifecycleProvider = provider;
        }

        public SubscriberBuilder setEvent(int event) {
            this.event = event;
            return this;
        }

        public SubscriberBuilder setEndEvent(FragmentEvent event) {
            this.mFragmentEndEvent = event;
            return this;
        }

        public SubscriberBuilder setEndEvent(ActivityEvent event) {
            this.mActivityEndEvent = event;
            return this;
        }

        public SubscriberBuilder onNext(Action1<? super Events<?>> action) {
            this.onNext = action;
            return this;
        }

        public SubscriberBuilder onError(Action1<Throwable> action) {
            this.onError = action;
            return this;
        }

        Action1<Throwable> rxDefError = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        };

        public Observable observable() {
            if (mLifecycleProvider != null && mFragmentEndEvent != null) {
                return RxBus.getInstance().toObservable().compose(mLifecycleProvider.bindUntilEvent(mFragmentEndEvent)).ofType(Events.class).filter(new Func1<Events, Boolean>() {
                    @Override
                    public Boolean call(Events events) {
                        return events.what == event;
                    }
                });
            }
            if (mLifecycleProvider != null && mActivityEndEvent != null) {
                return RxBus.getInstance().toObservable().compose(mLifecycleProvider.bindUntilEvent(mActivityEndEvent)).ofType(Events.class).filter(new Func1<Events, Boolean>() {
                    @Override
                    public Boolean call(Events events) {
                        return events.what == event;
                    }
                });
            }
            return null;
        }

        public void create() {
            _create();
        }

        public Subscription _create() {
            if (mLifecycleProvider != null && mFragmentEndEvent != null) {
                return RxBus.getInstance().toObservable().compose(mLifecycleProvider.bindUntilEvent(mFragmentEndEvent)).ofType(Events.class).filter(new Func1<Events, Boolean>() {
                    @Override
                    public Boolean call(Events events) {
                        return events.what == event;
                    }
                }).subscribe((Action1<? super Events>) onNext, onError == null ? rxDefError : onError);
            }
            if (mLifecycleProvider != null && mActivityEndEvent != null) {
                return RxBus.getInstance().toObservable().compose(mLifecycleProvider.bindUntilEvent(mActivityEndEvent)).ofType(Events.class).filter(new Func1<Events, Boolean>() {
                    @Override
                    public Boolean call(Events events) {
                        return events.what == event;
                    }
                }).subscribe((Action1<? super Events>) onNext, onError == null ? rxDefError : onError);
            }
            return null;
        }
    }

}