package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;

/**
 * 辅助操作符
 * Created by Alfred on 2017/5/9.
 */

public class RxJavaOperatorAssistActivity extends AppCompatActivity {
    public static final String TAG = "Activity ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void delay(){
        Observable.interval(1, TimeUnit.SECONDS).take(4)
                .delay(3,TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.i(TAG,"delay = " + aLong);
                    }
                });
    }

}
