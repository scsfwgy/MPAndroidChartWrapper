package com.matt.mpwrapper.view.listener

import android.graphics.Matrix
import android.view.MotionEvent
import android.view.View
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet
import com.github.mikephil.charting.listener.BarLineChartTouchListener
import com.github.mikephil.charting.listener.ChartTouchListener
import com.matt.mpwrapper.view.MasterView

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/6/26 11:49 AM
 * 描 述 ：
 *
 **/
open class BaseBarLineChartTouchListener(
    chart: BarLineChartBase<out BarLineScatterCandleBubbleData<out IBarLineScatterCandleBubbleDataSet<out Entry>>>,
    touchMatrix: Matrix, dragTriggerDistance: Float
) :
    BarLineChartTouchListener(chart, touchMatrix, dragTriggerDistance) {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
                onActionMove(v, event)
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return super.onTouch(v, event)
    }

    open fun onActionMove(v: View, event: MotionEvent) {
        highlightByMove(event)
    }

    /**
     * 滑动时展示高亮线，触发条件：当[mChart.valuesToHighlight()]触发才会触发此动作
     */
    open fun highlightByMove(event: MotionEvent) {
        val chart = mChart
        val valuesToHighlight = chart.valuesToHighlight()
        if (valuesToHighlight) {
            triggerHighlight(event, true)
        }
    }

    override fun startAction(me: MotionEvent) {
        super.startAction(me)
    }

    override fun endAction(me: MotionEvent) {
        super.endAction(me)
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return super.onDoubleTapEvent(e)
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        return super.onDoubleTap(e)
    }

    /**
     * 触发单击,不会继续出发双击
     */
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        val chart = mChart
        if (chart is MasterView) {
            chart.mMasterViewDelegate.showIndicatorType(true)
        }
        return super.onSingleTapConfirmed(e)
    }

    /**
     * 单击抬起手指,可能会继续出发双击
     */
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        triggerHighlight(e, false)
        return super.onSingleTapUp(e)
    }

    /**
     * 长按触发
     */
    override fun onLongPress(e: MotionEvent) {
        super.onLongPress(e)
        val touchMode = touchMode
        //拖动时不触发高亮线
        if (touchMode == ChartTouchListener.DRAG) {
            return
        }
        triggerHighlight(e, true)
    }

    open fun triggerHighlight(e: MotionEvent, showHighlight: Boolean) {
        val chart = mChart
        if (showHighlight) {
            chart.isDragEnabled = false
            val highlightByTouchPoint = chart.getHighlightByTouchPoint(e.x, e.y)
            chart.highlightValue(highlightByTouchPoint, true)
        } else {
            chart.isDragEnabled = true
            chart.highlightValue(null, true)
        }
    }

}