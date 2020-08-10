package com.example.sample_binance.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SpanUtils
import com.example.sample_binance.R
import com.example.sample_binance.model.kview.BinKType
import com.example.sample_binance.model.ws.WsSimpleTicker
import com.example.sample_binance.repository.memory.GlobalCache
import com.example.sample_binance.repository.sp.BinSpHelper
import com.example.sample_binance.repository.ws.BinWsApi
import com.example.sample_binance.ui.fragment.BinChartFragment
import com.example.sample_binance.ui.pop.IndicatorPop
import com.example.sample_binance.ui.pop.KViewTypePop
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.template.Template
import com.matt.libwrapper.ui.base.template.TemplateBarActivity
import com.matt.mpwrapper.utils.XFormatUtil
import com.matt.mpwrapper.view.MpWrapperConfig
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import kotlinx.android.synthetic.main.bin_activity_bin_chart.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

    override fun eventBusEnable(): Boolean {
        return true
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

    val mGreenUp by lazy {
        MpWrapperConfig.mConfig.greenUp
    }

    val mRedColor: Int by lazy {
        ColorUtils.getColor(com.matt.mpwrapper.R.color.mp_basekview_red)
    }
    val mGreenColor: Int by lazy {
        ColorUtils.getColor(com.matt.mpwrapper.R.color.mp_basekview_green)
    }
    val mBaseEqualColor: Int by lazy {
        ColorUtils.getColor(com.matt.mpwrapper.R.color.mp_basekview_equal)
    }
    val mUpColor: Int by lazy {
        if (mGreenUp) mGreenColor else mRedColor
    }
    val mDownColor: Int by lazy {
        if (!mGreenUp) mGreenColor else mRedColor
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        super.onCatchCreate(savedInstanceState)
        iniView()
        initListener()
        loadData()
    }

    override fun onCatchDestroy() {
        super.onCatchDestroy()
        sub(false)
    }

    private fun loadData() {
        sub(true)
    }

    private fun iniView() {
        initChart()
    }

    private fun initListener() {
        babc_tv_kType.text = mCurrBinKType.label
        val kViewTypePop = mKViewTypePop
        kViewTypePop.onDismissListener = object : BasePopupWindow.OnDismissListener() {
            override fun onDismiss() {
                val drawable = getDrawable(R.drawable.ic_triangle_down)
                val helper = babc_tv_kType.helper
                helper.iconNormalRight = drawable
            }
        }
        kViewTypePop.setOnPopupWindowShowListener {
            val helper = babc_tv_kType.helper
            helper.iconNormalRight = getDrawable(R.drawable.ic_triangle_up)
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

    fun sub(sub: Boolean) {
        val kline = BinWsApi.simpleTicker(arrayOf(mSymbol), sub)
        if (sub && !kline) {
            showToast("k线推送数据订阅失败，请重试")
            return
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(wsSimpleTicker: WsSimpleTicker) {
        chartFragment.onEvent(wsSimpleTicker)

        val open = wsSimpleTicker.o
        val current = wsSimpleTicker.c
        val rate = (current - open) / open * 100
        val symbolBySymbol = GlobalCache.getSymbolBySymbol(mSymbol)
        val currentFormat =
            XFormatUtil.globalFormat(current, symbolBySymbol?.priceDigit ?: 2, false)
        val rateFormat = XFormatUtil.globalFormat(rate, 2, false) + "%"
        val color = when {
            rate > 0 -> {
                mUpColor
            }
            rate < 0 -> {
                mDownColor
            }
            else -> {
                mRedColor
            }
        }
        val titleColor = getColor2(R.color.wrapper_appBarTitle_design)
        SpanUtils.with(getLeftTxtView()).append("$mSymbol($currentFormat ")
            .setForegroundColor(titleColor)
            .append("$rateFormat)")
            .setForegroundColor(color)
            .create()
    }
}