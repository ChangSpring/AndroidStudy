package com.alfred.androidstudy.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author :  Alfred
 * @date   : 2019-08-01 12:28
 *
 */
class TestView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var mDstPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mSrcPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        mDstPaint.color = Color.BLUE
        mSrcPaint.color = Color.YELLOW

        mSrcPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)


        setLayerType(LAYER_TYPE_HARDWARE, null)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(20f, 20f, 80f, 80f, mDstPaint)
        canvas.drawCircle(30f, 30f, 30f, mSrcPaint)
    }
}

