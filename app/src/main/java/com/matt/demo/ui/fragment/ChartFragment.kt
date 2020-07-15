package com.matt.demo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.matt.demo.R
import com.matt.demo.net.base.SimpleTObserver
import com.matt.demo.ui.activity.ChartActivity
import com.matt.demo.vm.ChartViewModel
import com.matt.libwrapper.ui.base.LazyLoadBaseFragment
import com.matt.libwrapper.utils.RxUtils
import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.type.KType
import com.matt.mpwrapper.view.type.MasterViewType
import kotlinx.android.synthetic.main.fragment_chart.view.*

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/16 11:44
 * 描 述 ：
 * ============================================================
 */
class ChartFragment : LazyLoadBaseFragment() {
    companion object {
        const val KEY_KTYPE = "KEY_KTYPE"
        fun newInstance(kType: KType, symbol: String): ChartFragment {
            return ChartFragment().apply {
                arguments = Bundle().apply {
                    putString(ChartActivity.KEY_SYMBOL, symbol)
                    putSerializable(KEY_KTYPE, kType)
                }
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_chart
    }

    val mChartViewMode by lazy {
        getVMByFragment(ChartViewModel::class.java)
    }

    lateinit var kType: KType

    override fun getBundleExtras(bundle: Bundle?) {
        super.getBundleExtras(bundle)
        val serializable =
            bundle?.getSerializable(KEY_KTYPE)
                ?: throw IllegalArgumentException("KEY_KTYPE 不允许为null")
        kType = serializable as KType
        val symbol = bundle.getString(ChartActivity.KEY_SYMBOL)
            ?: throw IllegalArgumentException("KEY_SYMBOL 不允许为null")
        mChartViewMode.mSymbol = symbol
    }


    override fun safeInitAll(rootView: View) {
        Log.d(TAG, "symbol:${mChartViewMode.mSymbol}")
        initView()
        loadData()
    }

    private fun initView() {
        mRootView.run {
            fc_kv_kview.initKView(
                4,
                if (kType == KType.K_TIMESHARE) MasterViewType.TIMESHARING else
                    MasterViewType.CANDLE
            )
        }
    }

    private fun loadData() {
        mChartViewMode.getQuoteListByNet(mChartViewMode.mSymbol, kType)
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : SimpleTObserver<List<Price>>(mBaseActivity) {
                override fun onFinalSuccess(data: List<Price>) {
                    Log.d(TAG, data.toString())
                    renderChart(data)
                }
            })
    }

    private fun renderChart(it: List<Price>) {
        mRootView.run {
            fc_kv_kview.reLoadData(it)
        }
    }
}