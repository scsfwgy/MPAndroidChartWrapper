package com.example.sample_binance.ui.pop

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.sample_binance.R
import com.example.sample_binance.repository.sp.BinSpHelper
import com.matt.libwrapper.ui.base.BasePopWindow
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import kotlinx.android.synthetic.main.bin_pop_indicator.view.*

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/7/19 11:13 AM
 * 描 述 ：
 *
 **/
class IndicatorPop(
    context: Context,
    val mainClickListener: MainClickListener,
    val minorClickListener: MinorClickListener
) :
    BasePopWindow(context) {

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.bin_pop_indicator)
    }

    val mainTextViewList by lazy {
        contentView.run {
            arrayOf(bpi_tv_ma, bpi_tv_ema, bpi_tv_boll)
        }
    }

    val minorTextViewList by lazy {
        contentView.run {
            arrayOf(bpi_tv_macd, bpi_tv_kdj, bpi_tv_rsi, bpi_tv_wr)
        }
    }

    init {
        updateViews()
        initListener()
    }

    private fun updateViews() {
        updateMainViews()
        updateMinorViews()
    }

    private fun updateMinorViews() {
        minorTextViewList.forEach {
            it.setTextColor(getUnSelectColor())
        }
        getMinorEye().setImageDrawable(getUnSelectEye())
        when (BinSpHelper.getMinorIndicator()) {
            MinorIndicatorType.MACD -> {
                minorTextViewList[0].setTextColor(getSelectColor())
            }
            MinorIndicatorType.KDJ -> {
                minorTextViewList[1].setTextColor(getSelectColor())
            }
            MinorIndicatorType.RSI -> {
                minorTextViewList[2].setTextColor(getSelectColor())
            }
            else -> {
                Log.d(TAG, "暂时不处理")
            }
        }
    }

    private fun updateMainViews() {
        mainTextViewList.forEach {
            it.setTextColor(getUnSelectColor())
        }
        getMainEye().setImageDrawable(getUnSelectEye())
        when (BinSpHelper.getMainIndicator()) {
            MasterIndicatorType.NONE -> {
                getMainEye().setImageDrawable(getSelectEye())
            }
            MasterIndicatorType.MA -> {
                mainTextViewList[0].setTextColor(getSelectColor())
            }
            MasterIndicatorType.BOLL -> {
                mainTextViewList[2].setTextColor(getSelectColor())
            }
            else -> {
                Log.d(TAG, "暂时不处理")
            }
        }
    }

    private fun initListener() {
        contentView.run {
            bpi_tv_ma.setOnClickListener {
                dismiss()
                BinSpHelper.updateMainIndicator(MasterIndicatorType.MA)
                updateMainViews()
                mainClickListener.onClick(MasterIndicatorType.MA)
            }
            bpi_tv_ema.setOnClickListener {
                //todo
            }
            bpi_tv_boll.setOnClickListener {
                dismiss()
                BinSpHelper.updateMainIndicator(MasterIndicatorType.BOLL)
                updateMainViews()
                mainClickListener.onClick(MasterIndicatorType.BOLL)
            }
            bpi_tv_maineye.setOnClickListener {
                dismiss()
                BinSpHelper.updateMainIndicator(MasterIndicatorType.NONE)
                updateMainViews()
                mainClickListener.onClick(MasterIndicatorType.NONE)
            }


            bpi_tv_macd.setOnClickListener {
                dismiss()
                BinSpHelper.updateMinorIndicator(MinorIndicatorType.MACD)
                updateMinorViews()
                minorClickListener.onClick(MinorIndicatorType.MACD)
            }
            bpi_tv_kdj.setOnClickListener {
                dismiss()
                BinSpHelper.updateMinorIndicator(MinorIndicatorType.KDJ)
                updateMinorViews()
                minorClickListener.onClick(MinorIndicatorType.KDJ)
            }
            bpi_tv_rsi.setOnClickListener {
                dismiss()
                BinSpHelper.updateMinorIndicator(MinorIndicatorType.RSI)
                updateMinorViews()
                minorClickListener.onClick(MinorIndicatorType.RSI)
            }
            bpi_tv_wr.setOnClickListener {
                dismiss()
                //todo
            }
            bpi_tv_stubeye.setOnClickListener {
                dismiss()
                //todo
            }
        }
    }

    fun getMainEye(): ImageView {
        return contentView.bpi_tv_maineye
    }

    fun getMinorEye(): ImageView {
        return contentView.bpi_tv_stubeye
    }

    fun getSelectColor(): Int {
        return getColor2(R.color.bin_global_yellow)
    }

    fun getUnSelectColor(): Int {
        return getColor2(R.color.bin_txt_first)
    }

    fun getSelectEye(): Drawable {
        return getDrawable2(R.drawable.ic_markets_reset_disable)
    }

    fun getUnSelectEye(): Drawable {
        return getDrawable2(R.drawable.ic_markets_reset_active)
    }

    interface MainClickListener {
        fun onClick(masterIndicatorType: MasterIndicatorType)
    }

    interface MinorClickListener {
        fun onClick(minorIndicatorType: MinorIndicatorType)
    }
}