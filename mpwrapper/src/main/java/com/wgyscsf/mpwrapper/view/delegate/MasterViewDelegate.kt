package com.wgyscsf.mpwrapper.view.delegate

import android.graphics.Paint
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.wgyscsf.mpwrapper.view.MasterView
import com.wgyscsf.mpwrapper.view.base.BaseLineDataSet
import com.wgyscsf.mpwrapper.view.enum.BollType
import com.wgyscsf.mpwrapper.view.enum.MaType
import com.wgyscsf.mpwrapper.view.enum.MasterViewType

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 15:14
 * 描 述 ：
 * ============================================================
 */
class MasterViewDelegate(masterView: MasterView) : BaseKViewDelegate(masterView) {
    val mMasterView by lazy {
        masterView
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
        timeSharingDataSet.color = mMasterView.mMasterTimeSharingColor
        timeSharingDataSet.lineWidth = 1f
        timeSharingDataSet.fillDrawable = mMasterView.mMasterTimeSharingFillDrawable
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
        ma5.color = mMasterView.mMasterViewMa5Color
        val ma10 = BaseLineDataSet(mMa10EntryList, MaType.MA10.toString())
        ma10.color = mMasterView.mMasterViewMa10Color
        val ma20 = BaseLineDataSet(mMa20EntryList, MaType.MA20.toString())
        ma20.color = mMasterView.mMasterViewMa20Color
        arrayOf(ma5, ma10, ma20)
    }

    val mBollLineDataSetArr by lazy {
        val bollup = BaseLineDataSet(mBollUpEntryList, BollType.UP.toString())
        bollup.color = mMasterView.mMasterViewBollUpColor

        val bollmd = BaseLineDataSet(mBollMdEntryList, BollType.MD.toString())
        bollmd.color = mMasterView.mMasterViewBollMdColor

        val bollDn = BaseLineDataSet(mBollDnEntryList, BollType.DN.toString())
        bollDn.color = mMasterView.mMasterViewBollDnColor

        arrayOf(bollup, bollmd, bollDn)
    }

    val mLimitLine by lazy {
        val limitLine = LimitLine(0.0f, "--")
        limitLine.lineWidth = 0.5f
        limitLine.lineColor = mBaseKView.mBaseLimitColor
        limitLine.enableDashedLine(8f, 8f, 8f)
        limitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        mMasterView.axisRight.addLimitLine(limitLine)
        limitLine
    }

    fun setMaDataSetArrVisible(visible: Boolean) {
        setDataSetArrVisible(mMaLineDataSetArr, visible)
    }

    fun setBollDataSetArrVisible(visible: Boolean) {
        setDataSetArrVisible(mBollLineDataSetArr, visible)
    }

    private fun setDataSetArrVisible(arr: Array<out LineDataSet>, visible: Boolean) {
        val lineData = mLineData
        arr.forEach {
            if (visible) {
                if (!lineData.contains(it)) lineData.addDataSet(it)
            } else {
                if (lineData.contains(it)) lineData.removeDataSet(it)
            }
        }
    }

}