package com.alfred.androidstudy.graphics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alfred on 2017/3/1.
 */

public class PaintView extends View {
    private Paint mPaint = new Paint();

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, AttributeSet attrs) {
        this(context,attrs,-1);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //是否抗锯齿 抗锯齿会使绘图速度变慢
        mPaint.setAntiAlias(true);
        //绘图样式,是否填充描边,对于设文字和几何图形都有效
        mPaint.setStyle(Paint.Style.FILL);
        //设置画笔宽度
        mPaint.setStrokeWidth(1.0f);
        //设置画笔的颜色
        mPaint.setColor(Color.RED);
        //设置文字的对齐方式
        mPaint.setTextAlign(Paint.Align.LEFT);
        //设置文字大小
        mPaint.setTextSize(100);

        //设置是否为粗体
        mPaint.setFakeBoldText(true);
        //设置是否有下划线
        mPaint.setUnderlineText(true);
        //设置文字的水平倾斜度,普通倾斜度字是-0.25
//        mPaint.setTextSkewX((float) - 0.5);
        //设置带有删除线的效果
//        mPaint.setStrikeThruText(true);

        //设置水平方向的拉伸,高度不会变
//        mPaint.setTextScaleX(2);

        //第二个参数起始位置的X轴的值
        //第三个参数是Y轴 baseLine 也就是四线三个中的第三条线的位置
        canvas.drawText("Hello World !",0 ,100,mPaint);

//        drawStrokeCap(canvas);
//        drawStrokeJoin(canvas);
//        drawPathEffect(canvas);
        drawDashPathEffect(canvas);
    }
//    @Deprecated
//    private  void drawPosText(Canvas canvas){
//        float []pos = new float[]{80,100,
//                                  80,200,
//        80,300,80,400};
//        canvas.drawPosText();
//    }

    private void drawTextOnPath(Canvas canvas){
        Path path = new Path();
        path.addCircle(200,200,200,Path.Direction.CCW);
        canvas.drawPath(path,mPaint);

        Path path1 = new Path();
        path1.addCircle(500,200,200, Path.Direction.CCW);
        canvas.drawPath(path1,mPaint);

        mPaint.setColor(Color.BLUE);
        String text = "Hello World ! ";
        canvas.drawTextOnPath(text,path,0,0,mPaint);

        canvas.drawTextOnPath(text,path,80,30,mPaint);
    }

    /**
     * 线帽样式
     */
    private void drawStrokeCap(Canvas canvas){
        Paint paint = new Paint();
        paint.setStrokeWidth(80);
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        //无线帽
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100,200,400,200,paint);

        //圆形线帽
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100,400,400,400,paint);

        //方形线帽
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100,600,400,600,paint);

        paint.reset();
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);
        canvas.drawLine(100,50,100,750,paint);

    }

    /**
     * 线段连接处样式
     */
    private void drawStrokeJoin(Canvas canvas){
        Paint paint  = new Paint();
        paint.setStrokeWidth(40);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        //结合处为锐角
        Path path = new Path();
        path.moveTo(100,100);
        path.lineTo(450,100);
        path.lineTo(100,300);
        paint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path,paint);

        //结合处为圆弧
        path.moveTo(100,400);
        path.lineTo(450,400);
        path.lineTo(100,600);
        paint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path,paint);

        //结合处为直线
        path.moveTo(100,700);
        path.lineTo(450,700);
        path.lineTo(100,900);
        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path,paint);
    }

    /**
     * 圆形拐角效果
     */
    private void drawPathEffect(Canvas canvas){
        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(700,900);

        canvas.drawPath(path,paint);

        paint.setColor(Color.RED);
        paint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path,paint);

        paint.setColor(Color.YELLOW);
        paint.setPathEffect(new CornerPathEffect(200));
        canvas.drawPath(path,paint);
    }

    int dashDx;

    /**
     * 虚线效果
     */
    private void drawDashPathEffect(final Canvas canvas){
        final Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);

        final Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(700,900);

        canvas.drawPath(path, paint);
        paint.setColor(Color.RED);

        //长度必须大于等于2；因为必须有一个实线段和一个空线段来组成虚线。
        //个数必须为偶数，如果是基数，最后一个数字将被忽略；这个很好理解，因为一组虚线的组成必然是一个实线和一个空线成对组成的。
        paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},0));
        canvas.translate(0,100);
        canvas.drawPath(path,paint);

        //位移15
        paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},dashDx));
        paint.setColor(Color.YELLOW);
        canvas.translate(0,100);
        canvas.drawPath(path,paint);


    }

    public void startAnimator(){
        ValueAnimator valueAnimator =ValueAnimator.ofInt(0,230);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dashDx = (int)animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    /**
     * 离散路径效果
     */









}
