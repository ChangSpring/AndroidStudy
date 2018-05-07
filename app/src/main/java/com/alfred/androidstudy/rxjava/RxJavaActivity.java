package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alfred.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Observable：发射源，英文释义“可观察的”，在观察者模式中称为“被观察者”或“可观察对象”；
 * <p>
 * Observer：接收源，英文释义“观察者”，没错！就是观察者模式中的“观察者”，可接收Observable、Subject发射的数据；
 * <p>
 * Subject：Subject是一个比较特殊的对象，既可充当发射源，也可充当接收源，AsyncSubjuect BehaviorSubject PushlishSubject ReplaySubject
 * <p>
 * Subscriber：“订阅者”，也是接收源，那它跟Observer有什么区别呢？
 * <p>
 * 1. Subscriber实现了Observer接口，比Observer多了一个最重要的方法unsubscribe( )，用来取消订阅，当你不再想接收数据了，可以调用unsubscribe( )方法停止接收;
 * <p>
 * 2. Subscriber在执行完onCompleted()方法执行完毕后是否取消了订阅.Observer
 * 在subscribe() 过程中,最终也会被转换成 Subscriber 对象，一般情况下，建议使用Subscriber作为接收源；
 * <p>
 * Subscription ：Observable调用subscribe( )方法返回的对象，同样有unsubscribe( )方法，可以用来取消订阅事件；
 * <p>
 * Action0：RxJava中的一个接口，它只有一个无参call（）方法，且无返回值，同样还有Action1，Action2...Action9等，Action1封装了含有 1 个参的call（）方法，即call（T t），Action2封装了含有 2 个参数的call方法，即call（T1 t1，T2
 * t2），以此类推；
 * <p>
 * Func0：与Action0非常相似，也有call（）方法，但是它是有返回值的，同样也有Func0、Func1...Func9;
 * <p>
 * 关于observeOn()和SubscribeOn()的使用和区别
 * 1. 下面提到的“操作”包括产生事件、用操作符操作事件以及最终的通过 subscriber 消费事件；
 * 2. 只有第一subscribeOn() 起作用（所以多个 subscribeOn() 无意义）；
 * 3. 这个 subscribeOn() 控制从流程开始的第一个操作，直到遇到第一个 observeOn()；
 * 4. observeOn() 可以使用多次，每个 observeOn() 将导致一次线程切换()，这次切换开始于这次 observeOn() 的下一个操作；
 * 5. 不论是 subscribeOn() 还是 observeOn()，每次线程切换如果不受到下一个 observeOn() 的干预，线程将不再改变，不会自动切换到其他线程。
 */
public class RxJavaActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView mTextView;

    public static final String TAG = RxJavaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        ButterKnife.bind(this);

        test1();
        test2();
    }

    private static void test1() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello RxJava");
                subscriber.onCompleted();
            }
        });
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String value) {
                System.out.println("onNext value : " + value);
            }
        };

        Subscription subscription = observable.subscribe(subscriber);
        System.out.println("1.subscriber.isUnsubscribed() ? " + subscriber.isUnsubscribed());
        System.out.println("1.subscription.isUnsubscribed() ? " + subscription.isUnsubscribed());
        System.out.println("----------------");

        observable.subscribe(subscriber);//重新建立订阅关系

        System.out.println("*****************");

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Second");
                subscriber.onCompleted();
            }
        }).subscribe(subscriber);
    }

    private static void test2() {
        Observable observable = Observable.just("Hello RxJava");
        Subscriber subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String value) {
                System.out.println("onNext value : " + value);
            }
        };

        Subscription subscription = observable.subscribe(subscriber);
        System.out.println("2.subscriber.isUnsubscribed() ? " + subscriber.isUnsubscribed());
        System.out.println("2.subscription.isUnsubscribed() ? " + subscription.isUnsubscribed());
        System.out.println("----------------");

        observable.subscribe(subscriber);

        System.out.println("*****************");

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Second");
                subscriber.onCompleted();
            }
        }).subscribe(subscriber);
    }


}

