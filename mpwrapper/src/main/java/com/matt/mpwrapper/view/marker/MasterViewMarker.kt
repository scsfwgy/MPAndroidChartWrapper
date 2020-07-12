package com.matt.mpwrapper.view.marker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.matt.mpwrapper.R
import com.matt.mpwrapper.utils.ScreenUtils
import com.matt.mpwrapper.utils.TimeUtils
import com.matt.mpwrapper.utils.XFormatUtil
import com.matt.mpwrapper.view.MasterView
import kotlinx.android.synthetic.main.markerview_k_view_shell.view.*

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/29 14:12
 * 描 述 ：主图的markerview，何为markerview:就是长按高亮线上显示的文字，
 * 默认情况下显示在x、y轴高亮线交互点处右下角显示。
 * 现在自定义其在x轴内侧显示x的数值，在y轴外侧显示y轴数值。
 * ============================================================
 */
@SuppressLint("ViewConstructor")
open class MasterViewMarker(
    private val mMasterView: MasterView,
    val mContext: Context,
    val digit: Int
) :
    MarkerView(mContext, R.layout.markerview_k_view_shell) {

    companion object {
        const val TAG = "BaseKViewMarker"
    }

    private val mOffset3 = MPPointF()

    //绘制背景
    val mMpTxtColor by lazy {
        colorResId(R.color.mp_marker_txtColor)
    }
    val mMpTxtPaint: Paint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = ScreenUtils.dip2px(8f).toFloat()
        paint.color = mMpTxtColor
        paint
    }
    val mMpBgColor by lazy {
        colorResId(R.color.mp_marker_bgColor)
    }
    val mMpBgPaint: Paint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = mMpBgColor
        paint
    }

    //背景的圆角
    private val mRadius = ScreenUtils.dip2px(2f)
    private var mEntry: Entry? = null

    override fun refreshContent(e: Entry, highlight: Highlight) {
        super.refreshContent(e, highlight)
        mEntry = e

        if (e is CandleEntry) {
            tch_tv_time.text = TimeUtils.millis2String(e.x.toLong())
            tch_tv_open.text = XFormatUtil.globalFormat(e.open.toString(), digit)
            tch_tv_high.text = XFormatUtil.globalFormat(e.high.toString(), digit)
            tch_tv_low.text = XFormatUtil.globalFormat(e.low.toString(), digit)
            tch_tv_close.text = XFormatUtil.globalFormat(e.close.toString(), digit)
            tch_tv_amount.text = XFormatUtil.globalFormat(e.open.toString(), digit)
            tch_tv_amountRate.text = XFormatUtil.globalFormat(e.open.toString(), digit)
        }

    }

    /**
     * 自定义Entry的MarkerView的位置
     */
    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        val masterViewRectF = masterViewRectF()
        val chartWidth = masterViewRectF.right - masterViewRectF.left
        val markViewWidth = width
        val xOffset = ScreenUtils.dip2px(4f)
        val yOffset = ScreenUtils.dip2px(14f)

        val offset3 = mOffset3
        offset3.x = offset.x
        offset3.y = offset.y

        offset3.x = if (posX > chartWidth / 2f) {
            0f + masterViewRectF.left + xOffset
        } else {
            masterViewRectF.right - markViewWidth - xOffset
        }
        offset3.y = 0f + masterViewRectF.top + yOffset

        return offset3
    }

    /**
     * 自定义x、y轴上高亮线文字
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        //super.draw(canvas, posX, posY)
        Log.d(TAG, "draw: $posX,$posY")
        drawXMarker(canvas, posX)
        drawYMarker(canvas, posY)
        //绘制长按点的entry详情
        drawEntryMarker(posX, posY, canvas)
    }

    open fun drawEntryMarker(
        posX: Float,
        posY: Float,
        canvas: Canvas
    ) {
        val offset = getOffsetForDrawingAtPoint(posX, posY)

        val saveId = canvas.save()
        // translate to the correct position and draw
        canvas.translate(offset.x, offset.y)
        draw(canvas)
        canvas.restoreToCount(saveId)
    }

    /**
     * 绘制y轴的markerview。
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    open fun drawYMarker(canvas: Canvas, posY: Float) {
        val entry = mEntry ?: return
        val contentRect = masterViewRectF()
        val txt = XFormatUtil.globalFormat(entry.y.toString(), digit)
        val txtHeight =
            Utils.calcTextHeight(mMpTxtPaint, txt)
        val txtWidth = Utils.calcTextWidth(mMpTxtPaint, txt)
        val borderWidth = ScreenUtils.dip2px(2f).toFloat()
        val xL = contentRect.right + borderWidth
        val yL = posY - txtHeight / 2.0f
        val xR = contentRect.right + txtWidth + borderWidth
        val yR = posY + txtHeight / 2.0f
        //左右边距、上下边距
        val paddingLR = ScreenUtils.dip2px(2f).toFloat()
        val paddingTB = ScreenUtils.dip2px(2f).toFloat()
        //这里看似复杂的加减，主要为文字显示居中，背景加一个padding值和圆角
        val rectF = RectF(xL - paddingLR, yL, xR + paddingLR, yR + paddingTB + paddingTB)
        canvas.drawRoundRect(rectF, mRadius.toFloat(), mRadius.toFloat(), mMpBgPaint)
        val txtOffset = ScreenUtils.dip2px(1f).toFloat()
        canvas.drawText(txt, xL, yL + txtHeight + paddingTB - txtOffset, mMpTxtPaint)
    }

    /**
     * 绘制x轴的markerview,注意x轴多了一个边界校验。
     *
     * @param canvas
     * @param posX
     */
    open fun drawXMarker(canvas: Canvas, posX: Float) {
        val contentRect = masterViewRectF()
        val txt =
            TimeUtils.millis2String(posX.toLong())
        val txtHeight =
            Utils.calcTextHeight(mMpTxtPaint, txt)
        val txtWidth = Utils.calcTextWidth(mMpTxtPaint, txt)
        //左右边距、上下边距
        val paddingLR = ScreenUtils.dip2px(2f).toFloat()
        val paddingTB = ScreenUtils.dip2px(2f).toFloat()

        /**
         * 确认x轴左右边界，并且限制在contentRect范围内。y轴不用处理，因为本来y轴的显示范围本来上下就有padding值。
         */
        val finalPostX = when {
            posX - txtWidth / 2.0f <= contentRect.left -> {
                contentRect.left + txtWidth / 2.0f + paddingLR
            }
            posX + txtWidth / 2.0f >= contentRect.right -> {
                contentRect.right - txtWidth / 2.0f - paddingLR
            }
            else -> {
                //正常范围内
                posX
            }
        }
        val xL = finalPostX - txtWidth / 2.0f
        val yL = contentRect.bottom
        val xR = finalPostX + txtWidth / 2.0f
        val yR = contentRect.bottom + txtHeight


        //这里看似复杂的加减，主要为文字显示居中，背景加一个padding值和圆角
        val rectF = RectF(xL - paddingLR, yL, xR + paddingLR, yR + paddingTB + paddingTB)
        canvas.drawRoundRect(rectF, mRadius.toFloat(), mRadius.toFloat(), mMpBgPaint)
        val txtOffset = ScreenUtils.dip2px(1f).toFloat()
        canvas.drawText(txt, xL, yL + txtHeight + paddingTB - txtOffset, mMpTxtPaint)
    }

    /**
     * 获取主图绘制区域的范围
     *
     * @return
     */
    fun masterViewRectF(): RectF {
        val viewPortHandler = mMasterView.viewPortHandler
        //就是这个图表绘制线的区域
        return viewPortHandler.contentRect
    }

    @ColorInt
    fun colorResId(id: Int): Int {
        return ContextCompat.getColor(mContext, id)
    }
}