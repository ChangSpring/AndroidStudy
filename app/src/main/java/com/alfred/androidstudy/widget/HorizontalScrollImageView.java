package com.alfred.androidstudy.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.alfred.androidstudy.R;

/**
 * Created by alfred on 2018/5/7.
 */

public class HorizontalScrollImageView extends AppCompatImageView {

    private int mWidth, mHeight;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Rect mSrcRect, mDestRect;

    public HorizontalScrollImageView(Context context) {
        this(context, null);
    }

    public HorizontalScrollImageView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HorizontalScrollImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.checkbox);

        Log.i("Size", "widht = " + mBitmap.getWidth() + "  " + mBitmap.getHeight());

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mSrcRect = new Rect();
        mDestRect = new Rect();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //srcRect绘制Bitmap的哪一部分   destRect绘制的Bitmap拉伸到哪里
        canvas.drawBitmap(mBitmap, mSrcRect, mDestRect, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        //此值的位置是相对于view的左边距,而不是距离屏幕的距离!!!!!!!
        mDestRect.top = 0;
        mDestRect.bottom = mHeight;
        //此值的位置是相对于view的左边距,而不是距离屏幕的距离!!!!!!!
        mSrcRect.top = 0;
        mSrcRect.bottom = mHeight;
    }


    public void startTranslate() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentLeft = (Float) animation.getAnimatedValue();
                mSrcRect.left = mWidth - (int) (mWidth * currentLeft);
                mSrcRect.right = mWidth;
                //此值的位置是相对于view的左边距,而不是距离屏幕的距离!!!!!!!
                mDestRect.left = 0;
                mDestRect.right = (int) (mWidth * currentLeft);

                postInvalidate();

            }
        });
    }
}
