package com.alfred.androidstudy.rxjava;

import android.util.Log;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Alfred on 2017/3/7.
 */

public class RxBus {
    private static volatile RxBus intstance;
    private final Subject<Object, Object> bus;

    private RxBus() {
        //Subject是非线程安全的,要避免该问题,需要将Subject转换成一个SerializedSubject,
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getInstance() {
        if (intstance == null) {
            synchronized (RxBus.class) {
                if (intstance == null) {
                    intstance = new RxBus();
                }
            }
        }
        return intstance;
    }

    public void send(Object object) {
        bus.onNext(object);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        Log.i("RXBus", "------" + bus.ofType(eventType));
        return bus.ofType(eventType);
    }

    public Observable<Object> toObservable() {
        return bus;
    }
}
