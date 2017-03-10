package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 贝塞尔曲线
 * </p>
 * <a>http://blog.csdn.net/harvic880925/article/details/50995587</a>
 * Created by Alfred on 2017/3/3.
 */

public class QuadView extends View {
    private Path mPath = new Path();
    private float mPreX,mPreY;

    public QuadView(Context context) {
        this(context, null);
    }

    public QuadView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public QuadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //quadTo()方法参数是绝对坐标
        //rQuadTo()方法参数是相对坐标(相对于上个点的坐标)
    }

    /**
     * 贝塞尔曲线有三个坐标:起始点,控制点,终止点
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mPath,paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) /2;
                float endY = (mPreY + event.getY()) /2 ;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY = event.getY();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void drawQuad(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        Path path = new Path();
        path.moveTo(100,300);

        path.quadTo(200,200,300,300);
        path.quadTo(400,400,500,300);

        canvas.drawPath(path,paint);
    }
}
