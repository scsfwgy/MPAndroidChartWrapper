package com.matt.sample_zm.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.matt.sample_zm.ui.fragment.ChartContainerFragment
import com.matt.sample_zm.vm.ChartViewModel
import com.matt.libwrapper.ui.base.HandleExceptionActivity
import com.matt.sample_zm.R

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

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.zm_activity_chart)
        initView()
        initListener()
    }

    override fun getIntentExtras(intent: Intent) {
        super.getIntentExtras(intent)
        val symbol =
            intent.getStringExtra(KEY_SYMBOL) ?: throw IllegalArgumentException("mSymbol不允许为null")
        mCharViewMode.mSymbol = symbol
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
