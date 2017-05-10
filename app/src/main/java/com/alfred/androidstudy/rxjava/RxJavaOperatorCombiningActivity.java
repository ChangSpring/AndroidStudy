package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alfred.androidstudy.R;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * 组合操作符
 */
public class RxJavaOperatorCombiningActivity extends AppCompatActivity {

    private static final String TAG = "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_combining);

//        merge();
//        zip();
//        combineLatest();
//        join();
        switchOnNext();
    }

    private void switchOperator() {

    }

    /**
     * merge操作符,将2-9个Observable合并到一个Observable中进行发射,合并后的数据可能是无序的(如果想要没有顺序错乱,可以使用concat操作符)
     */
    private void merge() {
        Observable<String> observable1 = Observable.just("A", "B", "C", "D", "E", "F");
        Observable<Integer> observable2 = Observable.just(1, 2, 4, 5, 6, 7, 8);

        Observable.merge(observable1, observable2)
                .subscribe(new Action1<Serializable>() {
                    @Override
                    public void call(Serializable serializable) {
                        Log.i(TAG, "merge = " + serializable);
                    }
                });
    }


    /**
     * zip操作符将多个Observable发射的数据按顺序组合起来,每个数据只能组合一次,而且都是有序的 最终组合的数据的数量由发射数据最少的Observable来决定
     */
    private void zip() {
        Observable.zip(createObserver(2), createObserver(3), createObserver(4), new Func3<String, String, String, String>() {
            @Override
            public String call(String s, String s2, String s3) {
                return s + "-" + s2 + "-" + s3;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Logger.d("zip operator :" + s);
            }
        });
    }

    private void zipWith() {
        createObserver(2).zipWith(createObserver(3), new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + "-" + s2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Logger.d("zipWith operator : " + s);
            }
        });
    }

    private Observable<String> createObserver(final int index) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= index; i++) {
                    Logger.i("emitted : " + index + "-" + i);
                    subscriber.onNext(index + "-" + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

    /**
     * combineLatest
     * combineLatest操作符把两个observable产生的结果进行合并,合并的结果组成一个新的Observable
     * 这两个Observable中任意一个Observable产生的结果,都和另一个Observable最后产生的结果,按照一定的规则进行合并
     * combinLatest会使用第一个Observable的最后一个元素(剩下的抛弃)跟第二个Observable的所有元素分别一一合并
     */
    private void combineLatest() {
        Observable<String> observable1 = Observable.just("A", "B", "C");
        Observable<Integer> observable2 = Observable.just(1, 2, 4, 5);

        Observable.combineLatest(observable1, observable2, new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                return s + integer;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "combinLatest = " + s);
            }
        });
    }

    /**
     * join 类似于combineLatest操作符 但是根据join操作符可以控制每个Observable产生结果的生命周期,在每个结果的生命周期内,
     * 可以与另一个Observable产生的的结果按照一定的规则进行合并
     * <p>
     * 一句话概括:在ObservableA的生命周期内,observableB输出的数据项与ObservableA输出的数据项进行合并
     * <p>
     * 统一线程:ObservableA的生命周期已经执行完了,ObservableB的还没有出来,所以合并不了
     */
    private static void join() {
        Observable<Integer> observableA = Observable.range(1, 5);
        Observable<Integer> observableB = Observable.from(Arrays.asList(6, 7, 8, 9, 0));
        observableA.join(observableB, new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
//                return Observable.just(integer);
                return Observable.just(integer).delay(1, TimeUnit.SECONDS);
            }
        }, new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                return Observable.just(integer);
            }
        }, new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "join = " + integer);
            }
        });
    }

    /**
     * switchOnNext操作符
     * switchOnNext操作符是把Observable转换成一个Observable
     */
    private void switchOnNext() {
        Observable.switchOnNext(Observable.just(Observable.range(0, 3), Observable.range(100, 3)))
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(TAG, "switchOnNext = " + integer);
                    }
                });
    }

    /**
     * startWith操作符
     * <p>
     * 在源Observable发射的数据前面插上一些数据,不仅仅只可以插入一些数据,还可以将Iterable和Observable插入
     * <p>
     * 如果插入的是Observable,则这个Observable发射的数据会插入到源Observable发射的数据前面
     */
    private void startWith() {
        Observable.just(0, 1, 3, 4).startWith(100, 300, 500)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(TAG, "STARTWITH = " + integer);
                    }
                });
    }

}
