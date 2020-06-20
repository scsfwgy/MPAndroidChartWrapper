package com.matt.mpwrapper.view.delegate

import android.graphics.Paint
import android.graphics.drawable.Drawable
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.Boll
import com.matt.mpwrapper.bean.Ma
import com.matt.mpwrapper.ktx.getColor
import com.matt.mpwrapper.ktx.getDrawable
import com.matt.mpwrapper.utils.FormatUtil
import com.matt.mpwrapper.view.MasterView
import com.matt.mpwrapper.view.base.BaseLineDataSet
import com.matt.mpwrapper.view.type.BollType
import com.matt.mpwrapper.view.type.MaType
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MasterViewType
import java.lang.reflect.Field

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 15:14
 * 描 述 ：
 * ============================================================
 */
class MasterViewDelegate(masterView: MasterView) : BaseKViewDelegate(masterView) {
    val mMasterView by lazy {
        masterView
    }

    var mMasterViewType: MasterViewType = MasterViewType.CANDLE
    var mMasterIndicatorType: MasterIndicatorType = MasterIndicatorType.NONE

    //ma
    val mMasterViewMa5Color by lazy {
        getColor(R.color.mp_masterview_ma5)
    }
    val mMasterViewMa10Color by lazy {
        getColor(R.color.mp_masterview_ma10)
    }
    val mMasterViewMa20Color by lazy {
        getColor(R.color.mp_masterview_ma20)

    }

    //boll
    val mMasterViewBollUpColor by lazy {
        getColor(R.color.mp_masterview_bollup)

    }
    val mMasterViewBollMdColor by lazy {
        getColor(R.color.mp_masterview_bollmb)

    }
    val mMasterViewBollDnColor by lazy {
        getColor(R.color.mp_masterview_bolldn)

    }

    val mMasterTimeSharingColor by lazy {
        getColor(R.color.mp_basekview_timesharing)
    }
    val mMasterTimeSharingFillDrawable: Drawable by lazy {
        getDrawable(R.drawable.shape_gradient_filled)
    }


    val mTimeSharingEntryList by lazy {
        ArrayList<Entry>()
    }
    val mCandleEntryList by lazy {
        ArrayList<CandleEntry>()
    }
    val mMa5EntryList by lazy {
        ArrayList<Entry>()
    }
    val mMa10EntryList by lazy {
        ArrayList<Entry>()
    }
    val mMa20EntryList by lazy {
        ArrayList<Entry>()
    }
    val mBollUpEntryList by lazy {
        ArrayList<Entry>()
    }
    val mBollMdEntryList by lazy {
        ArrayList<Entry>()
    }
    val mBollDnEntryList by lazy {
        ArrayList<Entry>()
    }

    val mLimitLine by lazy {
        val limitLine = LimitLine(0.0f, FormatUtil.numFormat(0, mBaseKView.mDigit))
        limitLine.lineWidth = 0.5f
        limitLine.lineColor = mBaseKView.mBaseLimitColor
        limitLine.enableDashedLine(8f, 8f, 8f)
        limitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        mMasterView.axisRight.addLimitLine(limitLine)
        limitLine
    }

    /**
     * 需要反射
     */
    val mField: Field by lazy {
        val limitLine = mLimitLine
        val limitLineClass: Class<out LimitLine> = limitLine.javaClass
        val field = limitLineClass.getDeclaredField("mLimit")
        field.isAccessible = true
        field
    }

    //limit的值
    var mLimitValue = 0f

