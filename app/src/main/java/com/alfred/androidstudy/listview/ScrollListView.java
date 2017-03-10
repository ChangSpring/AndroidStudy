package com.alfred.androidstudy.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by Alfred on 2017/2/17.
 */

public class ScrollListView extends ListView {
    private int mLastX;
    private View itemRoot;
    private View preItemRoot;
    private int maxLength;
    private final int MAX_SCROLLABLE_WIDTH = 200;

    private ScrollItemView mCurView;

    private Scroller mPreScroller;
    private Scroller mScroller;

    private static final String TAG = ScrollListView.class.getSimpleName();

    public ScrollListView(Context context) {
        this(context, null);
    }

    public ScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(context, new LinearInterpolator(context, attrs));
//        mPreScroller = new Scroller(context, new LinearInterpolator(context, attrs));
//        maxLength = dipToPx(context, MAX_SCROLLABLE_WIDTH);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int position = pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    ScrollListViewAdaper.DataHolder dataHolder = (ScrollListViewAdaper.DataHolder) getItemAtPosition(position);
                    mCurView = dataHolder.rootLl;
                    Log.i(TAG, dataHolder.title);
                }
//                if (preItemRoot != null) {
//                    int preScrollerX = preItemRoot.getScrollX();
//                    mPreScroller.startScroll(preScrollerX, 0, 0 - preScrollerX, 0);
//                }
                break;
//            case MotionEvent.ACTION_MOVE:
//                int scrollX = itemRoot.getScrollX();
//                int newScrollX = scrollX + mLastX - x;
//                if (newScrollX < 0) {
//                    newScrollX = 0;
//                } else if (newScrollX > maxLength) {
//                    newScrollX = maxLength;
//                }
//
//                itemRoot.scrollTo(newScrollX, 0);
//                break;
//            case MotionEvent.ACTION_UP:
//                scrollX = itemRoot.getScrollX();
//                if (scrollX > maxLength / 2) {
//                    newScrollX = maxLength;
//                } else {
//                    newScrollX = 0;
//                }
//
//                preItemRoot = itemRoot;
//
//                mScroller.startScroll(scrollX, 0, newScrollX - scrollX, 0);
//                break;
        }
//        mLastX = x;
        if (mCurView != null) {
            mCurView.handlerTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

//    private int dipToPx(Context context, int dip) {
//        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
//    }

//    @Override
//    public void computeScroll() {
//        if (mScroller.computeScrollOffset()) {
//            itemRoot.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//        }
//        if (mPreScroller.computeScrollOffset()) {
//            preItemRoot.scrollTo(mPreScroller.getCurrX(), mPreScroller.getCurrY());
//        }
//        invalidate();
//    }

}
