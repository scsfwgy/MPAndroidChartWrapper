package com.matt.mpwrapper.view.listener

import android.view.MotionEvent
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/6/27 8:58 PM
 * 描 述 ：
 *
 **/
class LinkChartListener(
    private val mMasterChart: CombinedChart,
    private val mFollowCharts: Array<CombinedChart>
) :
    OnChartGestureListener {

    fun getFollowCharts(): Array<CombinedChart> {
        return mFollowCharts
    }


    fun linkCharts() {
        val masterMatrix = mMasterChart.viewPortHandler.matrixTouch
        val masterValue = FloatArray(9) {
            0f
        }
        masterMatrix.getValues(masterValue)

        val followValue = FloatArray(9) {
            0f
        }

        mFollowCharts.forEach {
            it.viewPortHandler.matrixTouch.getValues(followValue)
        }

        masterValue.forEachIndexed { index, fl ->
            followValue[index] = fl
        }

        mFollowCharts.forEach {
            it.viewPortHandler.matrixTouch.setValues(followValue)
        }

        mFollowCharts.forEach {
            it.viewPortHandler.refresh(it.viewPortHandler.matrixTouch, it, true)
        }
    }

    override fun onChartGestureEnd(
        me: MotionEvent,
        lastPerformedGesture: ChartTouchListener.ChartGesture
    ) {
        linkCharts()
    }

    override fun onChartFling(
        me1: MotionEvent,
        me2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ) {
        linkCharts()

    }

    override fun onChartSingleTapped(me: MotionEvent) {
        linkCharts()

    }

    override fun onChartGestureStart(
        me: MotionEvent,
        lastPerformedGesture: ChartTouchListener.ChartGesture
    ) {
        linkCharts()

    }

    override fun onChartScale(me: MotionEvent, scaleX: Float, scaleY: Float) {
        linkCharts()

    }

    override fun onChartLongPressed(me: MotionEvent) {
        linkCharts()

    }

    override fun onChartDoubleTapped(me: MotionEvent) {
        linkCharts()

    }

    override fun onChartTranslate(me: MotionEvent, dX: Float, dY: Float) {
        linkCharts()
    }
}