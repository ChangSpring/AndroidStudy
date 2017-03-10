package com.alfred.androidstudy.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by Alfred on 2017/2/10.
 */

public class MyPointView extends View{
    private Point mCurPoint = new Point(100);
    private Paint mPaint;

    public MyPointView(Context context) {
        this(context,null);
    }

    public MyPointView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public MyPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCurPoint != null){
            canvas.drawCircle(300,300,mCurPoint.getRadius(),mPaint);
        }
    }

    public void startAnimator(){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyPointEvalutor(),new Point(20),new Point(200));
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurPoint = (Point)animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public void setPointRadius(int radius){
        mCurPoint.setRadius(radius);
        invalidate();
    }
}
