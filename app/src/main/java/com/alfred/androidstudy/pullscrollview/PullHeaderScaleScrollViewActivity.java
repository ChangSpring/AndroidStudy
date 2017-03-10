package com.alfred.androidstudy.pullscrollview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PullHeaderScaleScrollViewActivity extends AppCompatActivity {
    @Bind(R.id.iv_header)
    ImageView headerIv;
    @Bind(R.id.sv_scroll)
    PullHeaderScaleScrollView mScrollView;
    @Bind(R.id.tl_content)
    TableLayout contentTl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_header_scale_scroll_view);

        ButterKnife.bind(this);

        showTable();

        mScrollView.setHeaderView(headerIv);

    }

    public void showTable() {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.leftMargin = 30;
        layoutParams.bottomMargin = 10;
        layoutParams.topMargin = 10;

        for (int i = 0; i < 30; i++) {
            TableRow tableRow = new TableRow(this);
            TextView textView = new TextView(this);
            textView.setText("Test pull down scroll view " + i);
            textView.setTextSize(20);
            textView.setPadding(15, 15, 15, 15);

            tableRow.addView(textView, layoutParams);
            if (i % 2 != 0) {
                tableRow.setBackgroundColor(Color.LTGRAY);
            } else {
                tableRow.setBackgroundColor(Color.WHITE);
            }

            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PullHeaderScaleScrollViewActivity.this,"click item",Toast.LENGTH_SHORT).show();
                }
            });

            contentTl.addView(tableRow);
        }
    }
}
