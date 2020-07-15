package com.example.sample_binance.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.sample_binance.R
import com.example.sample_binance.model.api.ApiSymbol
import com.example.sample_binance.model.api.ApiSymbolWrapper
import com.example.sample_binance.repository.net.BinObserver
import com.example.sample_binance.repository.net.BinanceServiceWrapper
import com.example.sample_binance.ui.fragment.BinListFragment
import com.matt.libwrapper.ui.base.HandleExceptionActivity
import com.matt.libwrapper.utils.RxUtils
import com.matt.libwrapper.widget.ObserverWrapper
import com.matt.libwrapper.widget.simple.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.bin_activity_bin_list.*

class BinListActivity : HandleExceptionActivity() {
    companion object {
        fun goIntent(context: Context) {
            val intent = Intent(context, BinListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.bin_activity_bin_list)
        //loadKLine()
        loadExchangeInfo()
    }

    private fun loadExchangeInfo() {
        BinanceServiceWrapper.sBinanceService.exchangeInfo()
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : BinObserver<ApiSymbolWrapper>(this) {
                override fun onFinalSuccess(t: ApiSymbolWrapper) {
                    Log.d(TAG, t.symbols.toString())
                    renderTabLayout(t.symbols)
                }
            })
    }

    private fun renderTabLayout(symbols: List<ApiSymbol>) {
        val groupMap = symbols.groupBy {
            it.quoteAsset
        }.toSortedMap()
        val titles = groupMap.keys
        val fragments = groupMap.values.map {
            BinListFragment.newInstance(it as ArrayList<ApiSymbol>)
        }
        babl_vp_viewPager.adapter =
            SimpleFragmentPagerAdapter(supportFragmentManager, fragments, titles.toList())
        babl_stl_tabLayout.setViewPager(babl_vp_viewPager)
    }

    private fun loadKLine() {
        val params = HashMap<String, Any>()
        params["symbol"] = "BTCUSDT"
        params["interval"] = "1d"
        BinanceServiceWrapper.sBinanceService.klines(params)
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : BinObserver<Array<Array<Any>>>(this) {

                override fun onFinalSuccess(t: Array<Array<Any>>) {
                    Log.d(TAG, t.toString())
                }
            })
    }
}