package com.example.sample_binance.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sample_binance.R
import com.example.sample_binance.model.kview.BinKType
import com.example.sample_binance.repository.sp.BinSpHelper
import com.example.sample_binance.ui.fragment.BinChartFragment
import com.example.sample_binance.ui.pop.IndicatorPop
import com.example.sample_binance.ui.pop.KViewTypePop
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.template.Template
import com.matt.libwrapper.ui.base.template.TemplateBarActivity
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import kotlinx.android.synthetic.main.bin_activity_bin_chart.*
import razerdp.basepopup.BasePopupWindow


class BinChartActivity : TemplateBarActivity() {
    companion object {
        const val KEY_SYMBOL = "KEY_SYMBOL"

        fun goIntent(context: Context, symbol: String) {
            val intent = Intent(context, BinChartActivity::class.java)
            intent.putExtra(KEY_SYMBOL, symbol)
            context.startActivity(intent)
        }
    }

    lateinit var mSymbol: String

    var mCurrBinKType = BinSpHelper.getBinKType()

    val mKViewTypePop by lazy {
        KViewTypePop(mContext, mCurrBinKType, object : KViewTypePop.KTypeListener {
            override fun onClick(binKType: BinKType) {
                mCurrBinKType = binKType
                babc_tv_kType.text = mCurrBinKType.label
                chartFragment.updateBinKType(mCurrBinKType)
            }
        })
    }

    val mIndicatorPop by lazy {
        IndicatorPop(mContext, object : IndicatorPop.MainClickListener {
            override fun onClick(masterIndicatorType: MasterIndicatorType) {
                chartFragment.updateMasterIndicatorType(masterIndicatorType)
            }

        }, object : IndicatorPop.MinorClickListener {
            override fun onClick(minorIndicatorType: MinorIndicatorType) {
                chartFragment.updateMinorIndicatorType(minorIndicatorType)
            }
        })
    }

    override fun getIntentExtras(intent: Intent) {
        super.getIntentExtras(intent)
        mSymbol = intent.getStringExtra(KEY_SYMBOL) ?: throw ParamsException("KEY_SYMBOL参数缺失")
    }

    val chartFragment by lazy {
        BinChartFragment.newInstance(mCurrBinKType, mSymbol)
    }

    override fun templateType(): Int {
        return Template.TEMPLATETYPE_DEFVIEW
    }

    override fun addChildrenView(): Any {
        return R.layout.bin_activity_bin_chart
    }

    override fun renderTitle(): Any {
        return mSymbol
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        super.onCatchCreate(savedInstanceState)
        iniView()
        initListener()
    }

    private fun iniView() {
        initChart()
    }

    private fun initListener() {
        val kViewTypePop = mKViewTypePop
        kViewTypePop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                val drawable = getDrawable(R.drawable.ic_triangle_down)
                val helper = babc_tv_kType.helper
                helper.iconNormal = drawable
            }
        }
        kViewTypePop.setOnPopupWindowShowListener {
            val helper = babc_tv_kType.helper
            helper.iconNormal = getDrawable(R.drawable.ic_triangle_up)
        }
        babc_tv_kType.setOnClickListener {
            kViewTypePop.showPopupWindow(babc_tv_kType)
        }
        babc_tv_indicator.setOnClickListener {
            mIndicatorPop.showPopupWindow(babc_tv_indicator)
        }
    }

    private fun initChart() {
        addFragment(
            chartFragment,
            R.id.babc_fl_chartContainer
        )
    }
}