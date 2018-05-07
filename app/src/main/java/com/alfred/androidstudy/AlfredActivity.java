package com.alfred.androidstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alfred.androidstudy.widget.HorizontalScrollTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlfredActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    HorizontalScrollTextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alfred);

        ButterKnife.bind(this);

        textview.init();
        textview.startScroll();

        textview.setOnScrollCompleteListener(new HorizontalScrollTextView.OnScrollCompleteListener() {
            @Override
            public void onScrollComplete() {
                Log.i("AlfredActivity","onScrollCompleted");
            }
        });

    }
}
