package com.example.sample_binance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ColorUtils.getColor
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sample_binance.R
import com.example.sample_binance.model.api.Api24Hr
import com.example.sample_binance.repository.memory.GlobalCache
import com.example.sample_binance.ui.activity.BinChartActivity
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.LazyLoadBaseFragment
import com.matt.mpwrapper.utils.XFormatUtil
import com.matt.mpwrapper.view.MpWrapperConfig
import kotlinx.android.synthetic.main.bin_fragment_bin_list.view.*
import kotlinx.android.synthetic.main.bin_item_fragment_bin_list.view.*

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

    override fun safeInitAll(rootView: View) {
        initAdapter()
        initlistener()
        loadData()
    }

    private fun initlistener() {
        mBaseQuickAdapter.setOnItemClickListener { _, _, position ->
            val apiSymbol = mBaseQuickAdapter.data[position]
            BinChartActivity.goIntent(mContext, apiSymbol.symbol)
        }
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
    }
}