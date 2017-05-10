package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;
import com.orhanobut.logger.Logger;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * @author Alfred
 * error handling 操作符集中统一地处理错误
 */
public class RxJavaOperatorErrorHandlingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_operator_erro_handing);
    }

    /**
     * onErrorReturn操作符,当发生错误的时候,让Observable发射一个预先定义好的数据并正常停止
     */
    private void onErrorReturn(){
        createObservable(false).onErrorReturn(  new Func1<Throwable, String>() {
            @Override
            public String call(Throwable throwable) {
                return "onErrorReturn";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.d("onErrorReturn-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onErrorReturn-onError: " + e.getMessage());

            }

            @Override
            public void onNext(String s) {
                Logger.d("onErrorReturn-onNext:" + s);
            }
        });
    }

    /**
     * onErrorResume操作符,当发生错误的时候,由另外一个Observable来代替当前的Observable并继续发射数据
     */
    private void onErrorResume(){
        createObservable(false).onErrorResumeNext(Observable.just("7","8","9")).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.d("onErrorResumeNext-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onErrorResumeNext-onError: " + e.getMessage());

            }

            @Override
            public void onNext(String s) {
                Logger.d("onErrorResumeNext-onNext:" + s);
            }
        });
    }

    /**
     * OnExceptionResumeNext操作符
     * 类似于onErrorResumeNext
     * 区别是onErrorResumeNext操作符是当Observable发生错误时触发,而onExceptionResumeNext是当Observable发生异常时触发
     * 换句话说就是如果onError收到的Throwable不是一个Exception,他会将错误传递给观察者的onError方法,不会使用备用的Observable
     */
    private void onExceptionResumeNext(){
        createObservable(true).onExceptionResumeNext(Observable.just("7","8","9")).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.d("onException-true-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("onException-true-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Logger.d("onException-true-onNext:" + s);
            }
        });
    }

    /**
     * retry操作符在发生错误的时候会重新进行订阅,而且可以重复多次,所以发射的数据可能会产生重复,如果重复指定次数还有错误的话,就会将错误返回给观察者
     */
    private void retry(){
        createObservable().retry(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Logger.i("retry-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                Logger.i("retry-onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer s) {
                Logger.i("retry-onNext:" + s);
            }
        });
    }

    /**
     * retryWhen操作符,当错误发生时,retryWhen会接收onError的throwable作为参数,并根据定义好的函数返回一个Observable,如果这个Observable发射一个数据,就会重新订阅
     */
    private void retryWhen(){
        createObservable().retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
            @Override
            public Observable<?> call(Observable<? extends Throwable> observable) {
                return observable.zipWith(Observable.just(1, 2, 3), new Func2<Throwable, Integer, String>() {
                    @Override
                    public String call(Throwable throwable, Integer integer) {
                        return throwable.getMessage() + integer;
                    }
                }).flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Logger.d("retryWhen-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("retryWhen-onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer s) {
                Logger.d("retryWhen-onNext:" + s);
            }
        });

        //在尝试了几次还是产生错误后，retry会将错误分发给观察者，而retryWhen会正常结束，并不会讲错误分发出去
    }

    private rx.Observable<String> createObservable(final boolean createException){
        return rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= 6; i++){
                    if (i < 3){
                        subscriber.onNext("onNext : " + i);
                    }else if(createException){
                        subscriber.onError(new Exception("Exception"));
                    }else{
                        subscriber.onError(new Throwable("throw error"));
                    }
                }
            }
        });
    }

    private Observable<Integer> createObservable(){
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Logger.i("subscribe");
                for (int i = 0; i < 3; i ++){
                    if (i == 2){
                        subscriber.onError(new Exception("exception"));

                    }else{
                        subscriber.onNext(i);
                    }
                }
            }
        });
    }







}
