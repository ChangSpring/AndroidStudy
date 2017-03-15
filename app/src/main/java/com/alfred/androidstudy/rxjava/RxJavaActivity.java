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

                rxBus.toObservable().subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof Course){
                            Course course = (Course) o;
                            Log.i(TAG, "textview " + course.getName());
                            mTextView.setText(course.getName());
                        }
                    }
                });

            }
        });
    }


  private void studyRxJava(){

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
  }


}

class Course {
    String name;

    public Course(String  name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
