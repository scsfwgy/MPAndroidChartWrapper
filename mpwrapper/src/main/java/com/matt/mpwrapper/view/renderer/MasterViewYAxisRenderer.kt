package com.matt.mpwrapper.view.renderer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.renderer.YAxisRenderer
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler
import com.matt.mpwrapper.R
import com.matt.mpwrapper.ktx.dip2px
import com.matt.mpwrapper.view.MasterView

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/10/16 19:08
 * 描 述 ：1. 实现实时价格横线上的文字的布局显示
 * 2. 其它...
 * ============================================================
 */
class MasterViewYAxisRenderer(
    viewPortHandler: ViewPortHandler,
    yAxis: YAxis,
    trans: Transformer,
    var mMasterView: MasterView
) : YAxisRenderer(viewPortHandler, yAxis, trans) {
    var mContext: Context
    var mLimitTxtPaint: Paint? = null
    var mLimitTxtColor = 0
    var mLimitTxtSize = 0
    //标记
    var mLable: String? = null
    var mTxtX = 0f
    var mTxtY = 0f
    var mLimitBgPaint: Paint? = null
    var mLimitBgColor = 0
    private fun initPaint() {
        mLimitBgColor = ContextCompat.getColor(mContext, R.color.mp_marker_bgLimitColor)
        mLimitTxtColor = ContextCompat.getColor(mContext, R.color.mp_marker_txtLimitColor)
        mLimitTxtSize = dip2px(8.0f).toInt()
        mLimitBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLimitBgPaint!!.color = mLimitBgColor
        mLimitBgPaint!!.style = Paint.Style.FILL
        mLimitTxtPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mLimitTxtPaint!!.color = mLimitTxtColor
        mLimitTxtPaint!!.style = Paint.Style.FILL
        mLimitTxtPaint!!.textSize = mLimitTxtSize.toFloat()
    }

    /**
     * 重写这个方法主要是重新安排实时横线的lable显示样式和位置
     *
     * @param c
     */
    override fun renderLimitLines(c: Canvas) { //super.renderLimitLines(c);
        val limitLines = mYAxis.limitLines
        if (limitLines == null || limitLines.size <= 0) return
        val pts = mRenderLimitLinesBuffer
        pts[0] = 0.0f
        pts[1] = 0.0f
        val limitLinePath = mRenderLimitLines
        limitLinePath.reset()
        for (i in limitLines.indices) {
            val l = limitLines[i]
            if (!l.isEnabled) continue
            val clipRestoreCount = c.save()
            mLimitLineClippingRect.set(mViewPortHandler.contentRect)
            mLimitLineClippingRect.inset(0f, -l.lineWidth)
            c.clipRect(mLimitLineClippingRect)
            mLimitLinePaint.style = Paint.Style.STROKE
            mLimitLinePaint.color = l.lineColor
            mLimitLinePaint.strokeWidth = l.lineWidth
            mLimitLinePaint.pathEffect = l.dashPathEffect
            pts[1] = l.limit
            mTrans.pointValuesToPixel(pts)
            limitLinePath.moveTo(mViewPortHandler.contentLeft(), pts[1])
            limitLinePath.lineTo(mViewPortHandler.contentRight(), pts[1])
            c.drawPath(limitLinePath, mLimitLinePaint)
            limitLinePath.reset()
            // c.drawLines(pts, mLimitLinePaint);
            val label = l.label
            // if drawing the limit-value label is enabled
            if (label != null && label != "") {
                mLimitLinePaint.style = l.textStyle
                mLimitLinePaint.pathEffect = null
                mLimitLinePaint.color = l.textColor
                mLimitLinePaint.typeface = l.typeface
                mLimitLinePaint.strokeWidth = 0.5f
                mLimitLinePaint.textSize = l.textSize
                val labelLineHeight =
                    Utils.calcTextHeight(mLimitLinePaint, label)
                        .toFloat()
                val labelLineWith =
                    Utils.calcTextWidth(mLimitLinePaint, label)
                        .toFloat()
                val xOffset =
                    Utils.convertDpToPixel(4f) + l.xOffset
                val yOffset = l.lineWidth + labelLineHeight + l.yOffset
                val position = l.labelPosition
                /**
                 * 注意只重写这一块，在其它位置仍然是默认显示==>在这个地方重写并不能实现效果，这个地方只能绘制在图表内部，
                 * 所以这个地方记下所需要的位置以及值信息在renderAxisLabels(Canvas c)绘制y轴标签的地方绘制实时线的lable。
                 */
                if (position == LimitLabelPosition.RIGHT_TOP) {
                    mLimitLinePaint.textAlign = Paint.Align.RIGHT
                    //                    c.drawText(label,
//                            mViewPortHandler.contentRight() - xOffset,
//                            pts[1] - yOffset + labelLineHeight, mLimitLinePaint);
//                    c.drawText(label,
//                            mViewPortHandler.contentRight() + labelLineWith / 2,
//                            pts[1] - yOffset + labelLineHeight, mLimitLinePaint);
//记下位置
                    mLable = label
                    mTxtX = mViewPortHandler.contentRight()
                    mTxtY = pts[1] - yOffset + labelLineHeight
                } else if (position == LimitLabelPosition.RIGHT_BOTTOM) {
                    mLimitLinePaint.textAlign = Paint.Align.RIGHT
                    c.drawText(
                        label,
                        mViewPortHandler.contentRight() - xOffset,
                        pts[1] + yOffset, mLimitLinePaint
                    )
                } else if (position == LimitLabelPosition.LEFT_TOP) {
                    mLimitLinePaint.textAlign = Paint.Align.LEFT
                    c.drawText(
                        label,
                        mViewPortHandler.contentLeft() + xOffset,
                        pts[1] - yOffset + labelLineHeight, mLimitLinePaint
                    )
                } else {
                    mLimitLinePaint.textAlign = Paint.Align.LEFT
                    c.drawText(
                        label,
                        mViewPortHandler.offsetLeft() + xOffset,
                        pts[1] + yOffset, mLimitLinePaint
                    )
                }
            }
            c.restoreToCount(clipRestoreCount)
        }
    }

    override fun renderAxisLabels(c: Canvas) {
        super.renderAxisLabels(c)
        //不要阻碍父类的实现，在这里实现自己的即可
        drawLimitLable(c)
    }

    protected fun drawLimitLable(c: Canvas) {
        if (mLable == null || mTxtX == 0f || mTxtY == 0f) return
        val width =
            Utils.calcTextWidth(mLimitTxtPaint, mLable)
        val height =
            Utils.calcTextHeight(mLimitTxtPaint, mLable)
        //绘制背景
        val radius: Float = dip2px(2f)
        val paddingLR: Float = dip2px(8.0f)
        val paddingTB: Float = dip2px(4.0f)
        val left = mTxtX
        val top = mTxtY - paddingTB / 2.0f
        val right = left + width + paddingLR
        val bottom = top + height + paddingTB
        val rectF = RectF(left, top, right, bottom)
        c.drawRoundRect(rectF, radius, radius, mLimitBgPaint!!)
        //绘制文字
        val x = mTxtX + paddingLR / 2.0f
        val y = mTxtY + height
        c.drawText(mLable!!, x, y, mLimitTxtPaint!!)
    }

    init {
        mContext = mMasterView.context
        initPaint()
    }
}