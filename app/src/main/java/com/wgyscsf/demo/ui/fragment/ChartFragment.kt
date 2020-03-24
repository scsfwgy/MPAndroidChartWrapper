package com.wgyscsf.demo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.wgyscsf.demo.R
import com.wgyscsf.demo.net.base.SimpleTObserver
import com.wgyscsf.demo.ui.base.LazyLoadBaseFragment
import com.wgyscsf.demo.utils.RxUtils
import com.wgyscsf.demo.vm.ChartViewModel
import com.wgyscsf.mpwrapper.bean.Price
import com.wgyscsf.mpwrapper.view.type.KType
import kotlinx.android.synthetic.main.fragment_chart.view.*

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/16 11:44
 * 描 述 ：
 * ============================================================
 */
class ChartFragment : LazyLoadBaseFragment() {
    companion object {
        const val KEY_KTYPE = "KEY_KTYPE"
        fun newInstance(kType: KType): ChartFragment {
            return ChartFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_KTYPE, kType)
                }
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_chart
    }

    val mChartViewMode by lazy {
        getVM(ChartViewModel::class.java)
    }

    lateinit var kType: KType

    override fun getBundleExtras(bundle: Bundle?) {
        super.getBundleExtras(bundle)
        val serializable =
            bundle?.getSerializable(KEY_KTYPE)
                ?: throw IllegalArgumentException("KEY_KTYPE 不允许为null")
        kType = serializable as KType
    }


    override fun safeInitAll(rootView: View) {
        Log.d(TAG, "symbol:${mChartViewMode.mSymbol}")
        loadData()
    }

    private fun loadData() {
        mChartViewMode.getQuoteListByNet(mChartViewMode.mSymbol, kType)
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : SimpleTObserver<List<Price>>(mBaseActivity) {
                override fun onSuccess(it: List<Price>) {
                    Log.d(TAG, it.toString())
                    renderChart(it)
                }
            })
    }

    private fun renderChart(it: List<Price>) {
        mRootView.run {
            fc_kv_kview.reLoadData(it)
        }
    }
}