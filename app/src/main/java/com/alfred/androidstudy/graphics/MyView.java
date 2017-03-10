package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alfred on 2017/3/1.
 */

public class MyView extends View {

    private Paint mPaint = new Paint();

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs, -1);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //抗锯齿
        mPaint.setAntiAlias(true);
        //画笔颜色
        mPaint.setColor(Color.BLUE);
        //设置填充
        mPaint.setStyle(Paint.Style.STROKE);
        //设置画笔宽度
        mPaint.setStrokeWidth(5.0f);
        //设置阴影
        mPaint.setShadowLayer(10, 15, 15, Color.RED);

        //设置画布背景颜色
        canvas.drawRGB(255, 255, 111);

        //画圆
        canvas.drawCircle(150, 150, 150, mPaint);

        canvas.drawLine(300,300,500,500,mPaint);

        //多条直线 上面有四个点：（10，10）、（100，100），（200，200），（400，400）），两两连成一条直线
        float []pts = {10,10,100,100,200,200,400,400};
        canvas.drawLines(pts,mPaint);

        //画点
        canvas.drawPoint(0,700,mPaint);

        //画多个点
        //float[] pts:点的合集，与上面直线一直，样式为｛x1,y1,x2,y2,x3,y3,……｝
        //int offset:集合中跳过的数值个数，注意不是点的个数！一个点是两个数值；
        //count:参与绘制的数值的个数，指pts[]里人数值个数，而不是点的个数，因为一个点是两个数值
        //同样是上面的四个点：（10，10）、（100，100），（200，200），（400，400），drawPoints里路过前两个数值，即第一个点横纵坐标，画出后面四个数值代表的点，即第二，第三个点，第四个点没画
        float []pts1 = {10,10,100,100,200,200,400,400};
        canvas.drawPoints(pts1,2,4,mPaint);

        //画矩形
        canvas.drawRect(10,10,100,100,mPaint);

        RectF rectF = new RectF(120,10,210,100);
        canvas.drawRect(rectF,mPaint);

        Rect rect = new Rect(230,10,320,100);
        canvas.drawRect(rect,mPaint);

        //画圆角矩形
        RectF rectF1 = new RectF(100,1000,300,1200);
        canvas.drawRoundRect(rectF1,200,100,mPaint);

        //画椭圆
        RectF rectF2 = new RectF(400,400,700,500);
        canvas.drawOval(rectF2,mPaint);

        //画弧
        //RectF oval:生成椭圆的矩形
        //float startAngle：弧开始的角度，以X轴正方向为0度
        //float sweepAngle：弧持续的角度
        //boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF3 = new RectF(500,500,900,900);
        canvas.drawArc(rectF3,0,-90,false,mPaint);

    }
}
