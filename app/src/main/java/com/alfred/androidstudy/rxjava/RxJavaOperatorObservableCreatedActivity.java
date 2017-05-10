package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;
import com.orhanobut.logger.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

/**
 * 创建Observable的多种方式
 * 其原理就是创建一个Observable对象来干活，然后使用各种操作符建立起来的链式操作，就如同流水线一样把你想要处理的数据一步一步地加工成你想要的成品然后发射(emit)给Subscriber(Observer)
 */
public class RxJavaOperatorObservableCreatedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator);


    }

    /**
     * create操作符 在RxJava的subscribe过程中,Observer会先被转换成一个Subscriber再使用
     */
    private void createOperator() {
        rx.Observable<Integer> observable = rx.Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 5; i++) {
                        int temp = new Random().nextInt(10);
                        if (temp > 8) {
                            subscriber.onError(new Throwable("value > 8"));
                            break;
                        } else {
                            subscriber.onNext(temp);
                        }
                        if (i == 4) {
                            subscriber.onCompleted();
                        }
                    }
                }
            }
        });

        observable.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Logger.d("create operator onCompleted ");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("create operator onError");
            }

            @Override
            public void onNext(Integer integer) {
                Logger.d("create operator onNext number =  " + integer);
            }
        });
    }

    /**
     * range操作符根据出入的初始值n和数目m发射一系列大于等于n的m个数值
     */
    private void rangeOperator() {
        //发送五个大于等于10的5个数字
        Observable<Integer> observable = Observable.range(10, 5);
        observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.d("range operator number = " + integer);
            }
        });
    }

    /**
     * defer操作符只有当有Subscriber来订阅的时候才会创建一个新的Observable对象,也就是说每次订阅都会得到一个刚创建的最新的Observable对象,这可以确保Observable对象里的数据是最新的
     */
    private void deferOperator() {
        Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return Observable.just(System.currentTimeMillis());
            }
        }).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Logger.d("defer operator long = " + aLong);
            }
        });
    }

    /**
     * just操作符将某个对象转化成Observable对象,并且将其发射出去并且一次发射出去,可以是一个数字,一个字符串,数组,Iterate对象等,是一种非常快捷的创建Observable对象的方法
     */
    private void justOperator() {
        Observable.just(System.currentTimeMillis()).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Logger.d("just operator along = " + aLong);
            }
        });
    }

    /**
     * From操作符用来将某个对象转化成Observable对象,并且依次将其内容发射出去,这个类似于just,但是just会将这个对象整个发射出去,比如说一个含有10个数字的数组,使用from就会发射十次,每次发射一个数字,而使用just会发射一次来将整个的数组发射出去
     */
    private void fromOperator() {
        String[] items = {"hello", "world", "!"};
        Observable.from(items).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Logger.i("from operator item = " + s);
            }
        });
    }

    /**
     * interval操作符所创建的Observable对象从0开始,每隔一段时间发射一个数字,需要注意的是每个对象是运行在computation Schedule,所以如果需要在view中显示结果,需要在主线程中订阅
     */
    private void intervalOperator() {
        //每隔一秒钟,发射一个数字
        Observable.interval(1, TimeUnit.SECONDS, Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Logger.i("interval operator number = " + aLong);
                    }
                });
    }

    /**
     * repeat会将一个Observable对象重新发射,我们可以指定其发射的次数
     */
    private void repeatOperator(){
        Observable.just(1).repeat(5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("repeat operator number = " + integer);
            }
        });
    }

    /**
     * timer会在指定时间后发射一个数字0,注意其也是运行在computation scheduler
     */
    private void timerOperator(){
        Observable.timer(1,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Logger.i("timer operator number = " + aLong);
                    }
                });
    }


}
