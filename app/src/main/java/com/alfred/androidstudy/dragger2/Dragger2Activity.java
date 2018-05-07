package com.alfred.androidstudy.dragger2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alfred.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dragger2Activity extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragger2);

        ButterKnife.bind(this);


    }
}
