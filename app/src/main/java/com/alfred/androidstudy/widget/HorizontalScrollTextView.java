package com.alfred.androidstudy.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by alfred on 2018/5/4.
 */
public class HorizontalScrollTextView extends AppCompatTextView {
    private float textLength = 0f;// 文本长度
    private float step = 0f;// 文本的横坐标
    private float y = 0f;// 文本的纵坐标
    public boolean isStarting = false;// 是否开始滚动
    private Paint paint = null;
    private String text = "";// 文本内容
    private OnScrollCompleteListener onScrollCompleteListener;//滚动结束监听

    public HorizontalScrollTextView(Context context) {
        this(context, null);
    }

    public HorizontalScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HorizontalScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public OnScrollCompleteListener getOnSrollCompleteListener() {
        return onScrollCompleteListener;
    }

    public void setOnScrollCompleteListener(OnScrollCompleteListener onScrollCompleteListener) {
        this.onScrollCompleteListener = onScrollCompleteListener;
    }

    public void init() {
        paint = getPaint();
        //设置滚动字体颜色
        paint.setColor(Color.parseColor("#f41301"));
        text = getText().toString();
        textLength = paint.measureText(text);
        y = getTextSize() + getPaddingTop();
        step = -textLength;
    }

    //开启滚动
    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    //停止滚动
    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawText(text, step, y, paint);
        if (!isStarting) {
            return;
        }
        // 2.0为文字的滚动速度
        step += 10;
        //判断是否滚动结束
        if (step >= 0) {
            step = 0;
            onScrollCompleteListener.onScrollComplete();
        } else {
            invalidate();
        }
    }

    public interface OnScrollCompleteListener {
        void onScrollComplete();
    }
}
