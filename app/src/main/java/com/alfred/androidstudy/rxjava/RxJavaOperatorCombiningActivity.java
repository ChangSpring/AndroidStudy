package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

public class RxJavaOperatorCombiningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_operator_combining);
    }

    /**
     * startWith操作符会在源Observable发射的数据前面插上一些数据,不仅仅只可以插入一些数据,还可以将Iterable和Observable插入
     * 如果插入的是Observable,则这个Observable发射的数据会插入到源Observable发射的数据前面
     */
    private void startWith(){

    }

    private void switchOperator(){

    }

    /**
     * zip操作符将多个Observable发射的数据按顺序组合起来,每个数据只能组合一次,而且都是有序的 最终组合的数据的数量由发射数据最少的Observable来决定
     */
    private void zip(){
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

    private void zipWith(){
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

    private Observable<String> createObserver(final int index){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= index;i++){
                    Logger.i("emitted : " + index + "-" + i);
                    subscriber.onNext(index + "-" + i);
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
