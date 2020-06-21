package com.matt.mpwrapper.view.marker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.Utils
import com.matt.mpwrapper.R
import com.matt.mpwrapper.utils.ScreenUtils
import com.matt.mpwrapper.utils.TimeUtils
import com.matt.mpwrapper.view.MasterView

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/29 14:12
 * 描 述 ：主图的markerview，何为markerview:就是长按高亮线上显示的文字，
 * 默认情况下显示在x、y轴高亮线交互点处右下角显示。
 * 现在自定义其在x轴内侧显示x的数值，在y轴外侧显示y轴数值。
 * ============================================================
 */
class MasterViewMarker(private val mMasterView: MasterView, val mContext: Context) :
    MarkerView(mContext, R.layout.markerview_k_view_shell) {

    //绘制背景
    private var mMpTxtColor = 0
    private var mMpTxtPaint: Paint? = null
    private var mMpBgColor = 0
    private var mMpBgPaint: Paint? = null

    //背景的圆角
    private val mRadius = ScreenUtils.dip2px(2f)
    private var mEntry: Entry? = null
    private fun initPaint() {
        mMpTxtPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mMpTxtPaint!!.textSize = ScreenUtils.dip2px(8f).toFloat()
        mMpTxtPaint!!.color = mMpTxtColor
        mMpBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mMpBgPaint!!.color = mMpBgColor
    }

    private fun initAttrs() {
        mMpTxtColor = colorResId(R.color.mp_marker_txtColor)
        mMpBgColor = colorResId(R.color.mp_marker_bgColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun refreshContent(
        e: Entry,
        highlight: Highlight
    ) {
        mEntry = e
        super.refreshContent(e, highlight)
    }

    /**
     * posX、posY就是十字高亮的十字交叉点
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    override fun draw(
        canvas: Canvas,
        posX: Float,
        posY: Float
    ) {
        super.draw(canvas, posX, posY)
        Log.d(TAG, "draw: $posX,$posY")
        val chartView = chartView ?: return
        if (mEntry == null) return
        drawXMarker(canvas, posX, posY)
        drawYMarker(canvas, posX, posY)
    }

    /**
     * 绘制y轴的markerview。
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    private fun drawYMarker(
        canvas: Canvas,
        posX: Float,
        posY: Float
    ) {
        val contentRect = masterViewRectF
        val txt = mEntry!!.y.toString()
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
        canvas.drawRoundRect(rectF, mRadius.toFloat(), mRadius.toFloat(), mMpBgPaint!!)
        val txtOffset = ScreenUtils.dip2px(1f).toFloat()
        canvas.drawText(txt, xL, yL + txtHeight + paddingTB - txtOffset, mMpTxtPaint!!)
    }

    /**
     * 绘制x轴的markerview,注意x轴多了一个边界校验。
     *
     * @param canvas
     * @param posX
     * @param posY
     */
    private fun drawXMarker(
        canvas: Canvas,
        posX: Float,
        posY: Float
    ) {
        var posX = posX
        val contentRect = masterViewRectF
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
        when {
            posX - txtWidth / 2.0f <= contentRect.left -> {
                posX = contentRect.left + txtWidth / 2.0f + paddingLR
            }
            posX + txtWidth / 2.0f >= contentRect.right -> {
                posX = contentRect.right - txtWidth / 2.0f - paddingLR
            }
            else -> {
                //正常范围内
            }
        }
        val xL = posX - txtWidth / 2.0f
        val yL = contentRect.top
        val xR = posX + txtWidth / 2.0f
        val yR = contentRect.top + txtHeight


        //这里看似复杂的加减，主要为文字显示居中，背景加一个padding值和圆角
        val rectF = RectF(xL - paddingLR, yL, xR + paddingLR, yR + paddingTB + paddingTB)
        canvas.drawRoundRect(rectF, mRadius.toFloat(), mRadius.toFloat(), mMpBgPaint!!)
        val txtOffset = ScreenUtils.dip2px(1f).toFloat()
        canvas.drawText(txt, xL, yL + txtHeight + paddingTB - txtOffset, mMpTxtPaint!!)
    }//就是这个图表绘制线的区域

    /**
     * 获取主图绘制区域的范围
     *
     * @return
     */
    private val masterViewRectF: RectF
        get() {
            val viewPortHandler = mMasterView.viewPortHandler
            //就是这个图表绘制线的区域
            return viewPortHandler.contentRect
        }

    @ColorInt
    fun colorResId(id: Int): Int {
        return ContextCompat.getColor(mContext, id)
    }

    companion object {
        const val TAG = "BaseKViewMarker"
    }

    init {
        initAttrs()
        initPaint()
    }
}