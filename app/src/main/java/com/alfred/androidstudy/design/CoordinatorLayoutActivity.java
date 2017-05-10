package com.alfred.androidstudy.design;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoordinatorLayoutActivity extends AppCompatActivity {
    @Bind(R.id.fab_button_constraint_layout)
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);

        ButterKnife.bind(this);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "FAB", Snackbar.LENGTH_LONG)
                        .setAction("cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //这里的单击事件代表点击消除Action后的响应事件
                                Toast.makeText(CoordinatorLayoutActivity.this,"cancel",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

}
