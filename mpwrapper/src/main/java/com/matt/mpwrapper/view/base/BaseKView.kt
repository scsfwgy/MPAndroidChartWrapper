package com.matt.mpwrapper.view.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.github.mikephil.charting.data.CombinedData
import com.matt.mpwrapper.view.charts.BaseCombinedChart
import com.matt.mpwrapper.view.delegate.BaseKViewDelegate

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 11:39
 * 描 述 ：
 * ============================================================
 */
abstract class BaseKView(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : BaseCombinedChart(context, attributeSet, defStyle) {

    protected val mDefMinCount = 40
    protected val mDefMaxCount = 100
    protected val mDefShowCount = 70

    lateinit var mBaseInit: BaseInit

    val mBaseKViewDelegate by lazy {
        BaseKViewDelegate(this)
    }

    init {
        initChartAttrs()
    }

    fun initBaseK(baseInit: BaseInit) {
        this.mBaseInit = baseInit
    }

    private fun initChartAttrs() {
        mBaseKViewDelegate.initChartAttrs()
    }

    /**
     * 设置X轴放大系数
     */
    open fun showDefCount(size: Int) {
        var scale: Float = size / mDefShowCount.toFloat()
        if (scale < 1) {
            scale = 1f
        }
        //设置右边距离
        mXAxis.spaceMax = size * 0.15f / scale
        Log.d("BaseChart", "postScaleX: $size  scale= $scale")
        mViewPortHandler.zoom(scale, 1f, mViewPortHandler.matrixTouch)
    }


    /**
     * 设置最终数据
     */
    fun setKViewData(data: CombinedData, allDataSize: Int, reload: Boolean = true) {
        showDefCount(allDataSize)
        //调用系统方法
        setData(data)
        //设置最大和最小值
        setVisibleXRange(mDefMaxCount.toFloat(), mDefMinCount.toFloat())
        if (reload) {
            //移动到尾部
            moveViewToX(getData().entryCount.toFloat())
        }
    }
}