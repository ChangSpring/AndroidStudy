package com.alfred.androidstudy.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View


/**
 * @author :  Alfred
 * @date   : 2019-07-29 16:54
 *
 */
class CourseCouponView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var mBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mSmallCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBigCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mSmallBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDottedLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)


    /**
     * 虚线高度
     */
    private val dottedLineHeight = 6f


    /**
     * 圆跟圆之间的间距
     */
    private var circleGap = 0f

    /**
     * 小圆的个数
     */
    private val smallCircleNum = 8

    /**
     * 小圆的半径
     */
    private val smallCircleRadius = 6f

    /**
     * 大圆的半径
     */
    private val bigCircleRadius = 28f

    /**
     * 边框离虚线的距离
     */
    private val dashesDisance = 16f

    /**
     * 要画的圆圈的x轴位置
     */
    private var location = 0f

    init {
//        val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CourseCouponView)
//        circleNum = ta.getInt(R.styleable.CourseCouponView_circleNum, 10)
//        gap = ta.getDimensionPixelOffset(R.styleable.CourseCouponView_gap, 20).toFloat()
//        radius = ta.getDimensionPixelOffset(R.styleable.CourseCouponView_radius, 15).toFloat()

//        ta.recycle()


        mBgPaint.isDither = true
        mBgPaint.color = Color.RED
        mBgPaint.style = Paint.Style.FILL

        mBigCirclePaint.isDither = true
        mBigCirclePaint.color = Color.GREEN
        mBigCirclePaint.style = Paint.Style.FILL

        mSmallCirclePaint.isDither = true
        mSmallCirclePaint.color = Color.YELLOW
        mSmallCirclePaint.style = Paint.Style.FILL
        mSmallCirclePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

        mSmallBgPaint.isDither = true
        mSmallBgPaint.color = Color.BLUE
        mSmallBgPaint.style = Paint.Style.FILL

        mDottedLinePaint.isDither = true
        mDottedLinePaint.color = Color.BLACK
        mDottedLinePaint.style = Paint.Style.STROKE
        mDottedLinePaint.strokeWidth = dottedLineHeight
        mDottedLinePaint.pathEffect = DashPathEffect(floatArrayOf(20f, 6f), 0f)


        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

        /**
         * 开启硬件离屏缓存
         */


//        canvas?.drawARGB(0,0,0,0)

        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mBgPaint)


        //画左右两边的锯齿圆
        for (i in 0 until (smallCircleNum + 1)) {
            when (i) {
                0 -> location = circleGap + smallCircleRadius
                in 1 until (smallCircleNum / 2) -> location += circleGap + smallCircleRadius * 2
                smallCircleNum / 2, smallCircleNum / 2 + 1 -> location += circleGap + bigCircleRadius + smallCircleRadius
                else -> location += circleGap + smallCircleRadius * 2
            }
            Log.i("zhangquan", "location = $location")
            //画左边的圆
            when (i) {
                in 0 until (smallCircleNum / 2) -> canvas?.drawCircle(0f, location, smallCircleRadius, mSmallCirclePaint)
                smallCircleNum / 2 -> canvas?.drawCircle(0f, location, bigCircleRadius, mSmallCirclePaint)
                else -> canvas?.drawCircle(0f, location, smallCircleRadius, mSmallCirclePaint)
            }
            //画右边的圆
            when (i) {
                in 0 until (smallCircleNum / 2) -> canvas?.drawCircle(width.toFloat(), location, smallCircleRadius, mSmallCirclePaint)
                smallCircleNum / 2 -> canvas?.drawCircle(width.toFloat(), location, bigCircleRadius, mSmallCirclePaint)
                else -> canvas?.drawCircle(width.toFloat(), location, smallCircleRadius, mSmallCirclePaint)
            }
        }
        mSmallCirclePaint.xfermode = null

        canvas?.drawRect(dashesDisance + smallCircleRadius, dashesDisance, width - (dashesDisance + smallCircleRadius), height - dashesDisance, mSmallBgPaint)

        canvas?.drawArc(-42f, (height - dashesDisance * 2 - circleGap * 2 - bigCircleRadius * 2) / 2 + 14, 42f, (height - dashesDisance * 2 - circleGap * 2 - bigCircleRadius * 2) / 2 + 42 * 2, -60f, 3500f, true, mBigCirclePaint)

        //画虚线框
        canvas?.drawRect(dashesDisance + smallCircleRadius + dottedLineHeight / 2, dashesDisance + dottedLineHeight /2, width - (dashesDisance + smallCircleRadius) - dottedLineHeight/ 2, height - dashesDisance - dottedLineHeight /2, mDottedLinePaint)


        //画虚线
        //顶部横线
//        canvas?.drawLine(dashesDisance + smallCircleRadius + dottedLineHeight / 2, dashesDisance + dottedLineHeight / 2, width - (dashesDisance + smallCircleRadius + dottedLineHeight / 2), dashesDisance + dottedLineHeight / 2, mDottedLinePaint)
//        //左上竖线
//        canvas?.drawLine(dashesDisance + smallCircleRadius + dottedLineHeight / 2, dashesDisance + +dottedLineHeight / 2, dashesDisance + smallCircleRadius + dottedLineHeight / 2, (height - dashesDisance * 2 - circleGap * 2 - bigCircleRadius * 2) / 2, mDottedLinePaint)
//        //右上竖线
//        canvas?.drawLine(width - (dashesDisance + smallCircleRadius), dashesDisance, width - (dashesDisance + smallCircleRadius), (height - dashesDisance * 2 - circleGap * 2 - bigCircleRadius * 2) / 2, mDottedLinePaint)
//        //左下竖线
//        canvas?.drawLine(dashesDisance + smallCircleRadius, (height - dashesDisance * 2 - circleGap * 2 - bigCircleRadius * 2) / 2 + dashesDisance * 2 + bigCircleRadius * 2 + dashesDisance * 2, dashesDisance + smallCircleRadius, height - dashesDisance, mDottedLinePaint)
//        //右下竖线
//        canvas?.drawLine(width - (dashesDisance + smallCircleRadius), (height - dashesDisance * 2 - circleGap * 2 - bigCircleRadius * 2) / 2 + dashesDisance * 2 + bigCircleRadius * 2 + dashesDisance * 2, width - (dashesDisance + smallCircleRadius), height - dashesDisance, mDottedLinePaint)
//        //底部竖线
//        canvas?.drawLine(dashesDisance + smallCircleRadius, height - dashesDisance, width - (dashesDisance + smallCircleRadius), height - dashesDisance, mDottedLinePaint)


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //根据小圆的个数和大圆的半径,计算出圆和圆之间的间距
        //8个小圆 每个小圆半径是6px 一个大圆 半径为28
        //一个九个圆  十个间隙
        circleGap = (height - (smallCircleNum * smallCircleRadius * 2) - (1 * bigCircleRadius * 2)) / (smallCircleNum + 2)

        Log.i("zhangquan", "circleGap = $circleGap")
        Log.i("zhangquan", "height = $height")

    }


}