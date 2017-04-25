package com.alfred.androidstudy.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

/**
 * Observable：发射源，英文释义“可观察的”，在观察者模式中称为“被观察者”或“可观察对象”；
 * <p>
 * Observer：接收源，英文释义“观察者”，没错！就是观察者模式中的“观察者”，可接收Observable、Subject发射的数据；
 * <p>
 * Subject：Subject是一个比较特殊的对象，既可充当发射源，也可充当接收源，AsyncSubjuect BehaviorSubject PushlishSubject ReplaySubject
 * <p>
 * Subscriber：“订阅者”，也是接收源，那它跟Observer有什么区别呢？Subscriber实现了Observer接口，比Observer多了一个最重要的方法unsubscribe( )，用来取消订阅，当你不再想接收数据了，可以调用unsubscribe( )方法停止接收，Observer
 * 在subscribe() 过程中,最终也会被转换成 Subscriber 对象，一般情况下，建议使用Subscriber作为接收源；
 * <p>
 * Subscription ：Observable调用subscribe( )方法返回的对象，同样有unsubscribe( )方法，可以用来取消订阅事件；
 * <p>
 * Action0：RxJava中的一个接口，它只有一个无参call（）方法，且无返回值，同样还有Action1，Action2...Action9等，Action1封装了含有 1 个参的call（）方法，即call（T t），Action2封装了含有 2 个参数的call方法，即call（T1 t1，T2
 * t2），以此类推；
 * <p>
 * Func0：与Action0非常相似，也有call（）方法，但是它是有返回值的，同样也有Func0、Func1...Func9;
 */
public class RxJavaActivity extends AppCompatActivity {

    @Bind(R.id.textView)
    TextView mTextView;

    private static final String TAG = RxJavaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        ButterKnife.bind(this);

        final RxBus rxBus = RxBus.getInstance();
        rxBus.send(new Course("helll"));

//        RxBus.getInstance().toObservable(Course.class)
//                .subscribe(new Action1<Course>() {
//                    @Override
//                    public void call(Course course) {
//                        Log.i(TAG, "textview " + course.getName());
//                        mTextView.setText(course.getName());
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.i(TAG,"error : "+ throwable);
//                    }
//                });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                rxBus.toObservable().subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//                        if (o instanceof Course) {
//                            Course course = (Course) o;
//                            Log.i(TAG, "textview " + course.getName());
//                            mTextView.setText(course.getName());
//                        }
//                    }
//                });

//                studyAsyncSubject();
//                studyBehaviorSubject();
//                studyPublishSubject();
                studyReplaySubject();
            }
        });
    }

    /**
     * AsyncSubject无论输入多少个参数,永远只是输出最后一个参数
     */
    private void studyAsyncSubject() {
        //无论订阅的时候AsyncSubject是否Completed,均可以收到最后一个值的回调
        AsyncSubject<Integer> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext(1);
        asyncSubject.onNext(2);
        asyncSubject.onNext(3);
        asyncSubject.onCompleted();
        asyncSubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mTextView.setText("" + integer);
            }
        });
    }

    /**
     * BehaviorSubject发送离订阅最近的上一个值,没有上一个值的时候会发送默认值
     */
    private void studyBehaviorSubject() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create(-1);
        behaviorSubject.onNext(1);
        behaviorSubject.onNext(2);
        behaviorSubject.onNext(3);
        behaviorSubject.onCompleted();
        behaviorSubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mTextView.setText("" + integer);
            }
        });
    }

    /**
     * PublishSubject从哪里订阅就从哪里发送数据
     */
    public void studyPublishSubject() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);
        publishSubject.onCompleted();
        publishSubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mTextView.setText(" " + integer);
            }
        });
    }

    /**
     * 无论何时订阅,都将会历史所有订阅内容全部发出
     */
    public void studyReplaySubject() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);
        replaySubject.onCompleted();
        replaySubject.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                mTextView.setText(" " + integer);
            }
        });
    }


    private void studyRxJava() {

        //创建Observe 即观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onNext(String value) {
                Log.i(TAG, "onNext" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "OnError");
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "onComplete");
            }
        };

        //在RxJava的subscribe过程中,Observer会先被转换成一个Subscriber再使用
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onNext(String s) {
                Log.i(TAG, "OnNext");
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "OnError");
            }

            @Override
            public void onCompleted() {
                Log.i(TAG, "OnComplete");
            }
        };

        //创建Observable 即被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext(" World");
                subscriber.onNext(" !");
                subscriber.onCompleted();
            }
        });

        //创建Observable的其他方式

        //just(T...)
        Observable observable1 = Observable.just("Hello", " World", " !");

        //from(T[])
        String[] words = {"Hello", "World", "!"};
        Observable observable2 = Observable.from(words);

        //Subscribe 订阅

        observable.subscribe(observer);
        observable.subscribe(subscriber);

        //通过Action实现不完整回调

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "s = " + s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, "throwable");
            }
        };

        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {
                Log.i(TAG, "completed");
            }
        };

        observable.subscribe(onNextAction);

        observable.subscribe(onNextAction, onErrorAction);

        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

        //场景示例

        String[] names = {"zhangsan", "lisi", "wangwu"};

        //打印字符串数组
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "s = " + s);
            }
        });

        //由id取得图片并显示
        final int drawableRes = R.drawable.circle1;
        final ImageView imageView = new ImageView(this);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getTheme().getDrawable(drawableRes);
