package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alfred on 2017/3/1.
 */

public class PathView extends View {
    private Paint mPaint = new Paint();

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5.0f);
        mPaint.setColor(Color.RED);

    }

    /**
     * 直线路径
     * @param canvas
     */
    private void drawLine(Canvas canvas){
        Path path = new Path();
        path.moveTo(10, 10);
        path.lineTo(10, 100);
        path.lineTo(700, 100);
        path.lineTo(10, 10);
//        path.lineTo(500,100);

        canvas.drawPath(path, mPaint);
    }

    /**
     * 矩形路径
     * @param canvas
     */
    private void drawRect(Canvas canvas){
        //第一个逆向生成
        Path CCWRectPath = new Path();
        RectF rectF1 = new RectF(120, 120, 600, 600);
        CCWRectPath.addRect(rectF1, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectPath = new Path();
        RectF rectF2 = new RectF(720, 120, 1010, 600);
        CWRectPath.addRect(rectF2, Path.Direction.CW);

        canvas.drawPath(CCWRectPath, mPaint);
        canvas.drawPath(CWRectPath, mPaint);

        String text = "风萧萧兮易水寒,壮士一去兮不复返";
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(36);
        canvas.drawTextOnPath(text, CCWRectPath, 0, 18, mPaint);
        canvas.drawTextOnPath(text, CWRectPath, 0, 18, mPaint);
    }

    /**
     * 圆角矩形路径
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas){
        Path path = new Path();
        //只能构建统一圆角大小
        RectF rectF3 = new RectF(50, 50, 240, 200);
        //第二个第三个参数分别是所产生圆角的椭圆的横纵轴半径
        path.addRoundRect(rectF3, 10, 15, Path.Direction.CCW);

        RectF rectF4 = new RectF(290,50,480,200);
        //可以定制每个角的圆角大小：
        //float[] radii：必须传入8个数值，分四组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，如｛x1,y1,x2,y2,x3,y3,x4,y4｝，其中，x1,y1对应第一个角的（左上角）用来产生圆角的椭圆的横轴半径和纵轴半径，其它类推……
        float radii[] ={10,15,20,25,30,35,40,45};
        path.addRoundRect(rectF4, radii, Path.Direction.CCW);

        canvas.drawPath(path, mPaint);
    }

    /**
     * 圆形路径
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        Path path = new Path();
        path.addCircle(300,300,100, Path.Direction.CCW);
        canvas.drawPath(path,mPaint);
    }

    /**
     * 椭圆路径
     * @param canvas
     */
    private void drawOval(Canvas canvas){
       Path path = new Path();
        RectF rectF = new RectF(50,50,240,200);
        path.addOval(rectF, Path.Direction.CCW);
        canvas.drawPath(path,mPaint);
    }

    /**
     * 弧路径
     * @param canvas
     */
    private void drawArc(Canvas canvas){
        Path path = new Path();
        RectF rectF = new RectF(50,50,240,200);
        path.addArc(rectF,0,100);
        canvas.drawPath(path,mPaint);
    }









}
