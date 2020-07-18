package com.example.sample_binance.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sample_binance.R
import com.example.sample_binance.ui.fragment.BinChartFragment
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.HandleExceptionActivity
import com.matt.mpwrapper.view.type.BinKType

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
        initChart()
    }

    private fun initChart() {
        addFragment(
            BinChartFragment.newInstance(BinKType.K_1D, mSymbol),
            R.id.babc_fl_chartContainer
        )
    }
}