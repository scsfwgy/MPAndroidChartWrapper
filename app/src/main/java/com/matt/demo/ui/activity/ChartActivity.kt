package com.matt.demo.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.matt.demo.R
import com.matt.sample_base.ui.base.HandleExceptionActivity
import com.matt.demo.ui.fragment.ChartContainerFragment
import com.matt.demo.vm.ChartViewModel

class ChartActivity : HandleExceptionActivity() {
    companion object {
        const val KEY_SYMBOL = "KEY_SYMBOL"
        fun goIntent(context: Context, symbol: String) {
            val intent = Intent(context, ChartActivity::class.java)
            intent.putExtra(KEY_SYMBOL, symbol)
            context.startActivity(intent)
        }
    }


    val mCharViewMode by lazy {
        getVM(ChartViewModel::class.java)
    }

    override fun getIntentExtras(intent: Intent) {
        super.getIntentExtras(intent)
        val symbol =
            intent.getStringExtra(KEY_SYMBOL) ?: throw IllegalArgumentException("mSymbol不允许为null")
        mCharViewMode.mSymbol = symbol
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_chart
    }


    override fun safeInitAll(savedInstanceState: Bundle?) {
        initView()
        initListener()
    }

    private fun initView() {
        addFragment(
            ChartContainerFragment.newInstance(),
            R.id.ac_fl_chartContainer
        )
    }

    private fun initListener() {

    }
}
