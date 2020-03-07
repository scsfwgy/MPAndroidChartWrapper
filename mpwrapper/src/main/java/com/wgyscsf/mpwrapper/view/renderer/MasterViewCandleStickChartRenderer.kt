package com.wgyscsf.mpwrapper.view.renderer

import android.graphics.Canvas
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider
import com.github.mikephil.charting.renderer.CandleStickChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/09/30 14:35
 * 描 述 ：长按高亮线交叉点显示(最大值+最小值)/2.0f,修改为显示收盘价。
 * ============================================================
 */
class MasterViewCandleStickChartRenderer(
    chart: CandleDataProvider?,
    animator: ChartAnimator?,
    viewPortHandler: ViewPortHandler?
) : CandleStickChartRenderer(chart, animator, viewPortHandler) {
    override fun drawHighlighted(
        c: Canvas,
        indices: Array<Highlight>
    ) {
        val candleData = mChart.candleData
        for (high in indices) {
            val set = candleData.getDataSetByIndex(high.dataSetIndex)
            if (set == null || !set.isHighlightEnabled) continue
            val e = set.getEntryForXValue(high.x, high.y)
            if (!isInBoundsX(e, set)) continue
            //            float lowValue = e.getLow() * mAnimator.getPhaseY();
//            float highValue = e.getHigh() * mAnimator.getPhaseY();
//            float y = (lowValue + highValue) / 2f;
//重写这个值
            val closeValue = e.close * mAnimator.phaseY
            val pix =
                mChart.getTransformer(set.axisDependency).getPixelForValues(e.x, closeValue)
            high.setDraw(pix.x.toFloat(), pix.y.toFloat())
            // draw the lines
            drawHighlightLines(c, pix.x.toFloat(), pix.y.toFloat(), set)
        }
    }
}