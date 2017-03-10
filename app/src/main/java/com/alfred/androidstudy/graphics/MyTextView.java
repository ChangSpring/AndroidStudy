package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Alfred on 2017/3/3.
 */

public class MyTextView extends View {
    private Paint mPaint = new Paint();

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        drawText(canvas);
        drawFontMetrics(canvas);
        drawTextRect(canvas);
        drawTextCenterLine(canvas);





    }

    private void drawText(Canvas canvas) {
        int baseLineX = 0;
        int baseLineY = 200;

        canvas.drawLine(baseLineX,baseLineY,3000,baseLineY,mPaint);

        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(120);
        mPaint.setTextAlign(Paint.Align.CENTER);
        //drawText绘制的x坐标是文字所在矩形的相对位置,根据setTextAlign函数的设置
        //相对x坐标的位置，只有左、中、右三个位置了。也就是所绘制矩形可能是在x坐标的左侧绘制，也有可能在x坐标的中间，也有可能在x坐标的右侧
        //Y坐标的位置是绘制文字的基线位置(四线三格中的第三条线)
        canvas.drawText("Hello World gg !",baseLineX,baseLineY,mPaint);
    }

    /**
     * 获取文本的五条线 : top,ascent,baseline ,descent,bottom
     * http://img.blog.csdn.net/20151229092331153?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast
     * </p>
     * ascent: 系统建议的，绘制单个字符时，字符应当的最高高度所在线
     * descent:系统建议的，绘制单个字符时，字符应当的最低高度所在线
     * top: 可绘制的最高高度所在线
     * bottom: 可绘制的最低高度所在线
     *
     * fontMetrics.ascent = ascent线的y坐标 - baseline线的y坐标；
     * fontMetrics.descent = descent线的y坐标 - baseline线的y坐标；
     * fontMetrics.top = top线的y坐标 - baseline线的y坐标；
     * fontMetrics.bottom = bottom线的y坐标 - baseline线的y坐标；
     *
     * 推理可得:
     * ascent线的y坐标 = baseline线的y坐标 + fontMetrics.ascent
     * descent线的y坐标 = baseline线的y坐标 + fontMetrics.descent
     * top线的y坐标 = baseline线的y坐标 + fontMetrics.top
     * bottom线的y坐标 = baseline线的y坐标 + fontMetrics.bottom
     *
     * 千万不要将FontMetrics中的ascent,descent,top,bottom与现实中的ascent,descent,top,bottom所在线混淆！这几条线是真实存在的，
     * 而FontMetrics中的ascent,descent,top,bottom这个变量的值就是用来计算这几条线的位置的
     *
     * @param canvas
     */
    private  void drawFontMetrics(Canvas canvas){
        int baseLineX = 0;
        int baseLineY = 500;
        Paint paint = new Paint();
        paint.setTextSize(120);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Hello World ! gg " ,baseLineX,baseLineY,paint);

        //计算各线的位置
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float ascent = baseLineY + fontMetrics.ascent;
        float descent = baseLineY + fontMetrics.descent;
        float top = baseLineY + fontMetrics.top;
        float bottom = baseLineY + fontMetrics.bottom;

        //画基线baseLine
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX,baseLineY,3000,baseLineY,paint);

        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX,top,3000,top,paint);

        //🌹ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX,ascent,3000,ascent,paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent,3000, descent,paint);

        //画bottom
        paint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX,bottom,3000,bottom,paint);

    }

    /**
     * 绘制文本的两个矩形(最小矩形,紧紧包裹着文本,还有个是我们要求的宽高矩形)
     */
    private  void drawTextRect(Canvas canvas){
        String text = "Hello World ! ggg ";
        int baseLineX = 0;
        int baseLineY = 800;
        Paint paint = new Paint();
        paint.setTextSize(130);


        //获取大矩形的高度
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int top = baseLineY + fontMetrics.top;
        int bottom = baseLineY + fontMetrics.bottom;
        //大矩形的高度
        int height = bottom -top;
        //获取大矩形的宽度
        int width = (int)paint.measureText(text);
        Rect bigRect = new Rect(baseLineX,top,baseLineX + width ,bottom);
        paint.setColor(Color.BLUE);
        canvas.drawRect(bigRect,paint);

        //获取小矩形的尺寸
        paint.setColor(Color.GREEN);
        Rect minRect = new Rect();
        paint.getTextBounds(text,0,text.length(),minRect);
        minRect.top = baseLineY + minRect.top;
        minRect.bottom = baseLineY + minRect.bottom;
        canvas.drawRect(minRect,paint);
        Log.i("MyTextView","top = " + minRect.top + " left = " + minRect.left + " right = " + minRect.right + " bottom = " + minRect.bottom);

        //画文字 记住,要想能看到文字的话,一定要最后画文字,要不然就被矩形覆盖住了,canvas每一次draw都是一层图层的覆盖
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text,baseLineX,baseLineY,paint);
    }

    /**
     * 画出文本的中间线
     * @param canvas
     */
    private  void drawTextCenterLine(Canvas canvas){
        String text = "Hello World ! ggg ";
        int baseLineX = 0;

        //定义中间线的Y轴坐标
        int center = 1100;
        Paint paint = new Paint();
        paint.setTextSize(130);
        paint.setTextAlign(Paint.Align.LEFT);

        //画中间线
        paint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX,center,3000,center, paint);

        //画基线baseLine
        paint.setColor(Color.RED);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        //这个公式的推理自己看吧
        //http://blog.csdn.net/harvic880925/article/details/50423762
        int baseLineY = center + (fontMetricsInt.bottom - fontMetricsInt.top) /2 -fontMetricsInt.bottom;
        canvas.drawLine(baseLineX,baseLineY,3000,baseLineY,paint);

        //画文字
        paint.setColor(Color.BLUE);
        canvas.drawText(text,baseLineX,baseLineY,paint);
    }

















}
