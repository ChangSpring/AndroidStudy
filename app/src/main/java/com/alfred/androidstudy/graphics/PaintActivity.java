package com.alfred.androidstudy.graphics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaintActivity extends AppCompatActivity {
    @Bind(R.id.paint_view)
    PaintView mPaintView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        ButterKnife.bind(this);

        mPaintView.startAnimator();
    }
}
