package com.alfred.androidstudy.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alfred.androidstudy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LayoutInflaterActivity extends AppCompatActivity {
    @BindView(R.id.root)
    LinearLayout rootLl;
    @BindView(R.id.click)
    TextView clickTv;

    private static final String TAG = LayoutInflaterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);

        ButterKnife.bind(this);

        //attachToRoot为true时,inflate()方法返回的是rootRl对象,为false时,inflate()返回的是add_layout root view 的对象,当add_layout的根布局是<merge>时,attachToRoot必须为true!
        View view = android.view.LayoutInflater.from(this).inflate(R.layout.add_layout, rootLl,true);
        Log.i(TAG,"view = " + view);
    }
}
