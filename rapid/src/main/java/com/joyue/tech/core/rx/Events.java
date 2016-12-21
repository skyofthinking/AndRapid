package com.joyue.tech.core.rx;

/**
 * 使用RxJava代替EventBus的方案
 *
 * @author JiangYH
 */
public class Events<T> {

    public int what;
    public T message;

    public static <O> Events<O> just(O t) {
        Events<O> events = new Events<>();
        events.message = t;
        return events;
    }

    public <T> T getMessage() {
        return (T) message;
    }

}
