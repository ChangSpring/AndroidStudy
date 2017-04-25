package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

/**
 * Observable转化
 * 我们可能需要将创建的Observable安装某种规则转化一下来发射数据
 */
public class RxJavaOperatorObservableTransformActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator_observable_transform);
    }

    /**
     * buffer操作符所要做的事情就是将数据安装规定的大小做一下缓存，然后将缓存的数据作为一个集合发射出去
     */
    private void buffer() {
        //buffer(2,3)会每3个数据发射一个包含两个数据的集合
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).buffer(2, 3).subscribe(new Action1<List<Integer>>() {
            @Override
            public void call(List<Integer> integers) {
                Logger.d("buffer skip :" + integers);
            }
        });

        //buffer(3,TimeUnit.SECONDS)会每隔三秒钟缓存发射一次
        Observable.interval(1, TimeUnit.SECONDS)
                .buffer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Long>>() {
                    @Override
                    public void call(List<Long> longs) {
                        Logger.d("buffer time : " + longs);
                    }
                });
    }

    /**
     * GroupBy操作符将原始Observable发射的数据按照key来拆分成一些小的Observable,然后这些小的Observable分别发射其所包含的数据,类似于sql里面的groupBy
     * 在使用中,需要提供一个生成key的规则,所有key相同的数据会包含在同一个小的Observable中,另外我们还可以提供一个函数对这些数据进行转化
     */
    private void groupBy() {
        //奇偶分组
        final Observable groupByObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .groupBy(new Func1<Integer, Object>() {
                    @Override
                    public Object call(Integer integer) {
                        return integer % 2;
                    }
                });
        groupByObservable.subscribe(new Subscriber<GroupedObservable<Integer, Integer>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                integerIntegerGroupedObservable.count().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Logger.i("key = " + integerIntegerGroupedObservable.getKey() + " contains = " + integer + " numbers");
                    }
                });
            }
        });

    }

    /**
     * map 变换
     */
    private void map() {
        Observable.just(1, 2, 3, 4, 5, 6, 6, 7, 8, 9)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer * 10;
                    }
                }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Logger.i("map number = " + integer);
            }
        });
    }

    /**
     * cast 将Observable发射的数据强制转化成另外的一种类型,属于Map的一种具体实现
     */
    private void cast() {

    }

    /**
     * Scan操作符对一个序列的数据应用一个函数,并将这个函数的结果发射出去作为下个数据,应用这个函数时候的第一个参数使用,有点类似于递归操作
     */
    private void scan() {
        //2的n次方
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(2);
        }
        Observable.from(list)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer * integer2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Logger.i("scan number = " + integer);
                    }
                });
    }


}
