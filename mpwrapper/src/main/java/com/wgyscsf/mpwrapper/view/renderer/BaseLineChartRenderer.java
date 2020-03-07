package com.wgyscsf.mpwrapper.view.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/10/16 17:37
 * 描 述 ：实现可以只绘制最后一个小圆点，如果设置mDrawLastCircles=true则不再走原有逻辑，只绘制最后一个小圆点。
 */
public class BaseLineChartRenderer extends LineChartRenderer {
    /**
     * 是否只绘制最后一个小圆点
     */
    protected boolean mDrawLastCircles = false;
    private float[] mCirclesBuffer = new float[2];
    private ChartAnimator mAnimator;


    public BaseLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.mAnimator = animator;
    }

    @Override
    public void drawExtras(Canvas c) {
        if (ismDrawLastCircles()) {
            drawLastCircle(c);
        }
        super.drawExtras(c);
    }

    /**
     * 绘制最后一个小圆点
     *
     * @param c
     */
    protected void drawLastCircle(Canvas c) {
        mRenderPaint.setStyle(Paint.Style.FILL);
        float phaseY = mAnimator.getPhaseY();
        mCirclesBuffer[0] = 0;
        mCirclesBuffer[1] = 0;

        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();

        if (dataSets == null || dataSets.isEmpty()) return;

        ILineDataSet dataSet = dataSets.get(dataSets.size() - 1);

        if (!dataSet.isVisible() ||
                dataSet.getEntryCount() == 0)
            return;

        mRenderPaint.setColor(dataSet.getCircleColor(0));
        mCirclePaintInner.setColor(dataSet.getCircleHoleColor());

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mXBounds.set(mChart, dataSet);

        float circleRadius = dataSet.getCircleRadius();
        float circleHoleRadius = dataSet.getCircleHoleRadius();
        boolean drawCircleHole = dataSet.isDrawCircleHoleEnabled() &&
                circleHoleRadius < circleRadius &&
                circleHoleRadius > 0.f;

        Entry e = dataSet.getEntryForIndex(dataSet.getEntryCount() - 1);

        if (e == null) return;

        mCirclesBuffer[0] = e.getX();
        mCirclesBuffer[1] = e.getY() * phaseY;

        trans.pointValuesToPixel(mCirclesBuffer);

        if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0]))
            return;

        if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) ||
                !mViewPortHandler.isInBoundsY(mCirclesBuffer[1]))
            return;

        c.drawCircle(
                mCirclesBuffer[0],
                mCirclesBuffer[1],
                circleRadius,
                mRenderPaint);

        if (drawCircleHole) {
            c.drawCircle(
                    mCirclesBuffer[0],
                    mCirclesBuffer[1],
                    circleHoleRadius,
                    mCirclePaintInner);
        }
    }

    public boolean ismDrawLastCircles() {
        return mDrawLastCircles;
    }

    public BaseLineChartRenderer setmDrawLastCircles(boolean mDrawLastCircles) {
        this.mDrawLastCircles = mDrawLastCircles;
        return this;
    }
}
