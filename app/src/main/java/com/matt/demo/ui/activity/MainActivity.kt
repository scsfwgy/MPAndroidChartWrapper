package com.matt.demo.ui.activity

import android.os.Bundle
import com.matt.demo.R
import com.matt.sample_base.ui.base.HandleExceptionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : HandleExceptionActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun safeInitAll(savedInstanceState: Bundle?) {
        initListener()
    }

    private fun initListener() {
        am_b_kview.setOnClickListener {
            SymbolListActivity.goIntent(mContext)
        }
    }
}
