package com.wgyscsf.demo.ui.activity

import android.os.Bundle
import com.wgyscsf.demo.R
import com.wgyscsf.demo.ui.base.HandleExceptionActivity
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
