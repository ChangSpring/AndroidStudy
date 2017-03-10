package com.alfred.androidstudy.pullscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import java.util.Arrays;
import java.util.LinkedList;

public class PullScrollListViewActivity extends AppCompatActivity {
    private String[] mStrings = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
            "Allgauer Emmentaler"};
    private LinkedList<String> mListItems;
    private PullScrollListView mListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_list_view);

        mListView = (PullScrollListView) findViewById(R.id.pull_list_view);
        mListItems = new LinkedList<>();
        mListItems.addAll(Arrays.asList(mStrings));
        mAdapter = new ArrayAdapter<>(this,R.layout.item_layout, mListItems);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.headerview,mListView,false);
        //设置headerView不可点击
        mListView.addHeaderView(view,null,false);

        ImageView headerView = (ImageView)findViewById(R.id.background_img);
        mListView.setTopView(headerView);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"position"+position,Toast.LENGTH_LONG).show();
            }
        });
    }
}
