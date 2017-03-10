package com.alfred.androidstudy.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Alfred on 2017/2/20.
 */

public class ScrollItemView extends LinearLayout {
    private Context mContext;
    private int mLastX = 0;
    private final int MAX_WIDTH = 200;
    private Scroller mScroller;

    private static final String TAG = ScrollItemView.class.getSimpleName();

    public ScrollItemView(Context context) {
        this(context, null);
    }

    public ScrollItemView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public ScrollItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        mScroller = new Scroller(context,new LinearInterpolator(context, attrs));

    }

    public void handlerTouchEvent(MotionEvent ev){
        int maxLength = dipToPx(mContext,MAX_WIDTH);
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int scrollX = this.getScrollX();

                Log.i(TAG,"scroll x = " + scrollX);

                int newScrollX = scrollX + mLastX - x;
                if (newScrollX < 0) {
                    newScrollX = 0;
                } else if (newScrollX > maxLength) {
                    newScrollX = maxLength;
                }

                this.scrollTo(newScrollX, 0);
                break;
            case MotionEvent.ACTION_UP:
                scrollX = this.getScrollX();
                if (scrollX > maxLength / 2) {
                    newScrollX = maxLength;
                } else {
                    newScrollX = 0;
                }

                mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
                invalidate();
                break;
        }
        mLastX = x;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            this.scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
        }
        invalidate();
    }

    private int dipToPx(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

}
