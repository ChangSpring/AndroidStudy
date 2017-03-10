package com.alfred.androidstudy.listview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alfred.androidstudy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScrollActivity extends AppCompatActivity {
    @Bind(R.id.scroll_by)
    Button scrollByBtn;
    @Bind(R.id.scroll_to)
    Button scrollToBtn;
    @Bind(R.id.reset)
    Button resetBtn;
    @Bind(R.id.hello)
    LinearLayout helloLl;
    @Bind(R.id.ll_scroll)
    LinearLayout scrollLl;

    private int mLastX;

    private static final String TAG = ScrollActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        ButterKnife.bind(this);


    }

    @OnClick(R.id.scroll_to)
    public void scrollTo() {
        helloLl.scrollTo(200, 200);
    }

    @OnClick(R.id.scroll_by)
    public void scrollBy() {
        helloLl.scrollBy(200, 200);
    }

    @OnClick(R.id.reset)
    public void reset() {
        helloLl.scrollTo(0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maxLength = dipToPx(this,MAX_WIDTH);
        int scrollX = scrollLl.getScrollX();
        int x = (int) event.getX();
        int newScrollX = scrollX + mLastX - x;
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (newScrollX < 0) {
                newScrollX = 0;
            } else if (newScrollX > maxLength) {
                newScrollX = maxLength;
            }
            scrollLl.scrollTo(newScrollX, 0);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            if (scrollX > maxLength / 2){
                newScrollX = maxLength;
            }else{
                newScrollX = 0;
            }
            scrollLl.scrollTo(newScrollX,0);
        }

        mLastX = x;
        return true;
    }

    private final int MAX_WIDTH = 200;

    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
