package com.matt.mpwrapper.view.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.CombinedData
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.Utils
import com.matt.mpwrapper.R
import com.matt.mpwrapper.ktx.dip2px
import com.matt.mpwrapper.ktx.getColor
import com.matt.mpwrapper.view.MpWrapperConfig

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


    val mGreenUp by lazy {
        MpWrapperConfig.mConfig.greenUp
    }

    val mRedColor: Int by lazy {
        getColor(R.color.mp_basekview_red)
    }
    val mGreenColor: Int by lazy {
        getColor(R.color.mp_basekview_green)
    }
    val mUpColor: Int by lazy {
        if (mGreenUp) mGreenColor else mRedColor
    }
    val mDownColor: Int by lazy {
        if (!mGreenUp) mGreenColor else mRedColor
    }
    val mBaseEqualColor: Int by lazy {
        getColor(R.color.mp_basekview_equal)
    }
    val mBaseBorderColor: Int by lazy {
        getColor(R.color.mp_basekview_border)
    }
    val mBaseGridBgColor: Int by lazy {
        getColor(R.color.mp_basekview_gridBg)
    }
    val mBaseBgColor: Int by lazy {
        getColor(R.color.colorPrimary)
    }
    val mBaseXAxisTxtColor: Int by lazy {
        getColor(R.color.mp_basekview_xAxisTxtColor)
    }
    val mBaseXAxisLineColor: Int by lazy {
        getColor(R.color.mp_basekview_xAxisLineColor)
    }
    val mBaseYAxisTxtColor: Int by lazy {
        getColor(R.color.mp_basekview_yAxisTxtColor)
    }
    val mBaseYAxisLineColor: Int by lazy {
        getColor(R.color.mp_basekview_yAxisLineColor)
    }
    val mBaseHighLightColor: Int by lazy {
        getColor(R.color.mp_basekview_highLightColor)
    }

    val mBaseLimitColor by lazy {
        getColor(R.color.mp_basekview_limit)
    }
    val mBaseNoPressColor by lazy {
        getColor(R.color.mp_basekview_noPressLegend)
    }
    var mDigit: Int = 4

    init {
        initChartAttrs()
    }

    private fun initChartAttrs() {
        /**
         * 背景边框相关
         */
        //是否绘制边框
        setDrawBorders(true)
        setBorderColor(mBaseBorderColor)
        //边框宽度
        setBorderWidth(1.0f)
        //是否绘制Grid背景
        setDrawGridBackground(true)
        //设置Grid背景的颜色，这个就是所有有x、y轴线的背景色
        setGridBackgroundColor(mBaseGridBgColor)
        //设置整个控件的背景色,注意和setDrawGridBackground的区别
        setBackgroundColor(mBaseBgColor)

        /**
         * 边距相关
         */
        // TODO: 05/11/2018 具体干嘛的？？？
        //setViewPortOffsets(dip2px(10.0f), dip2px(5.0f), dip2px(40.0f), dip2px(13.0f));
        //设置外边距，相当于margin值
        setExtraOffsets(
            Utils.convertDpToPixel(0.0f),
            dip2px(0.0f),
            dip2px(0.0f),
            dip2px(0.0f)
        )
        //设置Y轴最大放大倍数
        mViewPortHandler.setMaximumScaleY(1.0f)


        /**
         * 缩放相关
         */
        //是否允许x、y轴的缩放
        setScaleEnabled(false)
        //是否允许x轴缩放
        isScaleXEnabled = true
        //是否允许y轴缩放
        isScaleYEnabled = false
        // TODO:对这个属性存在疑问，如果我设置setScaleYEnabled(false);并不会再开启y轴缩放。
        //是否允许两指同时缩放x、y轴。如果设置为true,则会开启x、y轴的两指缩放。
        setPinchZoom(true)
        //是否允许双击放大
        isDoubleTapToZoomEnabled = false
        isAutoScaleMinMaxEnabled = true
        setDrawBarShadow(false)

        /**
         * 拖拽相关
         */
        //是否允许x、y轴的拖拽
        isDragEnabled = false
        //是否允许y轴的拖拽
        isDragYEnabled = false
        //是否允许x轴的拖拽
        isDragXEnabled = true
        //是否允许惯性滑动。换句话说就是手指松开后是否允许根据惯性继续滑行一段距离。
        isDragDecelerationEnabled = true
        //惯性滑动的大小[0,1)。越大惯性越大。
        dragDecelerationFrictionCoef = 0.9f
        // TODO: 23/09/2018
        isHighlightPerDragEnabled = false
        //敲击显示高亮线
        isHighlightPerTapEnabled = false
        //设置在图表方向改变后是否仍然保持原来状态。
        isKeepPositionOnRotation = true
        //
        isHighlightPerDragEnabled = false


        /**
         * 图例、描述相关
         */
        val lineChartLegend = legend
        lineChartLegend.isEnabled = true
        lineChartLegend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        lineChartLegend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        lineChartLegend.orientation = Legend.LegendOrientation.HORIZONTAL
        lineChartLegend.setDrawInside(true)
        lineChartLegend.textSize = 8f
        lineChartLegend.xOffset = 8f
        lineChartLegend.yOffset = 18f
        //注意重写了图例渲染器
        //mLegendRenderer = BaseLegendRenderer(mViewPortHandler, mLegend)

        //描述
        val description =
            Description()
        description.text = "hello,world"
        description.isEnabled = false
        setDescription(description)

        /**
         * 动画
         */
        animateX(1000)


        /**
         * 渲染器
         */
        //renderer = BaseCombinedChartRenderer(this, mAnimator, mViewPortHandler)


        /**
         * x轴相关设置
         */
        val xAxis = xAxis
        xAxis.setAvoidFirstLastClipping(true)
        //x轴指示文字显示的位置。
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        //设置网格
        xAxis.setDrawGridLines(true)
        //设置x轴网格数,设置true,可以强制固定label的位置。（当然mp不是这样解释的）。
        xAxis.setLabelCount(5, true)
        //X轴刻度线颜色
        xAxis.gridColor = mBaseXAxisLineColor
        //设置网格线为虚线
        xAxis.enableGridDashedLine(10f, 10f, 0f)
        //设置网格线宽度
        xAxis.gridLineWidth = 0.5f
        //X轴文字颜色
        xAxis.textColor = mBaseXAxisTxtColor
        xAxis.textSize = 8f
        //设置x轴左边距,左边边距不设置第一条会设置一半。
        xAxis.spaceMin = 0.5f
        //设置x轴右边距
        xAxis.spaceMax = 5f
        //设置文字显示的角度，0~360
        xAxis.labelRotationAngle = 0f
        //将间隔限制为1（最小）
        //xAxis.setGranularity(1f);
        //设置x轴上的值
        //将间隔限制为1（最小）
        //xAxis.setGranularity(1f);
        //设置x轴上的值
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return value.toString()
            }
        }


        /**
         * 设置为false会使setAutoScaleMinMaxEnabled(true)在绘制蜡烛图时失效
         */
        //y轴左侧
        val axisLeft = axisLeft
        axisLeft.isEnabled = true
        axisLeft.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        axisLeft.setDrawAxisLine(false)
        axisLeft.setDrawGridLines(false)
        axisLeft.setLabelCount(0, true)
        axisLeft.setDrawLabels(false) //禁止绘制y轴的值


        //y轴右侧
        val axisRight = axisRight
        axisRight.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        axisRight.setDrawAxisLine(false)
        axisRight.setDrawGridLines(true)
        axisRight.setLabelCount(5, true)
        axisRight.enableGridDashedLine(10f, 10f, 0f)
        axisRight.gridLineWidth = 0.5f
        axisRight.axisLineWidth = 0.5f
        axisRight.gridColor = mBaseYAxisLineColor
        axisRight.textColor = mBaseYAxisTxtColor
        axisRight.textSize = 8f
        //将间隔限制为1（最小）
        //axisRight.setGranularity(1f);
        //上边界内边距，也就是k线上面到边框的距离
        //将间隔限制为1（最小）
        //axisRight.setGranularity(1f);
        //上边界内边距，也就是k线上面到边框的距离
        axisRight.spaceTop = 10f

        axisRight.valueFormatter = object :
            ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return value.toString()
            }
        }
        //零线,用处类似于macd的线，分上下两块，以0为分界线
        axisRight.setDrawZeroLine(false)
        axisRight.zeroLineWidth = 3f
    }

    fun setDigit(digit: Int) {
        mDigit = digit
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