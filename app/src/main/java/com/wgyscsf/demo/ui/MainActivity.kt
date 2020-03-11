package com.wgyscsf.demo.ui

import android.os.Bundle
import com.wgyscsf.demo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener() {
        am_b_kview.setOnClickListener {
            SymbolListActivity.goIntent(mContext)
        }
    }
}
