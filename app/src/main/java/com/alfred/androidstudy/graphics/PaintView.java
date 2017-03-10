package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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


}
