package com.alfred.androidstudy.listview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScrollListViewActivity extends AppCompatActivity {
    @Bind(R.id.list_view)
    ScrollListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_list_view);

        ButterKnife.bind(this);

        ScrollListViewAdaper adaper = new ScrollListViewAdaper(this);
        mListView.setAdapter(adaper);



    }
}
