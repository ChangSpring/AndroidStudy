package com.alfred.androidstudy;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;

/**
 * @author :  Alfred
 * @date : 2019/1/30 16:39
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_test;
    }
}
