package com.example.sample_binance.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ColorUtils.getColor
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sample_binance.R
import com.example.sample_binance.model.api.Api24Hr
import com.example.sample_binance.model.ws.WsSimpleTicker
import com.example.sample_binance.repository.memory.GlobalCache
import com.example.sample_binance.repository.ws.BinWsApi
import com.example.sample_binance.ui.activity.BinChartActivity
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.LazyLoadBaseFragment
import com.matt.libwrapper.utils.RxUtils
import com.matt.mpwrapper.utils.XFormatUtil
import com.matt.mpwrapper.view.MpWrapperConfig
import kotlinx.android.synthetic.main.bin_fragment_bin_list.view.*
import kotlinx.android.synthetic.main.bin_item_fragment_bin_list.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 5:26 PM
 * 描 述 ：
 * ============================================================
 **/
class BinListFragment : LazyLoadBaseFragment() {
    companion object {
        const val KEY_CURRENCY = "KEY_CURRENCY"

        @JvmStatic
        fun newInstance(currency: String) =
            BinListFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_CURRENCY, currency)
                }
            }
    }

    lateinit var mCurrency: String

    val mGreenUp by lazy {
        MpWrapperConfig.mConfig.greenUp
    }

    val mRedColor: Int by lazy {
        getColor(com.matt.mpwrapper.R.color.mp_basekview_red)
    }
    val mGreenColor: Int by lazy {
        getColor(com.matt.mpwrapper.R.color.mp_basekview_green)
    }
    val mBaseEqualColor: Int by lazy {
        getColor(com.matt.mpwrapper.R.color.mp_basekview_equal)
    }
    val mUpColor: Int by lazy {
        if (mGreenUp) mGreenColor else mRedColor
    }
    val mDownColor: Int by lazy {
        if (!mGreenUp) mGreenColor else mRedColor
    }

    val mBaseQuickAdapter by lazy {
        object :
            BaseQuickAdapter<Api24Hr, BaseViewHolder>(R.layout.bin_item_fragment_bin_list, null) {
            override fun convert(holder: BaseViewHolder, item: Api24Hr) {
                holder.itemView.run {
                    val symbolBySymbol = GlobalCache.getSymbolBySymbol(item.symbol)
                    val priceChangePercent = item.priceChangePercent
                    val lastPrice = item.lastPrice
                    bifbl_tv_symbol.text = item.symbol
                    bifbl_tv_price.text =
                        XFormatUtil.globalFormat(lastPrice, symbolBySymbol?.baseAssetPrecision ?: 2)
                    bifbl_tv_rate.text = XFormatUtil.globalFormat(
                        priceChangePercent,
                        2
                    ) + "%"
                    when {
                        priceChangePercent > 0 -> {
                            bifbl_tv_price.setTextColor(mUpColor)
                            bifbl_tv_rate.setTextColor(mUpColor)
                        }
                        priceChangePercent < 0 -> {
                            bifbl_tv_price.setTextColor(mDownColor)
                            bifbl_tv_rate.setTextColor(mDownColor)

                        }
                        else -> {
                            bifbl_tv_price.setTextColor(mBaseEqualColor)
                            bifbl_tv_rate.setTextColor(mBaseEqualColor)
                        }
                    }
                }
            }
        }
    }

    override fun getBundleExtras(bundle: Bundle?) {
        super.getBundleExtras(bundle)
        mCurrency = bundle?.getString(KEY_CURRENCY) ?: throw ParamsException("参数异常")
    }

    override fun layoutId(): Int {
        return R.layout.bin_fragment_bin_list
    }

    override fun eventBusEnable(): Boolean {
        return true
    }

    override fun safeInitAll(rootView: View) {
        initAdapter()
        initListener()
        loadData()
    }

    private fun initListener() {
        mBaseQuickAdapter.setOnItemClickListener { _, _, position ->
            val apiSymbol = mBaseQuickAdapter.data[position]
            BinChartActivity.goIntent(mContext, apiSymbol.symbol)
        }
        mRootView.bfbl_rv_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    subSimpleTickerBySymbols(visible = mCurrentVisible)
                }
            }
        })
    }

    private fun initAdapter() {
        mRootView.run {
            bfbl_rv_recycle.adapter = mBaseQuickAdapter
            bfbl_rv_recycle.layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun loadData() {
        val filter = GlobalCache.getSymbolMap().values.filter { it.quoteAsset == mCurrency }
        val map =
            filter.mapNotNull { GlobalCache.get24HrBySymbol(it.symbol) }
                .sortedByDescending { it.lastPrice }
        mBaseQuickAdapter.setNewInstance(map.toMutableList())

        val subscribe = RxUtils.timer(500).subscribe {
            subSimpleTickerBySymbols(mCurrentVisible)
        }
        addDisposable(subscribe)
    }

    override fun onVisable(visable: Boolean) {
        super.onVisable(visable)
        subSimpleTickerBySymbols(visable)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(wsSimpleTicker: WsSimpleTicker) {
        val data = mBaseQuickAdapter.data
        data.forEachIndexed loop@{ index, api24Hr ->
            if (api24Hr.symbol == wsSimpleTicker.s) {
                api24Hr.lastPrice = wsSimpleTicker.c
                api24Hr.priceChangePercent =
                    (wsSimpleTicker.o - wsSimpleTicker.c) / wsSimpleTicker.o * 100f
                mBaseQuickAdapter.notifyItemChanged(index)
                return@loop
            }
        }
    }

    private fun subSimpleTickerBySymbols(visible: Boolean, all: Boolean = false) {
        val linearLayoutManager = mRootView.bfbl_rv_recycle.layoutManager as LinearLayoutManager
        val first = linearLayoutManager.findFirstVisibleItemPosition()
        val last = linearLayoutManager.findLastVisibleItemPosition()
        val filterIndexed =
            if (all) {
                mBaseQuickAdapter.data
            } else {
                mBaseQuickAdapter.data.filterIndexed { index, _ -> index in first until last }
            }.map { it.symbol }
        if (filterIndexed.isEmpty()) return
        BinWsApi.simpleTicker(filterIndexed.toTypedArray(), visible)
    }

    override fun onCatchDestroy() {
        super.onCatchDestroy()
        subSimpleTickerBySymbols(false, true)
    }
}