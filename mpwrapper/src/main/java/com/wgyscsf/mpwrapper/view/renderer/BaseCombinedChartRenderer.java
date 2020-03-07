package com.wgyscsf.mpwrapper.view.renderer;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.BubbleChartRenderer;
import com.github.mikephil.charting.renderer.CombinedChartRenderer;
import com.github.mikephil.charting.renderer.ScatterChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/09/30 14:41
 * 描 述 ：CombinedChart的渲染器，本质上来说只是一个容器，主要作用遍历子组件获取子组件的渲染器。
 * 因此想要重写子组件渲染器，需要重写这个渲染器进而添加子组件的渲染器。
 * ============================================================
 */
public class BaseCombinedChartRenderer extends CombinedChartRenderer {
    private boolean mDrawLineRenderLastCircle = false;

    public BaseCombinedChartRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    /**
     * 重写这个方法主要为了重写内部子渲染器
     */
    @Override
    public void createRenderers() {

        mRenderers.clear();

        CombinedChart chart = (CombinedChart) mChart.get();
        if (chart == null)
            return;

        CombinedChart.DrawOrder[] orders = chart.getDrawOrder();

        for (CombinedChart.DrawOrder order : orders) {

            switch (order) {
                case BAR:
                    if (chart.getBarData() != null)
                        mRenderers.add(new BarChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case BUBBLE:
                    if (chart.getBubbleData() != null)
                        mRenderers.add(new BubbleChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case LINE:
                    if (chart.getLineData() != null) {
                        BaseLineChartRenderer baseLineChartRenderer = new BaseLineChartRenderer(chart, mAnimator, mViewPortHandler);
                        baseLineChartRenderer.setmDrawLastCircles(ismDrawLineRenderLastCircle());
                        mRenderers.add(baseLineChartRenderer);
                    }
                    break;
                case CANDLE:
                    if (chart.getCandleData() != null)
                        mRenderers.add(new MasterViewCandleStickChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
                case SCATTER:
                    if (chart.getScatterData() != null)
                        mRenderers.add(new ScatterChartRenderer(chart, mAnimator, mViewPortHandler));
                    break;
            }
        }
    }

    public boolean ismDrawLineRenderLastCircle() {
        return mDrawLineRenderLastCircle;
    }

    public BaseCombinedChartRenderer setmDrawLineRenderLastCircle(boolean mDrawLineRenderLastCircle) {
        this.mDrawLineRenderLastCircle = mDrawLineRenderLastCircle;
        return this;
    }
}
