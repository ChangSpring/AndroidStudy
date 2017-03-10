package com.alfred.androidstudy.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alfred on 2017/3/2.
 */

public class RangeView extends View {
    private Paint mPaint = new Paint();

    public RangeView(Context context) {
        this(context, null);
    }

    public RangeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RangeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);


//        drawRegion(canvas);
//        drawPathRegion(canvas);
        drawOpRegion(canvas);
    }

    /**
     * 通过Region创建一个区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        Region region = new Region(10, 10, 100, 100);
//        region.set(100,100,200,200);

        drawRegionIterator(canvas, region);
    }

    /**
     * 通过Region创建不规则区域
     */
    private void drawPathRegion(Canvas canvas) {
        //显示RegionIterator的原理
//        mPaint.setStyle(Paint.Style.STROKE);
        Path ovalPath = new Path();
        RectF rectF = new RectF(50, 50, 200, 500);
        ovalPath.addOval(rectF, Path.Direction.CCW);

        //setPath时,传入一个小的矩形区域,取交集,从而获得不规则的区域
        Region region = new Region();
        region.setPath(ovalPath, new Region(50, 50, 200, 200));
        //画出路径
        drawRegionIterator(canvas, region);
    }

    /**
     * 通过RegionIterator画出路径
     */
    private void drawRegionIterator(Canvas canvas, Region region) {
        RegionIterator regionIterator = new RegionIterator(region);
        Rect rect = new Rect();

        while (regionIterator.next(rect)) {
            canvas.drawRect(rect, mPaint);
        }
    }

    /**
     * 区域的合并,交叉
     * DIFFERENCE(0), //最终区域为region1 与 region2不同的区域
     * INTERSECT(1), // 最终区域为region1 与 region2相交的区域
     * UNION(2),      //最终区域为region1 与 region2组合一起的区域
     * XOR(3),        //最终区域为region1 与 region2相交之外的区域
     * REVERSE_DIFFERENCE(4), //最终区域为region2 与 region1不同的区域
     * REPLACE(5); //最终区域为为region2的区域
     *
     * @param canvas
     */
    private void drawOpRegion(Canvas canvas) {
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        canvas.drawRect(rect1, mPaint);
        canvas.drawRect(rect2, mPaint);

        Region region1 = new Region(rect1);
        Region region2 = new Region(rect2);

        mPaint.setStyle(Paint.Style.STROKE);

        region1.op(region2, Region.Op.DIFFERENCE);

        Paint fillPaint = new Paint();
        fillPaint.setColor(Color.GREEN);
        fillPaint.setStyle(Paint.Style.FILL);
        RegionIterator regionIterator = new RegionIterator(region1);
        Rect rect = new Rect();

        while (regionIterator.next(rect)) {
            canvas.drawRect(rect, fillPaint);
        }
    }


}
