package com.alfred.androidstudy.pullscrollview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.alfred.androidstudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alfred on 2017/2/24.
 */

public class PullScrollListView extends ListView {
    //底部图片view
    private View mTopView;
    private List<View> mHeaderViews = new ArrayList<>();
    private int mContentMaxMoveHeight;

    //底部view,也就是listview
    private View mContentView = this;
    //初始点击位置
    private Point mTouchPoint = new Point();
    //初始headerview的初始化位置
    private Rect mHeaderInitRect = new Rect();
    //初始contentView的初始化位置
    private Rect mContentInitRect = new Rect();
    //标识当前view是否移动
    private boolean mIsMoving = false;
    //是否关闭listView的滑动
    private boolean mEnableTouch = false;

    private int mHeaderCurTop;
    private int mContentTop;

    /**
     * 阻尼系数,越小阻力就越大.
     */
    public static final float SCROLL_RATIO = 0.5f;

    private static final String TAG = PullScrollListView.class.getSimpleName();

    public PullScrollListView(Context context) {
        this(context, null);
    }

    public PullScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PullScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setOverScrollMode(OVER_SCROLL_IF_CONTENT_SCROLLS);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PullScrollView);
            if (ta != null) {
                mContentMaxMoveHeight = (int) ta.getDimension(R.styleable.PullScrollView_maxMoveHeight, -1);
                ta.recycle();
            }
        }
    }

    public void setTopView(View view) {
        mTopView = view;
    }

    /**
     * 获取当前滚动高度
     */
    private int getScrollHeight() {
        //获取headerView的个数
        int headerCount = getHeaderViewsCount();
        //获取当前可见item的位置(从第一个headerView开始计算,从0开始)
        int firstVisiblePosition = getFirstVisiblePosition();
        //当前滚动高度
        int scrollHeight = 0;
        //所有的headerView并没有全部滚出屏幕
        if (firstVisiblePosition < headerCount) {
            if (mHeaderViews.size() == 0) {
                new RuntimeException("error");
                return -1;
            }

            for (int i = 0; i <= firstVisiblePosition; i++) {
                View view = mHeaderViews.get(i);
                //此headerView只有部分滚出屏幕
                if (view != null && i == firstVisiblePosition) {
                    scrollHeight += (-view.getTop());
                } else if (i != firstVisiblePosition) {
                    scrollHeight += view.getHeight();
                }
            }
        } else {
            //所有的headeriew完全滚出屏幕
            if (mHeaderViews != null) {
                for (View view : mHeaderViews) {
                    scrollHeight += view.getHeight();
                }
            }

            //getChildAt(int index)中的index索引是从当前可见的位置开始的,当前第一个可见的item索引是0,不是从第一个headerView开始的!!!
            View itemView = getChildAt(0);
            if (itemView != null) {
                //划出屏幕的所有item的高度和
                scrollHeight += (firstVisiblePosition - headerCount) * itemView.getHeight();
            }

            scrollHeight += (-itemView.getTop());
        }
        return scrollHeight;
    }

    @Override
    public void addHeaderView(View v) {
        super.addHeaderView(v);
        if (v != null) {
            mHeaderViews.add(v);
        }
    }

    @Override
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        super.addHeaderView(v, data, isSelectable);
        if (v != null) {
            mHeaderViews.add(v);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //保存原始位置
            mTouchPoint.set((int) ev.getX(), (int) ev.getY());
            mHeaderInitRect.set(mTopView.getLeft(), mTopView.getTop(), mTopView.getRight(), mTopView.getBottom());
            mContentInitRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());

            mIsMoving = false;
            mEnableTouch = false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //手指的滑动距离
            int moveHeight = (int)ev.getY() - mTouchPoint.y;
            //当前的滚动高度
            int scrolledHeight = getScrollHeight();

            //下拉并且当前listview在顶部,即滚动距离为0时,才开始向下拉动
            if (moveHeight > 0 && scrolledHeight == 0){
                if (moveHeight > mContentMaxMoveHeight){
                    moveHeight = mContentMaxMoveHeight;
                }

                float headerMoveHeight = moveHeight * 0.5f * SCROLL_RATIO;
                float contentMoveHeight = moveHeight * SCROLL_RATIO;

                mHeaderCurTop = (int) (mHeaderInitRect.top + headerMoveHeight);
                mContentTop = (int) (mContentInitRect.top + contentMoveHeight);

                mTopView.layout(mHeaderInitRect.left,mHeaderCurTop,mHeaderInitRect.right,(int) (mHeaderInitRect.bottom + headerMoveHeight));
                mContentView.layout(mContentInitRect.left,mContentTop,mContentInitRect.right,(int) (mContentInitRect.bottom + contentMoveHeight));

                mIsMoving = true;
                mEnableTouch = true;
            }else{
                mEnableTouch = false;
            }

        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mIsMoving){
                mTopView.layout(mHeaderInitRect.left,mHeaderInitRect.top,mHeaderInitRect.right,mHeaderInitRect.bottom);
                TranslateAnimation headerAnimation = new TranslateAnimation(0,0,mHeaderCurTop - mHeaderInitRect.top,0);
                headerAnimation.setDuration(200);
                mTopView.startAnimation(headerAnimation);

                mContentView.layout(mContentInitRect.left,mContentInitRect.top,mContentInitRect.right,mContentInitRect.bottom);
                TranslateAnimation contentAnimation = new TranslateAnimation(0,0,mContentTop - mContentInitRect.top,0);
                contentAnimation.setDuration(200);
                mContentView.startAnimation(contentAnimation);

                mIsMoving = false;
            }
            mEnableTouch = false;
        }
        return mEnableTouch || super.onTouchEvent(ev);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int
            maxOverScrollY, boolean isTouchEvent) {
        Log.i(TAG, "deltaX = " + deltaX);
        Log.i(TAG, "deltaY = " + deltaY);
        Log.i(TAG, "scrollX = " + scrollX);
        Log.i(TAG, "scrollY = " + scrollY);
        Log.i(TAG, "maxOverScrollX = " + maxOverScrollX);
        Log.i(TAG, "maxOverScrollY = " + maxOverScrollY);
        Log.i(TAG, "isTouchEvent = " + isTouchEvent);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }
}
