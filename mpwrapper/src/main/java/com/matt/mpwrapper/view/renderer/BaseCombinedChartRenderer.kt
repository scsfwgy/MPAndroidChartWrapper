package com.matt.mpwrapper.view.renderer

import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder
import com.github.mikephil.charting.renderer.BarChartRenderer
import com.github.mikephil.charting.renderer.BubbleChartRenderer
import com.github.mikephil.charting.renderer.CombinedChartRenderer
import com.github.mikephil.charting.renderer.ScatterChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/09/30 14:41
 * 描 述 ：CombinedChart的渲染器，本质上来说只是一个容器，主要作用遍历子组件获取子组件的渲染器。
 * 因此想要重写子组件渲染器，需要重写这个渲染器进而添加子组件的渲染器。
 * ============================================================
 */
class BaseCombinedChartRenderer(
    chart: CombinedChart,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler
) : CombinedChartRenderer(chart, animator, viewPortHandler) {
    private var mDrawLineRenderLastCircle = false

    /**
     * 重写这个方法主要为了重写内部子渲染器
     */
    override fun createRenderers() {
        mRenderers.clear()
        val chart = mChart.get()
        if (chart !is CombinedChart) return
        val orders = chart.drawOrder
        for (order in orders) {
            if (order == null) continue
            val dataRenderer = when (order) {
                DrawOrder.BAR -> {
                    if (chart.barData != null) {
                        BarChartRenderer(chart, mAnimator, mViewPortHandler)
                    } else {
                        null
                    }
                }
                DrawOrder.BUBBLE -> {
                    if (chart.bubbleData != null) {
                        BubbleChartRenderer(chart, mAnimator, mViewPortHandler)
                    } else {
                        null
                    }
                }
                DrawOrder.LINE -> {
                    if (chart.lineData != null) {
                        val baseLineChartRenderer =
                            BaseLineChartRenderer(chart, mAnimator, mViewPortHandler)
                        baseLineChartRenderer.setDrawLastCircles(isDrawLineRenderLastCircle())
                        baseLineChartRenderer
                    } else {
                        null
                    }
                }
                DrawOrder.CANDLE -> {
                    if (chart.candleData != null) {
                        MasterViewCandleStickChartRenderer(chart, mAnimator, mViewPortHandler)
                    } else {
                        null
                    }
                }
                DrawOrder.SCATTER -> {
                    if (chart.scatterData != null) {
                        ScatterChartRenderer(chart, mAnimator, mViewPortHandler)
                    } else {
                        null
                    }
                }
                else -> null
            }
            if (dataRenderer != null) {
                mRenderers.add(dataRenderer)
            }
        }
    }

    fun isDrawLineRenderLastCircle(): Boolean {
        return mDrawLineRenderLastCircle
    }

    fun setDrawLineRenderLastCircle(mDrawLineRenderLastCircle: Boolean): BaseCombinedChartRenderer {
        this.mDrawLineRenderLastCircle = mDrawLineRenderLastCircle
        return this
    }
}