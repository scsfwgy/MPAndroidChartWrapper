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
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.HandleExceptionActivity
import com.matt.libwrapper.utils.RxUtils
import com.matt.libwrapper.widget.ObserverWrapper
import com.matt.libwrapper.widget.simple.SimpleFragmentPagerAdapter
import kotlinx.android.synthetic.main.bin_activity_bin_list.*

class BinChartActivity : HandleExceptionActivity() {
    companion object {
        const val KEY_SYMBOL = "KEY_SYMBOL"

        fun goIntent(context: Context, symbol: String) {
            val intent = Intent(context, BinChartActivity::class.java)
            intent.putExtra(KEY_SYMBOL, symbol)
            context.startActivity(intent)
        }
    }

    lateinit var mSymbol: String

    override fun getIntentExtras(intent: Intent) {
        super.getIntentExtras(intent)
        mSymbol = intent.getStringExtra(KEY_SYMBOL) ?: throw ParamsException("KEY_SYMBOL参数缺失")
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.bin_activity_bin_chart)
        loadKLine()
    }

    private fun loadKLine() {
        val params = HashMap<String, Any>()
        params["symbol"] = mSymbol
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