    val mTimeSharingDataSet by lazy {
        val timeSharingDataSet =
            BaseLineDataSet(mTimeSharingEntryList, MasterViewType.TIMESHARING.toString())
        /**
         * 设置单个蜡烛图value的值，一般都设置不显示
         */
        //设置单个蜡烛文字
        timeSharingDataSet.valueTextSize = 10.0f
        timeSharingDataSet.setDrawValues(false)

        /**
         * 线涨跌相关设置
         */
        //模式
        timeSharingDataSet.mode = LineDataSet.Mode.LINEAR
        timeSharingDataSet.color = mMasterTimeSharingColor
        timeSharingDataSet.lineWidth = 1f
        timeSharingDataSet.fillDrawable = mMasterTimeSharingFillDrawable
        //是否允许被填充，默认false
        timeSharingDataSet.setDrawFilled(true)
        /**
         * 绘制小圆点
         */
        timeSharingDataSet.setDrawCircles(false)
        timeSharingDataSet.setDrawCircleHole(false)
        timeSharingDataSet.circleRadius = 3f

        //长按高亮十字线
        timeSharingDataSet.isHighlightEnabled = true
        timeSharingDataSet.highLightColor = mBaseKView.mBaseHighLightColor
        timeSharingDataSet.highlightLineWidth = 0.5f
        //是否显示指示线
        timeSharingDataSet.setDrawHighlightIndicators(true)
        timeSharingDataSet.setDrawHorizontalHighlightIndicator(true)
        timeSharingDataSet.setDrawVerticalHighlightIndicator(true)

        timeSharingDataSet
    }

    val mCandleDataSet by lazy {
        val candleDataSet = CandleDataSet(mCandleEntryList, MasterViewType.CANDLE.toString())
        candleDataSet.setDrawIcons(false)
        candleDataSet.axisDependency = YAxis.AxisDependency.LEFT

        //Shadow：就是单个蜡烛图中间的线
        candleDataSet.shadowWidth = 1f
        //设置shadow和蜡烛图颜色一致
        candleDataSet.shadowColorSameAsCandle = true

        /**
         * 设置单个蜡烛图value的值，一般都设置不显示
         */
        //设置单个蜡烛文字
        candleDataSet.valueTextSize = 10f
        candleDataSet.setDrawValues(false)

        /**
         * 蜡烛图涨跌相关设置
         */
        //设置跌颜色
        candleDataSet.decreasingColor = mBaseKView.mDownColor
        candleDataSet.decreasingPaintStyle = Paint.Style.FILL
        //设置涨颜色
        candleDataSet.increasingColor = mBaseKView.mUpColor
        candleDataSet.increasingPaintStyle = Paint.Style.FILL
        //设置当收盘价和开盘价一样
        candleDataSet.neutralColor = mBaseKView.mBaseEqualColor

        //长按高亮十字线
        candleDataSet.isHighlightEnabled = false
        candleDataSet.highLightColor = mBaseKView.mBaseHighLightColor
        candleDataSet.highlightLineWidth = 0.5f
        //是否显示指示线
        candleDataSet.setDrawHighlightIndicators(true)
        candleDataSet.setDrawHorizontalHighlightIndicator(true)
        candleDataSet.setDrawVerticalHighlightIndicator(true)

        candleDataSet
    }

    val mMaLineDataSetArr by lazy {
        val ma5 = BaseLineDataSet(mMa5EntryList, MaType.MA5.toString())
        ma5.color = mMasterViewMa5Color
        val ma10 = BaseLineDataSet(mMa10EntryList, MaType.MA10.toString())
        ma10.color = mMasterViewMa10Color
        val ma20 = BaseLineDataSet(mMa20EntryList, MaType.MA20.toString())
        ma20.color = mMasterViewMa20Color
        arrayOf(ma5, ma10, ma20)
    }

    val mBollLineDataSetArr by lazy {
        val bollup = BaseLineDataSet(mBollUpEntryList, BollType.UP.toString())
        bollup.color = mMasterViewBollUpColor

        val bollmd = BaseLineDataSet(mBollMdEntryList, BollType.MD.toString())
        bollmd.color = mMasterViewBollMdColor

        val bollDn = BaseLineDataSet(mBollDnEntryList, BollType.DN.toString())
        bollDn.color = mMasterViewBollDnColor

        arrayOf(bollup, bollmd, bollDn)
    }

    fun setMaDataSetArrVisible(visible: Boolean) {
        setDataSetArrVisible(mMaLineDataSetArr, visible)
    }

    fun setBollDataSetArrVisible(visible: Boolean) {
        setDataSetArrVisible(mBollLineDataSetArr, visible)
    }

