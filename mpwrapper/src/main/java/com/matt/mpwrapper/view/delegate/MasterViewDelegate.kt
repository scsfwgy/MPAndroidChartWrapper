package com.matt.mpwrapper.view.delegate

import android.graphics.drawable.Drawable
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.Boll
import com.matt.mpwrapper.bean.Ma
import com.matt.mpwrapper.bean.MasterData
import com.matt.mpwrapper.utils.XFormatUtil
import com.matt.mpwrapper.view.MasterView
import com.matt.mpwrapper.view.components.MasterViewMarker
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
    var mMasterIndicatorType: MasterIndicatorType = MasterIndicatorType.MA

    //ma
    val mMaColorArr by lazy {
        arrayOf(
            getColor(R.color.mp_masterview_ma5),
            getColor(R.color.mp_masterview_ma10),
            getColor(R.color.mp_masterview_ma20)
        )
    }

    //boll
    val mBollColorArr by lazy {
        arrayOf(
            getColor(R.color.mp_masterview_bollup),
            getColor(R.color.mp_masterview_bollmb),
            getColor(R.color.mp_masterview_bolldn)
        )
    }

    val mMaType by lazy {
        arrayOf(MaType.MA5, MaType.MA10, MaType.MA20)
    }

    val mBollType by lazy {
        arrayOf(BollType.UP, BollType.MD, BollType.DN)
    }

    val mMasterTimeSharingColor by lazy {
        getColor(R.color.mp_basekview_timesharing)
    }
    val mMasterTimeSharingFillDrawable: Drawable by lazy {
        getDrawable(R.drawable.shape_gradient_filled)
    }

    val mLimitLine by lazy {
        val limitLine = LimitLine(0.0f, XFormatUtil.globalFormat(0.0, mBaseKView.mBaseInit.digit()))
        limitLine.lineWidth = 0.5f
        limitLine.lineColor = mBaseLimitColor
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

    val mTimeSharingDataSet by lazy {
        val timeSharingDataSet =
            mCombinedDataControl.getLineDataSet(MasterViewType.TIMESHARING.toString())
        /**
         * 线涨跌相关设置
         */
        //模式
        timeSharingDataSet.color = mMasterTimeSharingColor
        timeSharingDataSet.fillDrawable = mMasterTimeSharingFillDrawable
        //是否允许被填充，默认false
        timeSharingDataSet.setDrawFilled(true)

        //长按高亮十字线
        timeSharingDataSet.isHighlightEnabled = true
        timeSharingDataSet.highLightColor = mBaseHighLightColor
        //允许x轴高亮线
        timeSharingDataSet.setDrawHighlightIndicators(true)

        timeSharingDataSet
    }

    val mCandleDataSet by lazy {
        val candleDataSet = mCombinedDataControl.getCandleDataSet(MasterViewType.CANDLE.toString())
        /**
         * 蜡烛图涨跌相关设置
         */
        //设置跌颜色
        candleDataSet.decreasingColor = mDownColor
        //设置涨颜色
        candleDataSet.increasingColor = mUpColor

        //长按高亮十字线
        candleDataSet.highLightColor = mBaseHighLightColor

        candleDataSet
    }

    val mMaLineDataSetArr by lazy {
        val map = arrayOf(0, 1, 2)
            .map {
                val maType = mMaType[it]
                val color = mMaColorArr[it]
                val baseLineDataSet = mCombinedDataControl.getLineDataSet(maType.toString())
                baseLineDataSet.color = color
                baseLineDataSet
            }.toTypedArray()
        map
    }

    val mBollLineDataSetArr by lazy {
        val map = arrayOf(0, 1, 2)
            .map {
                val maType = mBollType[it]
                val color = mBollColorArr[it]
                val baseLineDataSet = mCombinedDataControl.getLineDataSet(maType.toString())
                baseLineDataSet.color = color
                baseLineDataSet
            }.toTypedArray()
        map
    }


    override fun initChart() {
        super.initChart()
        mMasterView.run {
            //marker
            val masterViewMarker =
                MasterViewMarker(
                    this,
                    mContext,
                    4
                )
            masterViewMarker.chartView = this
            marker = masterViewMarker

            setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onNothingSelected() {
                    showLegend(
                        getMasterDataByIndex(mBaseKView.mBaseInit.kViewDataList().size - 1),
                        false
                    )
                }

                override fun onValueSelected(e: Entry, h: Highlight) {
                    //这样使用的前提是设置值的时候x轴是用的list的索引
                    val index = e.x.toInt()
                    showLegend(getMasterDataByIndex(index), true)
                }

            })
        }
    }

    fun setMaDataSetArrVisible(visible: Boolean) {
        setLineDataSetArrVisible(mMaLineDataSetArr, visible)
    }

    fun setBollDataSetArrVisible(visible: Boolean) {
        setLineDataSetArrVisible(mBollLineDataSetArr, visible)
    }

    fun getMaLegend(ma: Ma, press: Boolean): Array<LegendEntry> {
        return if (press) {
            val ma5 =
                generateLegendEntry(mMaColorArr[0], "MA5 " + numFormat(ma.ma5))
            val ma10 =
                generateLegendEntry(mMaColorArr[1], "MA10 " + numFormat(ma.ma10))
            val ma20 =
                generateLegendEntry(mMaColorArr[2], "MA20 " + numFormat(ma.ma20))
            arrayOf(ma5, ma10, ma20)
        } else {
            getUnPressLegend("MA(5,10,20)")
        }
    }

    fun getBollLegend(boll: Boll, press: Boolean): Array<LegendEntry> {
        when {
            press -> {
                val up =
                    generateLegendEntry(
                        mBollColorArr[0],
                        "UPPER " + numFormat(boll.up)
                    )
                val md =
                    generateLegendEntry(
                        mBollColorArr[1],
                        "MID " + numFormat(boll.mb)
                    )
                val dn =
                    generateLegendEntry(
                        mBollColorArr[2],
                        "LOWER " + numFormat(boll.dn)
                    )
                return arrayOf(up, md, dn)
            }
            else -> {
                return getUnPressLegend("BOLL(26)")
            }
        }
    }

    fun showLegend(masterData: MasterData?, press: Boolean) {
        val masterView = mMasterView
        val masterViewType = mMasterViewType
        val masterIndicatorType = mMasterIndicatorType
        val legend = generateLegend(masterView.legend)
        val unShowLegend =
            masterViewType != MasterViewType.CANDLE || masterIndicatorType == MasterIndicatorType.NONE
        val legendEntryArr = if (unShowLegend) {
            null
        } else {
            when (masterIndicatorType) {
                MasterIndicatorType.MA -> {
                    val ma2 = masterData?.ma
                    if (ma2 != null) {
                        getMaLegend(ma2, press)
                    } else {
                        null
                    }
                }
                MasterIndicatorType.BOLL -> {
                    val boll2 = masterData?.boll
                    if (boll2 != null) {
                        getBollLegend(boll2, press)
                    } else {
                        null
                    }
                }
                else -> {
                    null
                }
            }
        }
        if (legendEntryArr != null) {
            legend.isEnabled = true
            legend.setCustom(legendEntryArr)
        } else {
            legend.isEnabled = false
        }
        masterView.legendRenderer.computeLegend(mBaseKView.data)
    }

    /**
     * 切换指标
     */
    fun showIndicatorType(masterIndicatorType: MasterIndicatorType) {
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
                throw  IllegalArgumentException("MasterIndicatorType 类型错误")
            }
        }
        mMasterView.updateAll()
    }

    fun showIndicatorType(toNext: Boolean = false) {
        if (toNext) {
            mMasterIndicatorType = when (mMasterIndicatorType) {
                MasterIndicatorType.NONE -> {
                    MasterIndicatorType.MA
                }
                MasterIndicatorType.MA -> {
                    MasterIndicatorType.BOLL
                }
                MasterIndicatorType.BOLL -> {
                    MasterIndicatorType.NONE
                }
            }
        }
        val masterIndicatorType: MasterIndicatorType = mMasterIndicatorType
        showIndicatorType(masterIndicatorType)
    }

    /**
     * 实时修改最新数据的实时线
     *
     * @param limitLine
     * @param c
     */
    fun setLimit(limitLine: LimitLine, c: Float) {
        try {
            limitLine.label = XFormatUtil.globalFormat(c, mMasterView.mBaseInit.digit())
            mField.set(limitLine, c)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun refreshLimit(latestPrice: Float) {
        setLimit(mLimitLine, latestPrice)
    }
}