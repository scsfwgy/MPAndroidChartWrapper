package com.matt.mpwrapper.view.renderer

import android.graphics.Canvas
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.*
import com.github.mikephil.charting.renderer.LegendRenderer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/10/14 11:13
 * 描 述 ：重写图例渲染器实现图例文字的颜色和图例颜色一致。
 * ============================================================
 */
class BaseLegendRenderer(viewPortHandler: ViewPortHandler?, legend: Legend?) :
    LegendRenderer(viewPortHandler, legend) {
    /**
     * 看似这个地方很多逻辑，其实大部分都是父类的，这里继承主要是修改两行代码，修改lable paint的颜色绘制而已。
     * @param c
     */
    override fun renderLegend(c: Canvas) {
        if (!mLegend.isEnabled) return
        val tf = mLegend.typeface
        if (tf != null) mLegendLabelPaint.typeface = tf
        mLegendLabelPaint.textSize = mLegend.textSize
        mLegendLabelPaint.color = mLegend.textColor
        val labelLineHeight = Utils.getLineHeight(
            mLegendLabelPaint,
            legendFontMetrics
        )
        val labelLineSpacing =
            (Utils.getLineSpacing(
                mLegendLabelPaint,
                legendFontMetrics
            )
                    + Utils.convertDpToPixel(mLegend.yEntrySpace))
        val formYOffset =
            labelLineHeight - Utils.calcTextHeight(
                mLegendLabelPaint,
                "ABC"
            ) / 2f
        val entries = mLegend.entries
        val formToTextSpace =
            Utils.convertDpToPixel(mLegend.formToTextSpace)
        val xEntrySpace =
            Utils.convertDpToPixel(mLegend.xEntrySpace)
        val orientation = mLegend.orientation
        val horizontalAlignment = mLegend.horizontalAlignment
        val verticalAlignment = mLegend.verticalAlignment
        val direction = mLegend.direction
        val defaultFormSize =
            Utils.convertDpToPixel(mLegend.formSize)
        // space between the entries
        val stackSpace =
            Utils.convertDpToPixel(mLegend.stackSpace)
        val yoffset = mLegend.yOffset
        val xoffset = mLegend.xOffset
        var originPosX = 0f
        if (horizontalAlignment == LegendHorizontalAlignment.LEFT) {
            originPosX =
                if (orientation == LegendOrientation.VERTICAL) xoffset else mViewPortHandler.contentLeft() + xoffset
            if (direction == LegendDirection.RIGHT_TO_LEFT) originPosX += mLegend.mNeededWidth
        } else if (horizontalAlignment == LegendHorizontalAlignment.RIGHT) {
            originPosX =
                if (orientation == LegendOrientation.VERTICAL) mViewPortHandler.chartWidth - xoffset else mViewPortHandler.contentRight() - xoffset
            if (direction == LegendDirection.LEFT_TO_RIGHT) originPosX -= mLegend.mNeededWidth
        } else if (horizontalAlignment == LegendHorizontalAlignment.CENTER) {
            originPosX =
                if (orientation == LegendOrientation.VERTICAL) mViewPortHandler.chartWidth / 2f else mViewPortHandler.contentLeft()
            +mViewPortHandler.contentWidth() / 2f
            originPosX += if (direction == LegendDirection.LEFT_TO_RIGHT) +xoffset else -xoffset
            // Horizontally layed out legends do the center offset on a line basis,
            // So here we offset the vertical ones only.
            if (orientation == LegendOrientation.VERTICAL) {
                originPosX += (if (direction == LegendDirection.LEFT_TO_RIGHT) -mLegend.mNeededWidth / 2.0 + xoffset else mLegend.mNeededWidth / 2.0 - xoffset).toFloat()
            }
        }
        if (orientation == LegendOrientation.HORIZONTAL) {
            val calculatedLineSizes =
                mLegend.calculatedLineSizes
            val calculatedLabelSizes =
                mLegend.calculatedLabelSizes
            val calculatedLabelBreakPoints =
                mLegend.calculatedLabelBreakPoints
            var posX = originPosX
            var posY = 0f
            posY = when (verticalAlignment) {
                LegendVerticalAlignment.TOP -> yoffset
                LegendVerticalAlignment.BOTTOM -> mViewPortHandler.chartHeight - yoffset - mLegend.mNeededHeight
                LegendVerticalAlignment.CENTER -> (mViewPortHandler.chartHeight - mLegend.mNeededHeight) / 2f + yoffset
            }
            var lineIndex = 0
            var i = 0
            val count = entries.size
            while (i < count) {
                val e = entries[i]
                //注意这个地方重写
                mLegendLabelPaint.color = e.formColor
                val drawingForm = e.form != LegendForm.NONE
                val formSize =
                    if (java.lang.Float.isNaN(e.formSize)) defaultFormSize else Utils.convertDpToPixel(
                        e.formSize
                    )
                if (i < calculatedLabelBreakPoints.size && calculatedLabelBreakPoints[i]) {
                    posX = originPosX
                    posY += labelLineHeight + labelLineSpacing
                }
                if (posX == originPosX && horizontalAlignment == LegendHorizontalAlignment.CENTER && lineIndex < calculatedLineSizes.size
                ) {
                    posX += (if (direction == LegendDirection.RIGHT_TO_LEFT) calculatedLineSizes[lineIndex].width else -calculatedLineSizes[lineIndex].width) / 2f
                    lineIndex++
                }
                val isStacked =
                    e.label == null // grouped forms have null labels
                if (drawingForm) {
                    if (direction == LegendDirection.RIGHT_TO_LEFT) posX -= formSize
                    drawForm(c, posX, posY + formYOffset, e, mLegend)
                    if (direction == LegendDirection.LEFT_TO_RIGHT) posX += formSize
                }
                if (!isStacked) {
                    if (drawingForm) posX += if (direction == LegendDirection.RIGHT_TO_LEFT) -formToTextSpace else formToTextSpace
                    if (direction == LegendDirection.RIGHT_TO_LEFT) posX -= calculatedLabelSizes[i].width
                    drawLabel(c, posX, posY + labelLineHeight, e.label)
                    if (direction == LegendDirection.LEFT_TO_RIGHT) posX += calculatedLabelSizes[i].width
                    posX += if (direction == LegendDirection.RIGHT_TO_LEFT) -xEntrySpace else xEntrySpace
                } else posX += if (direction == LegendDirection.RIGHT_TO_LEFT) -stackSpace else stackSpace
                i++
            }
        } else if (orientation == LegendOrientation.VERTICAL) {
            // contains the stacked legend size in pixels
            var stack = 0f
            var wasStacked = false
            var posY = 0f
            when (verticalAlignment) {
                LegendVerticalAlignment.TOP -> {
                    posY =
                        if (horizontalAlignment == LegendHorizontalAlignment.CENTER) 0f else mViewPortHandler.contentTop()
                    posY += yoffset
                }
                LegendVerticalAlignment.BOTTOM -> {
                    posY =
                        if (horizontalAlignment == LegendHorizontalAlignment.CENTER) mViewPortHandler.chartHeight else mViewPortHandler.contentBottom()
                    posY -= mLegend.mNeededHeight + yoffset
                }
                LegendVerticalAlignment.CENTER -> posY = (mViewPortHandler.chartHeight / 2f
                        - mLegend.mNeededHeight / 2f
                        + mLegend.yOffset)
            }
            var i = 0
            while (i < entries.size) {
                val e = entries[i]
                //注意这个地方重写
                mLegendLabelPaint.color = e.formColor
                val drawingForm = e.form != LegendForm.NONE
                val formSize =
                    if (java.lang.Float.isNaN(e.formSize)) defaultFormSize else Utils.convertDpToPixel(
                        e.formSize
                    )
                var posX = originPosX
                if (drawingForm) {
                    if (direction == LegendDirection.LEFT_TO_RIGHT) posX += stack else posX -= formSize - stack
                    drawForm(c, posX, posY + formYOffset, e, mLegend)
                    if (direction == LegendDirection.LEFT_TO_RIGHT) posX += formSize
                }
                if (e.label != null) {
                    if (drawingForm && !wasStacked) posX += if (direction == LegendDirection.LEFT_TO_RIGHT) formToTextSpace else -formToTextSpace else if (wasStacked) posX =
                        originPosX
                    if (direction == LegendDirection.RIGHT_TO_LEFT) posX -= Utils.calcTextWidth(
                        mLegendLabelPaint,
                        e.label
                    ).toFloat()
                    if (!wasStacked) {
                        drawLabel(c, posX, posY + labelLineHeight, e.label)
                    } else {
                        posY += labelLineHeight + labelLineSpacing
                        drawLabel(c, posX, posY + labelLineHeight, e.label)
                    }
                    // make a step down
                    posY += labelLineHeight + labelLineSpacing
                    stack = 0f
                } else {
                    stack += formSize + stackSpace
                    wasStacked = true
                }
                i++
            }
        }
    }
}