package com.matt.demo

import android.os.Bundle
import android.view.View
import com.example.sample_binance.ui.activity.BinListActivity
import com.matt.libwrapper.ui.base.HandleExceptionActivity
import com.matt.libwrapper.ui.base.template.Template
import com.matt.libwrapper.ui.base.template.TemplateBarActivity
import com.matt.sample_zm.ui.activity.SymbolListActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : TemplateBarActivity() {

    private fun initListener() {
        am_b_kview.setOnClickListener {
            SymbolListActivity.goIntent(mContext)
        }
        am_b_bin.setOnClickListener {
            BinListActivity.goIntent(mContext)
        }
    }

    override fun templateType(): Int {
        return Template.TEMPLATETYPE_DEFVIEW
    }

    override fun addChildrenView(): Any {
        return R.layout.activity_main
    }

    override fun renderTitle(): Any {
        return "MPAndroidChartWrapper Demo"
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        super.onCatchCreate(savedInstanceState)
        initView()
        initListener()

    }

    private fun initView() {
        getBarContainer().setBackgroundColor(getColor2(R.color.wrapper_theme_design))
        getLeftImageView().visibility = View.INVISIBLE
    }
}
