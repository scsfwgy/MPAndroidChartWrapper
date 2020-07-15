package com.matt.demo.ui.activity

import android.os.Bundle
import com.example.sample_binance.ui.activity.BinListActivity
import com.matt.demo.R
import com.matt.libwrapper.ui.base.HandleExceptionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : HandleExceptionActivity() {

    private fun initListener() {
        am_b_kview.setOnClickListener {
            SymbolListActivity.goIntent(mContext)
        }
        am_b_bin.setOnClickListener {
            BinListActivity.goIntent(mContext)
        }
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        initListener()

    }
}
