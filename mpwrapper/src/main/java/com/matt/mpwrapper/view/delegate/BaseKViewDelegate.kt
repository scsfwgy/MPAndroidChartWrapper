package com.matt.mpwrapper.view.delegate

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.*
import com.matt.mpwrapper.ktx.dip2px
import com.matt.mpwrapper.ktx.getColor
import com.matt.mpwrapper.ktx.getDrawable
import com.matt.mpwrapper.utils.TimeUtils
import com.matt.mpwrapper.utils.XFormatUtil
import com.matt.mpwrapper.view.MpWrapperConfig
import com.matt.mpwrapper.view.base.BaseKView
import com.matt.mpwrapper.view.data.BaseLineDataSet
import com.matt.mpwrapper.view.listener.BaseBarLineChartTouchListener
import com.matt.mpwrapper.view.renderer.BaseCombinedChartRenderer

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 15:08
 * 描 述 ：
 * ============================================================
 */
open class BaseKViewDelegate(baseKView: BaseKView) {

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
        getColor(R.color.mp_basekview_bg)
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

    val mBaseKView by lazy {
        baseKView
    }

    val mCombinedData by lazy {
        CombinedData()
    }
    val mCandleData by lazy {
        CandleData()
    }
    val mBarData by lazy {
        BarData()
    }
    val mLineData by lazy {
        LineData()
    }

    val mShowHighlightEntryList by lazy {
        ArrayList<Entry>()
    }

    fun initChartAttrs() {
        mBaseKView.run {
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
            // 整个chat的外边距，相当于margin值
            setViewPortOffsets(dip2px(10.0f), dip2px(5.0f), dip2px(40.0f), dip2px(13.0f))
            //todo：干嘛的？
            setExtraOffsets(
                dip2px(0.0f),
                dip2px(0.0f),
                dip2px(0.0f),
                dip2px(0.0f)
            )
            val viewPortHandler = viewPortHandler
            //设置Y轴最大放大倍数
            viewPortHandler.setMaximumScaleY(1.0f)

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
            dragDecelerationFrictionCoef = 0.5f
            //设置在图表方向改变后是否仍然保持原来状态。
            isKeepPositionOnRotation = true
            //拖动时是否允许高亮线
            isHighlightPerDragEnabled = false
            //敲击显示高亮线
            isHighlightPerTapEnabled = false

            setDrawMarkers(true)


            //监听所有事件
            onTouchListener =
                BaseBarLineChartTouchListener(this, getViewPortHandler().matrixTouch, 3f)


            /**
             * 图例、描述相关
             */
            val legend = legend
            legend.isEnabled = true
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(true)
            legend.textSize = 8f
            legend.xOffset = 8f
            legend.yOffset = 8f
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
            renderer = BaseCombinedChartRenderer(this, animator, viewPortHandler)


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
            //是否绘制x轴上的值
            xAxis.setDrawLabels(false)
            //设置x轴上的值
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase): String {
                    val kViewData = mBaseInit.kViewDataList()
                    val valueInt = value.toInt()
                    val size = kViewData.size
                    val index = if (valueInt < size) valueInt else size - 1
                    val price = kViewData[index].price
                        ?: throw IllegalArgumentException("price字段为null,不允许为null")
                    return TimeUtils.millis2String(price.t, TimeUtils.getFormat("HH:mm:ss"))
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
                    return XFormatUtil.globalFormat(value.toDouble(), mBaseInit.digit())
                }
            }
            //零线,用处类似于macd的线，分上下两块，以0为分界线
            axisRight.setDrawZeroLine(false)
            axisRight.zeroLineWidth = 3f
        }
    }

    fun generateLegendEntry(formColor: Int, label: String): LegendEntry {
        val legendEntry = LegendEntry()
        legendEntry.form = Legend.LegendForm.CIRCLE
        //设置图例的大小
        legendEntry.formSize = 4f
        legendEntry.formColor = formColor
        legendEntry.label = label
        return legendEntry
    }

    fun generateLegend(legend: Legend): Legend {
        legend.textColor = mBaseYAxisTxtColor
        return legend
    }

    fun numFormat(data: Float?, digits: Int = mBaseKView.mBaseInit.digit()): String {
        if (data == null) return KViewConstant.VALUE_NULL_PLACEHOLDER
        return XFormatUtil.globalFormat(data.toDouble(), digits)
    }


    fun setLineDataSetArrVisible(arr: Array<out LineDataSet>, visible: Boolean) {
        val lineData = mLineData
        arr.forEach {
            if (visible) {
                if (!lineData.contains(it)) lineData.addDataSet(it)
            } else {
                if (lineData.contains(it)) lineData.removeDataSet(it)
            }
        }
    }

    fun setBarDataSetArrVisible(arr: Array<out BarDataSet>, visible: Boolean) {
        val barData = mBarData
        arr.forEach {
            if (visible) {
                if (!barData.contains(it)) barData.addDataSet(it)
            } else {
                if (barData.contains(it)) barData.removeDataSet(it)
            }
        }
    }

    fun getColor(@ColorRes colorId: Int): Int {
        return mBaseKView.getColor(colorId)
    }

    fun getDrawable(drawableId: Int): Drawable {
        return mBaseKView.getDrawable(drawableId)
    }

    fun getKViewDataListByIndex(index: Int): KViewData? {
        val kViewDataList = mBaseKView.mBaseInit.kViewDataList()
        if (index >= kViewDataList.size) return null
        return kViewDataList[index]
    }

    fun getMasterDataByIndex(index: Int): MasterData? {
        return getKViewDataListByIndex(index)?.masterData
    }

    fun getMinorDataByIndex(index: Int): MinorData? {
        return getKViewDataListByIndex(index)?.minorData
    }

    fun getVolDataByIndex(index: Int): VolData? {
        return getKViewDataListByIndex(index)?.volData
    }

    fun getUnPressLegend(label: String): Array<LegendEntry> {
        val legendEntry = LegendEntry()
        legendEntry.label = label
        legendEntry.formColor = mBaseNoPressColor
        legendEntry.form = Legend.LegendForm.NONE
        return arrayOf(legendEntry)
    }

    val mShowHighlightLineData by lazy {
        val baseLineDataSet = BaseLineDataSet(
            mShowHighlightEntryList,
            "show line"
        )
        baseLineDataSet.isHighlightEnabled = true
        baseLineDataSet.highLightColor = mBaseHighLightColor
        baseLineDataSet.color = Color.TRANSPARENT
        baseLineDataSet
    }
}