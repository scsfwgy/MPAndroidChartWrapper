package com.matt.mpwrapper.view.renderer

import android.graphics.Canvas
import android.graphics.Paint
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/10/16 17:37
 * 描 述 ：实现可以只绘制最后一个小圆点，如果设置mDrawLastCircles=true则不再走原有逻辑，只绘制最后一个小圆点。
 */
open class BaseLineChartRenderer(
    chart: LineDataProvider,
    val animator: ChartAnimator,
    viewPortHandler: ViewPortHandler
) : LineChartRenderer(chart, animator, viewPortHandler) {
    /**
     * 是否只绘制最后一个小圆点
     */
    var mDrawLastCircles = false

    private val mCirclesBuffer = FloatArray(2)
    override fun drawExtras(c: Canvas) {
        if (isDrawLastCircles()) {
            drawLastCircle(c)
        }
        super.drawExtras(c)
    }

    /**
     * 绘制最后一个小圆点
     *
     * @param c
     */
    fun drawLastCircle(c: Canvas) {
        mRenderPaint.style = Paint.Style.FILL
        val phaseY: Float = animator.phaseY
        mCirclesBuffer[0] = 0f
        mCirclesBuffer[1] = 0f
        val dataSets = mChart.lineData.dataSets
        if (dataSets == null || dataSets.isEmpty()) return
        val dataSet = dataSets[dataSets.size - 1]
        if (!dataSet.isVisible || dataSet.entryCount == 0) return
        mRenderPaint.color = dataSet.getCircleColor(0)
        mCirclePaintInner.color = dataSet.circleHoleColor
        val trans = mChart.getTransformer(dataSet.axisDependency)
        mXBounds[mChart] = dataSet
        val circleRadius = dataSet.circleRadius
        val circleHoleRadius = dataSet.circleHoleRadius
        val drawCircleHole =
            dataSet.isDrawCircleHoleEnabled && circleHoleRadius < circleRadius && circleHoleRadius > 0f
        val e = dataSet.getEntryForIndex(dataSet.entryCount - 1) ?: return
        mCirclesBuffer[0] = e.x
        mCirclesBuffer[1] = e.y * phaseY
        trans.pointValuesToPixel(mCirclesBuffer)
        if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0])) return
        if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) ||
            !mViewPortHandler.isInBoundsY(mCirclesBuffer[1])
        ) return
        c.drawCircle(
            mCirclesBuffer[0],
            mCirclesBuffer[1],
            circleRadius,
            mRenderPaint
        )
        if (drawCircleHole) {
            c.drawCircle(mCirclesBuffer[0], mCirclesBuffer[1], circleHoleRadius, mCirclePaintInner)
        }
    }

    fun isDrawLastCircles(): Boolean {
        return mDrawLastCircles
    }

    fun setDrawLastCircles(mDrawLastCircles: Boolean): BaseLineChartRenderer {
        this.mDrawLastCircles = mDrawLastCircles
        return this
    }
}