    fun getMaLegend(ma: Ma, press: Boolean): Array<LegendEntry> {
        return if (press) {
            val ma5 =
                generateLegendEntry(mMasterViewMa5Color, "MA5 " + numFormat(ma.ma5))
            val ma10 =
                generateLegendEntry(mMasterViewMa10Color, "MA10 " + numFormat(ma.ma10))
            val ma20 =
                generateLegendEntry(mMasterViewMa20Color, "MA20 " + numFormat(ma.ma20))
            arrayOf(ma5, ma10, ma20)
        } else {
            val legendEntry = LegendEntry()
            legendEntry.label = "MA(5,10,20)"
            legendEntry.formColor = mBaseKView.mBaseNoPressColor
            legendEntry.form = Legend.LegendForm.NONE
            arrayOf(legendEntry)
        }
    }

    fun getBollLegend(boll: Boll, press: Boolean): Array<LegendEntry> {
        when {
            press -> {
                val up =
                    generateLegendEntry(
                        mMasterViewBollUpColor,
                        "UPPER " + numFormat(boll.up)
                    )
                val md =
                    generateLegendEntry(
                        mMasterViewBollMdColor,
                        "MID " + numFormat(boll.mb)
                    )
                val dn =
                    generateLegendEntry(
                        mMasterViewBollDnColor,
                        "LOWER " + numFormat(boll.dn)
                    )
                return arrayOf(up, md, dn)
            }
            else -> {
                val legendEntry = LegendEntry()
                legendEntry.label = "BOLL(26)"
                legendEntry.formColor = mBaseKView.mBaseNoPressColor
                legendEntry.form = Legend.LegendForm.NONE
                return arrayOf(legendEntry)
            }
        }
    }

    fun showLegend(ma: Ma?, boll: Boll?, press: Boolean) {
        val masterView = mMasterView
        val legend = generateLegend(masterView.legend)
        val masterIndicatrixType = mMasterIndicatorType
        if (ma == null ||
            boll == null || mMasterViewType != MasterViewType.CANDLE ||
            masterIndicatrixType == MasterIndicatorType.NONE
        ) {
            legend.isEnabled = false
            return
        }
        legend.isEnabled = true
        val legendEntryArr = when (masterIndicatrixType) {
            MasterIndicatorType.MA -> {
                getMaLegend(ma, press)
            }
            MasterIndicatorType.BOLL -> {
                getBollLegend(boll, press)
            }
            else -> {
                null
            }
        }
        if (legendEntryArr != null) {
            legend.setCustom(legendEntryArr)
        } else {
            legend.isEnabled = false
        }
        masterView.legendRenderer.computeLegend(mBaseKView.data)
    }

    fun showIndicatrixType() {
        val masterView = mMasterView
        val masterIndicatorType: MasterIndicatorType = mMasterIndicatorType
        when {
            masterIndicatorType === MasterIndicatorType.NONE -> {
                setMaDataSetArrVisible(false)
                setBollDataSetArrVisible(false)
            }
            masterIndicatorType === MasterIndicatorType.MA -> {
                setMaDataSetArrVisible(true)
                setBollDataSetArrVisible(false)
            }
            masterIndicatorType === MasterIndicatorType.BOLL -> {
                setMaDataSetArrVisible(false)
                setBollDataSetArrVisible(true)
            }
            else -> {
                throw  IllegalArgumentException("MasterIndicatrixType 类型错误")
            }
        }
    }

    /**
     * 实时修改最新数据的实时线
     *
     * @param limitLine
     * @param c
     */
    fun setLimit(limitLine: LimitLine, c: Float) {
        try {
            this.mLimitValue = c
            mField.set(limitLine, c)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refreshLimit() {
        setLimit(mLimitLine, mLimitValue)
        mMasterView.invalidate()
    }

    private fun getColor(colorId: Int): Int {
        return mMasterView.getColor(colorId)
    }

    private fun getDrawable(drawableId: Int): Drawable {
        return mMasterView.getDrawable(drawableId)
    }
}