//                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(RxJavaActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
//                imageView.setImageDrawable(drawableRes);
            }
        });

        //Scheduler 线程调度

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) //指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
                .observeOn(AndroidSchedulers.mainThread()) //指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(TAG, "number = " + integer);
                    }
                });


        //变换
        //所谓变换,就是将事件序列中的对象或整个序列进行加工处理,转换成不同的事件或事件序列
        //Func1类和Action1类非常相似
        //Func1类和Action的区别在于,Func1包装的是有返回值的方法

        //map()变换

//        Observable.just("images/logo.png")
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String s) {
//                        return getBitmapFromPath(s);
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        showBitmap(bitmap);
//                    }
//                });

        //flatMap()
        Subscriber<Course> subscriber1 = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.i(TAG, "name = " + course.getName());
            }
        };

//        Observable.from(names)
//                .flatMap(new Func1<String, Observable<Course>>() {
//                    @Override
//                    public Observable<Course> call(String s) {
//                        return Observable.from(s.getCourse());
//                    }
//                })
//                .subscribe(subscriber1);

//        networkClient.token() // 返回 Observable<String>，在订阅时请求 token，并在响应后发送 token
//                .flatMap(new Func1<String, Observable<Messages>>() {
//                    @Override
//                    public Observable<Messages> call(String token) {
//                        // 返回 Observable<Messages>，在订阅时请求消息列表，并在响应后发送请求到的消息列表
//                        return networkClient.messages();
//                    }
//                })
//                .subscribe(new Action1<Messages>() {
//                    @Override
//                    public void call(Messages messages) {
//                        // 处理显示消息列表
//                        showMessages(messages);
//                    }
//                });

        //flatMap()和map()方法的区别:flatMap()也是把传入的参数转化之后返回另一个对象,但是和map()不同的是,flatMap()返回的是Observable对象,并且这个对象并不是直接发送到了Subscriber的回调方法中
        //map()是一对一的转化,flatMap()是一对多的转化


        //observerOn()指定的是Subscriber的线程,而这个Subscriber并不是Subscribe()参数中的Subscriber,而是observeOn()执行时的当前Observable所对应的Subscriber,即他的直接下级Subscriber.
        //换句话说,observeOn()指定的是它之后的操作所在的线程,因此如果有多次切换线程的需求,只要在每个想要切换线程的位置调用一次observeOn()即可

//        Observable.just(1,2,3,4) //IO线程 由 subscribeOn() 指定
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .map(mapOperator) //新线程 由 observerOn()指定
//                .observeOn(Schedulers.io())
//                .map(mapOperator2) //IO线程 由 observerOn() 指定
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber); //Android 主线程 由observeOn()指定


        //doOnSubscribe() 执行在 subscribe() 发生的线程；而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
//        Observable.create(onSubscribe)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        progressBar.setVisibility(View.VISIBLE); // 需要在主线程执行
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread()) // 指定主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);


        //compose

        //lift()是针对事件项和事件序列的 而compose()是针对Observable自身进行变换

        //Subject是一个比较特殊的对象,既可以充当发射源,也可以充当接收源,
    }


}

class Course {
    String name;

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
