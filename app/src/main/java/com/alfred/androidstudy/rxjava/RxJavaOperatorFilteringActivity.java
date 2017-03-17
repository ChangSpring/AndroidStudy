package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * filter 过滤数据
 */
public class RxJavaOperatorFilteringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator_filtering);
    }


    /**
     * debounce操作符就是起到了限流的作用，可以理解为阀门，当你半开阀门的时候，水会以较慢的速度流出来。不同之处就是阀门里的水不会浪费掉，而debounce过滤掉的数据会被丢弃掉。在Rxjava中，将这个操作符分为了    * throttleWithTimeout和debounce两个操作符。
     * 先来看一下throttleWithTimeOut吧，这个操作符通过时间来限流，源Observable每次发射出来一个数据后就会进行计时，如果在设定好的时间结束前源Observable
     * 有新的数据发射出来，这个数据就会被丢弃，同时重新开始计时。如果每次都是在计时结束前发射数据，那么这个限流就会走向极端：只会发射最后一个数据。
     */
    private void debounce() {
        //每隔100毫秒发射一个数据，当要发射的数据是3的倍数的时候，下一个数据就延迟到300毫秒再发射
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    }
                    int sleep = 100;
                    if (i % 3 == 0) {
                        sleep = 300;
                    }
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }

        }).subscribeOn(Schedulers.computation())
                //使用throttleWithTimeOut来过滤一下这个Observable，我们设定的过滤时间是200毫秒，也就说发射间隔小于200毫秒的数据会被过滤掉
                .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Logger.i("throttleWithTimeout number = " + integer);
                    }
                });
    }

    /**
     * Distinct操作符的用处是用来去重,所有重复的数据都会被过滤掉
     * distinctUntilChanged是用来过滤掉连续的重复数据
     */
    private void distinct() {
        Observable.just(1, 2, 3, 3, 5, 6, 5, 1).distinct().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("distinct number = " + integer);
            }
        });

        Observable.just(1, 2, 3, 3, 3, 5, 5, 5, 6, 6).distinctUntilChanged().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("distinctUntilChanged number = " + integer);
            }
        });
    }

    /**
     * ElementAt 返回指定位置的数据
     */
    private void elementAt() {
        Observable.just(0, 1, 2, 3, 4, 5, 6).elementAt(2).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("elementAt number = " + integer);
            }
        });
    }

    /**
     * filter 返回满足过滤条件的数据
     */
    private void filter() {
        Observable.just(0, 1, 3, 4, 5, 6).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer < 3;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("filter number = " + integer);
            }
        });
    }

    /**
     * First操作符返回第一条数据,并且还可以返回满足条件的第一条数据
     * Last操作符返回最后一条满足条件的数据
     */
    private void firstObserver() {
        Observable.just(0, 1, 2, 3, 4, 5).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 1;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("first operator number = " + integer);
            }
        });

        //BlockingObservable方法,这个方法不会对Observable做任何处理，只会阻塞住，
        //当满足条件的数据发射出来的时候才会返回一个BlockingObservable对象。
        // 可以使用Observable.toBlocking或者BlockingObservable.from方法来将一个Observable对象转化为BlockingObservable对象。
        // BlockingObservable可以和first操作符进行配合使用
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!subscriber.isUnsubscribed()) {
                        Logger.i("onNext : " + i);
                        subscriber.onNext(i);
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        })
                //BlockingObservable则一直阻塞着，直到第一个大于1的数据发射出来
                .toBlocking()
                .first(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 1;
                    }
                });
    }


    /**
     *
     * Skip操作符将源Observable发射的数据过滤掉前n项，而Take操作符则只取前n项，理解和使用起来都很容易，但是用处很大。另外还有SkipLast和TakeLast操作符
     */
    private void skip(){
        Observable.just(0,1,2,3,4,5,6).skip(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Logger.i("skip operator number = " + integer);
                    }
                });

        Observable.just(0,1,2,3,4,5,6).take(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Logger.i("take operator number = " + integer);
                    }
                });
    }


    /**
     * sample操作符会定时地发射源Observable最近发射的数据,其他的都会被过滤掉
     */
    private void sampleObserver(){
        //sample操作符会每隔5个数字发射出一个数据来
        createObserver().sample(1000,TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("sample operator number = " + integer);
            }
        });
    }


    /**
     * ThrottleFirst操作符则会定期发射这个时间段里源Observable发射的第一个数据
     */
    private void throttleFirst(){
        //throttleFirst则会每隔5个数据发射第一个数据
        createObserver().throttleFirst(1000,TimeUnit.MILLISECONDS).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("throttleFirst operator number =  " + integer);
            }
        });
    }

    private Observable<Integer> createObserver(){
        return Observable.create(new Observable.OnSubscribe<Integer>(){

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for(int i = 0;i < 20;i++){
                    try{
                        Thread.sleep(200);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
    }






}
