package com.alfred.androidstudy.rxjava;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.alfred.androidstudy.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Alfred on 2017/5/8.
 */

public class RxJavaWarActivity extends AppCompatActivity {
    @Bind(R.id.tv_content_rxjava_war)
    TextView contentTv;

    public static final String TAG = "RxJavaWarActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_war);

        ButterKnife.bind(this);


        getPngFileList();
    }

    /**
     * 编译sd卡获取后缀名为.png的文件名集合
     */
    private void getPngFileList(){
        final StringBuilder stringBuilder = new StringBuilder();
        String basePath = Environment.getExternalStorageDirectory().getPath();
        Log.i(TAG,"path = " + basePath);
        File rootFile = new File(basePath);

        //创建Observable
        Observable.just(rootFile)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        Log.i(TAG,"size = " + file.getPath());
                        return listFiles(file);
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().endsWith(".png");
                    }
                })
                .map(new Func1<File, String>() {
                    @Override
                    public String call(File file) {
                        return file.getName();
                    }
                })
//                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        contentTv.setText(stringBuilder.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        stringBuilder.append(s + "\n");
                    }
                });

    }

    /**
     * 递归遍历,判断该file是否为路径
     * @param file
     * @return
     */
    private Observable<File> listFiles(File file){
        if (file.isDirectory()){
            return Observable.from(file.listFiles())
                    .flatMap(new Func1<File, Observable<File>>() {
                        @Override
                        public Observable<File> call(File file) {
                            return listFiles(file);
                        }
                    });

        }else{
            return Observable.just(file);
        }
    }
}
