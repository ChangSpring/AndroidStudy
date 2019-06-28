package com.alfred.androidstudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * @author :  Alfred
 * @date : 2018/12/20 16:32
 */
public class MessageDrawable extends Drawable {
    private Paint mbgPaint;
    private Paint mPaint;
    private Drawable mDrawable;

    public MessageDrawable(Context ctx, int res) {
        mDrawable = ContextCompat.getDrawable(ctx, res);
        mbgPaint = new Paint();
        mbgPaint.setColor(Color.parseColor("#ffffff"));
        mbgPaint.setAntiAlias(true);

        mPaint = new Paint();
        mPaint.setColor(ContextCompat.getColor(ctx, R.color.startred));
        mPaint.setTextSize(20);
        mPaint.setAntiAlias(true);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Log.i("MessageDrawable", "width = " + mDrawable.getIntrinsicWidth() + " height = " + mDrawable.getIntrinsicHeight());
        Log.i("MessageDrawable", "radius x = " + (mDrawable.getIntrinsicWidth() * 3 / 4 + 7) + " radius y = " + (mDrawable.getIntrinsicHeight() / 4 - 3));
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        mDrawable.draw(canvas);
        canvas.drawCircle(mDrawable.getIntrinsicWidth() * 3 / 4 + 7, mDrawable.getIntrinsicHeight() / 4 - 3, 12, mbgPaint);
        canvas.drawCircle(mDrawable.getIntrinsicWidth() * 3 / 4 + 7, mDrawable.getIntrinsicHeight() / 4 - 3, 9, mPaint);
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
