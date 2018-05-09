package com.alfred.androidstudy.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * 一张清明上河图横向自动滑动
 * Created by alfred on 2018/5/8.
 */

public class LargeTranslationImageView extends View {

    private BitmapRegionDecoder mDecoder;
    private int mImageWidth, mImageHeight;
    private int mViewWidth, mViewHeight;
    private volatile Rect mRect = new Rect();
    private static final BitmapFactory.Options mOptions = new BitmapFactory.Options();

    static {
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public LargeTranslationImageView(Context context) {
        this(context, null);
    }

    public LargeTranslationImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LargeTranslationImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRect.set(0, 0, 500, 500);
        mRect.left = 0;
        mRect.top = 0;

        mViewWidth = 1440;
        mViewHeight = 2464;
    }

    public void startHorizontalTranslateAnimation() {
        Logger.i("startHorizontalTranslateAnimation");
        if (mImageWidth == 0 || mViewWidth == 0 || mImageWidth < mViewWidth) {
            return;
        }

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(10000);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                mRect.left = (int) (mImageWidth * value);
                checkRectLeft();
                mRect.right = mRect.left + mViewWidth;

                postInvalidate();
            }
        });
    }

    public void setInputStream(InputStream inputStream) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            mImageWidth = options.outWidth;
            mImageHeight = options.outHeight;

            Logger.i("image width = " + mImageWidth + " image height = " + mImageHeight);

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkRectLeft() {
        if (mRect.left > mImageWidth - mViewWidth) {
            mRect.left = mImageWidth - mViewWidth;
        }
    }

    private void checkRectRight() {

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.i("onlayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap bitmap = mDecoder.decodeRegion(mRect, mOptions);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewHeight = h;
        mViewWidth = w;

        mRect.right = mViewWidth;
        mRect.bottom = mViewHeight < mImageHeight ? mViewHeight : mImageHeight;

        com.orhanobut.logger.Logger.i("width = " + w + " height = " + h);
    }
}
