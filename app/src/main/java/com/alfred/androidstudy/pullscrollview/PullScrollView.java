package com.alfred.androidstudy.pullscrollview;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 下拉header图片变大的效果
 * Created by Alfred on 2017/2/23.
 */

public class PullScrollView extends ScrollView {
    private View mHeaderView;
    private View mContentView;

    //按下ScrollView时的x,y的值
    private Point mTouchPoint;

    //记录按下scrollview时 header和content 的left,right,top,bottom 的值
    private Rect mHeaderInitRect;
    private Rect mContentInitRect;

    //记录是否下拉view的位置改变,如果改变就回弹
    private boolean mIsMoving = false;
    //TODO 是否禁止控件本身的的移动(说实话,没看懂)
    private boolean mEnableMoving = false;
    //是否从初始化的状态拖动
    private boolean mIsLayout = false;

    //记录move的时候的实时top和bottom
    private int mHeaderCurTop, mHeaderCurBottom;
    private int mContentCurTop, mContentCurBottom;

    /**
     * 阻尼系数,数值越小阻力越大
     */
    private final float SCROLL_RATIO = 0.5f;
    /**
     * headerView最大放大倍数
     */
    private final float SCALE_TATIO = 1.5f;

    private static final String TAG = PullScrollView.class.getSimpleName();


    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //当TableLayout的一个item添加onClick监听事件时,不执行onTouchEvent()方法中的MotionEvent.ACTION_DOWN
            //记录按下时的位置信息
            mTouchPoint = new Point((int) ev.getX(), (int) ev.getY());
            mHeaderInitRect = new Rect(mHeaderView.getLeft(), mHeaderView.getTop(), mHeaderView.getRight(), mHeaderView.getBottom());
            mContentInitRect = new Rect(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());

            mIsMoving = false;
            //如果当前不是从初始化位置开始滚动的话，就不让用户拖拽
            if (getScrollY() == 0) {
                mIsLayout = true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            //如果当前的事件是我们要处理的事件时，比如现在的下拉，这时候，我们就不能让子控件来处理这个事件
            //这里就需要把它截获，不传给子控件，更不能让子控件消费这个事件
            //不然子控件的行为就可能与我们的相冲突
            int deltaY = (int) ev.getY() - mTouchPoint.y;
            deltaY = deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY;
            if (deltaY > 0 && deltaY >= getScrollY()) {
                onTouchEvent(ev);
                return true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //下拉
                //手指的滑动距离
                int deltaY = (int) ev.getY() - mTouchPoint.y;
                //手指的滑动距离最大值为headeriew的高度
                deltaY = deltaY < 0 ? 0 : (deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY);

                Log.i(TAG, "deltaY =  " + deltaY + " headeriew height  = " + mHeaderView.getHeight());
//                Log.i(TAG, "deltaY = " + deltaY);
//                Log.i(TAG, "get scroll Y = " + getScrollY());
                //如果是下滑并且手指的移动距离大于滚动距离
                if (deltaY > 0 && deltaY >= getScrollY() && mIsLayout) {
                    //headerView实际的移动距离
                    float headerMoveHeight = deltaY * 0.5f * SCROLL_RATIO;

                    //记录headerView的top bottom
                    mHeaderCurTop = (int) (headerMoveHeight + mHeaderInitRect.top);
                    mHeaderCurBottom = (int) (headerMoveHeight + mHeaderInitRect.bottom);

                    //记录contentView的top bottom
                    mContentCurTop = (int) (headerMoveHeight + mContentInitRect.top);
                    mContentCurBottom = (int) (headerMoveHeight + mContentInitRect.bottom);

                    //此判断是控制下滑时headerView和contentView要一直连接着,不能断开
                    if (mContentCurTop <= mHeaderCurBottom) {
                        mHeaderView.layout(mHeaderInitRect.left, mHeaderCurTop, mHeaderInitRect.right, mHeaderCurBottom);
                        mContentView.layout(mContentInitRect.left, mContentCurTop, mContentInitRect.right, mContentCurBottom);

                        mIsMoving = true;
                        mEnableMoving = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsMoving) {
                    mHeaderView.layout(mHeaderInitRect.left, mHeaderInitRect.top, mHeaderInitRect.right, mHeaderInitRect.bottom);
                    TranslateAnimation headerAnimation = new TranslateAnimation(0, 0, mHeaderCurTop - mHeaderInitRect.top, 0);
                    headerAnimation.setDuration(200);
                    mHeaderView.startAnimation(headerAnimation);

                    mContentView.layout(mContentInitRect.left, mContentInitRect.top, mContentInitRect.right, mContentInitRect.bottom);
                    TranslateAnimation contentAnimation = new TranslateAnimation(0, 0, mContentCurTop - mContentInitRect.top, 0);
                    contentAnimation.setDuration(200);
                    mContentView.startAnimation(contentAnimation);

                    mIsMoving = false;
                }
                mEnableMoving = false;
                mIsLayout = false;
                break;
            default:
                break;
        }
        return mEnableMoving || super.onTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
        super.onFinishInflate();
    }
}
