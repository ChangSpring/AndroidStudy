package com.alfred.androidstudy;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by alfred on 2018/5/3.
 */

public class SplashView extends View {

    private Context mContext;
    //背景圆
    private Paint mBgCirclePaint;
    private RectF mBgCircleRect;

    private Paint mArcPaint;

    private Paint mPaint;

    private int mStartAngle;
    private int mSweepAngle;

    private int mWidth, mHeight;

    private ValueAnimator mValueAnimator;

    private RadialGradient mRadialGradient;

    private static final String TAG = SplashView.class.getSimpleName();

    private final ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener
            = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            Log.e(TAG, "value = " + value);
            invalidateSelf(value);
        }
    };

    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

//        init();

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(120,BlurMaskFilter.Blur.OUTER));
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE,null);

        mBgCirclePaint = new Paint();
        mBgCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.startblue));
        mBgCirclePaint.setAntiAlias(true);

        mArcPaint = new Paint();
        mArcPaint.setColor(ContextCompat.getColor(mContext, R.color.startred));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(10);
        mArcPaint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));

        mBgCircleRect = new RectF();

        mPaint = new Paint();

        mValueAnimator = ValueAnimator.ofFloat(0.0f, 360.0f + 150.0f);
//        mValueAnimator.setRepeatCount(1);
        mValueAnimator.setDuration(30000);
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
    }


    public void startAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.start();
        }
    }

    public void stopAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.end();
        }
    }

    private void invalidateSelf(float value) {
        if (value <= 150.0f) {
            mStartAngle = 0;
            mSweepAngle = (int) value;
        } else if (value >= 360.0f) {
//            mStartAngle = 360 - 150 + (int) value % 360;
            mStartAngle = 0;
            mSweepAngle = (int) (-150.0f + value % 360);
        } else {
            mStartAngle = (int) (value - 150.0f);
            mSweepAngle = (int) value;
        }

        Log.i(TAG, "start angle = " + mStartAngle + " , sweep angle = " + mSweepAngle);

        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawCircle(mWidth / 2, 500, 400, mBgCirclePaint);
//        mBgCircleRect.set(mWidth / 2 - 400 + 10, 100 + 10, mWidth / 2 + 400 - 10, 400 + 500 - 10);
//        canvas.drawArc(mBgCircleRect, mStartAngle - 90, mSweepAngle, false, mArcPaint);
//test
//        mBgCircleRect.set(mWidth / 2 - 400 + 10, 100 + 10, mWidth / 2 + 400 - 10, 400 + 500 - 10);
//        canvas.drawArc(mBgCircleRect, -90, -148, false, mArcPaint);

//        mRadialGradient = new RadialGradient(mWidth / 2,500.0f,400.0f,
//                new int[]{Color.WHITE, Color.GREEN, Color.BLUE, Color.RED},new float[]{0.8f,0.9f,0.95f,1.0f}, Shader.TileMode.REPEAT);
//        mPaint.setShader(mRadialGradient);
//        canvas.drawCircle(mWidth/2,500,400,mPaint);

        canvas.drawColor(Color.BLACK);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        canvas.drawCircle(600,600,400,mPaint);

    }
}
