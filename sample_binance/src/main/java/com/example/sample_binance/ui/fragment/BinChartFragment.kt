package com.example.sample_binance.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.sample_binance.R
import com.example.sample_binance.model.kview.BinKType
import com.example.sample_binance.repository.net.BinObserver
import com.example.sample_binance.repository.net.BinanceServiceWrapper
import com.example.sample_binance.ui.activity.BinChartActivity
import com.matt.libwrapper.ui.base.LazyLoadBaseFragment
import com.matt.libwrapper.utils.RxUtils
import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.KView
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MasterViewType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import kotlinx.android.synthetic.main.bin_fragment_chart.view.*

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/16 11:44
 * 描 述 ：
 * ============================================================
 */
class BinChartFragment : LazyLoadBaseFragment() {
    companion object {
        const val KEY_KTYPE = "KEY_KTYPE"
        fun newInstance(kType: BinKType, symbol: String): BinChartFragment {
            return BinChartFragment().apply {
                arguments = Bundle().apply {
                    putString(BinChartActivity.KEY_SYMBOL, symbol)
                    putSerializable(KEY_KTYPE, kType)
                }
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.bin_fragment_chart
    }

    lateinit var kType: BinKType
    lateinit var mSymbol: String

    override fun getBundleExtras(bundle: Bundle?) {
        super.getBundleExtras(bundle)
        val serializable =
            bundle?.getSerializable(KEY_KTYPE)
                ?: throw IllegalArgumentException("KEY_KTYPE 不允许为null")
        kType = serializable as BinKType
        mSymbol = bundle.getString(BinChartActivity.KEY_SYMBOL)
            ?: throw IllegalArgumentException("KEY_SYMBOL 不允许为null")
    }


    override fun safeInitAll(rootView: View) {
        Log.d(TAG, "symbol:${mSymbol}")
        initView()
        loadData()
    }

    private fun initView() {
        mRootView.run {
            bfc_kv_kview.initKView(
                4,
                if (kType == BinKType.K_TIMESHARE) MasterViewType.TIMESHARING else
                    MasterViewType.CANDLE
            )
        }
    }

    private fun loadData() {
        loadKLine()
    }

    private fun loadKLine() {
        val params = HashMap<String, Any>()
        params["symbol"] = mSymbol
        params["interval"] = kType.apiKey
        getKView().onLoading("正在加载中...")
        BinanceServiceWrapper.sBinanceService.klines(params)
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : BinObserver<Array<Array<Any>>>(this) {
                override fun onFinalSuccess(t: Array<Array<Any>>) {
                    Log.d(TAG, t.toString())
                    val priceList = ArrayList<Price>(t.size)
                    val volList = ArrayList<Float>(t.size)
                    t.forEach {
                        val price =
                            Price(
                                (it[0] as Double).toLong(),//开盘时间
                                (it[1] as String).toFloat(),//开盘价
                                (it[2] as String).toFloat(),//最高价
                                (it[3] as String).toFloat(),//最低价
                                (it[4] as String).toFloat()//收盘价
                            )
                        priceList.add(price)
                        volList.add((it[5] as String).toFloat())
                    }
                    renderChart(priceList, volList)
                }

                override fun onCatchError(e: Throwable) {
                    super.onCatchError(e)
                    getKView().onLoadingFail("加载失败，请稍后再试...")
                }
            })
    }

    private fun renderChart(
        it: List<Price>,
        volList: ArrayList<Float>
    ) {
        getKView().reLoadData(it, volList)
    }

    fun getKView(): KView {
        return mRootView.bfc_kv_kview
    }

    fun updateBinKType(currBinKType: BinKType) {
        kType = currBinKType
        loadKLine()
    }

    fun updateMasterIndicatorType(masterIndicatorType: MasterIndicatorType) {
        getKView().getMasterView().mMasterViewDelegate.showIndicatorType(masterIndicatorType)
    }

    fun updateMinorIndicatorType(minorIndicatorType: MinorIndicatorType) {
        getKView().getMinorView().mMinorViewDelegate.showIndicatorType(minorIndicatorType)
    }
}