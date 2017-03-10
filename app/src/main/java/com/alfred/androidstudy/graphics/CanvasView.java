package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Canvas变换与操作
 * Created by Alfred on 2017/3/2.
 */

public class CanvasView extends View {
    public CanvasView(Context context) {
        this(context, null);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 画布的每一次drawXXX()方法 就是贴一层图层
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        translateCanvas(canvas);
//        rotateCanvas(canvas);
//            scaleCanvas(canvas);
//        skewCanvas(canvas);
//        clipCanvas(canvas);
        saveRestoreCanvas(canvas);

    }

    /**
     * 平移画布
     *
     * @param canvas
     */
    private void translateCanvas(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);

        //平移画布,rect往右下平移
//        canvas.translate(100,100);
        Rect rect = new Rect(0, 0, 400, 200);
        canvas.drawRect(rect, paint);
    }

    /**
     * 画布旋转
     *
     * @param canvas
     */
    private void rotateCanvas(Canvas canvas) {
        Paint paintGreen = generatePaint(Color.GREEN, Paint.Style.FILL, 5);
        Paint paintRed = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect = new Rect(300, 10, 500, 100);
        canvas.drawRect(rect, paintRed);

        canvas.rotate(30);
        canvas.drawRect(rect, paintGreen);

    }

    /**
     * 画布缩放
     *
     * @param canvas
     */
    private void scaleCanvas(Canvas canvas) {
        Paint paintGreen = generatePaint(Color.GREEN, Paint.Style.FILL, 5);
        Paint paintRed = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect = new Rect(10, 10, 200, 100);
        canvas.drawRect(rect, paintGreen);

        canvas.scale(0.5f, 1);
        canvas.drawRect(rect, paintRed);
    }

    /**
     * 画布扭曲
     *
     * @param canvas
     */
    private void skewCanvas(Canvas canvas) {
        Paint paintGreen = generatePaint(Color.GREEN, Paint.Style.FILL, 5);
        Paint paintRed = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect = new Rect(10, 10, 200, 100);

        canvas.drawRect(rect, paintGreen);
        //float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
        //float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值，
        //在X轴方向上倾斜60度，tan60=根号3，小数对应1.732
        canvas.skew(1.732f, 0);
        canvas.drawRect(rect, paintRed);
    }

    /**
     * 画布裁切
     *
     * @param canvas
     */
    private void clipCanvas(Canvas canvas) {
        //将背景绘制成红色
        canvas.drawColor(Color.RED);
        //裁切画布
        canvas.clipRect(new Rect(100, 100, 200, 200));
        //最后将裁切好的画布涂成绿色
        canvas.drawColor(Color.GREEN);
    }

    /**
     *
     * 画布的保存与恢复
     * <br>
     * http://img.blog.csdn.net/20140909140844622?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaGFydmljODgwOTI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast
     * @param canvas
     */
    private void saveRestoreCanvas(Canvas canvas) {
        //将背景绘制成红色
        canvas.drawColor(Color.RED);
        //保存当前画布的状态
        canvas.save();

        //裁切
        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);

        canvas.save();

        canvas.clipRect(new Rect(200,200,700,700));
        canvas.drawColor(Color.BLUE);

        canvas.save();

        canvas.clipRect(new Rect(300,300,600,600));
        canvas.drawColor(Color.BLACK);

        canvas.save();

//        canvas.restore();

        canvas.clipRect(new Rect(400,400,500,500));
        canvas.drawColor(Color.BLUE);

        //每一次的save操作都是一次压栈操作,http://img.blog.csdn.net/20140909143603109?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaGFydmljODgwOTI1/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast
    }


    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }


}
