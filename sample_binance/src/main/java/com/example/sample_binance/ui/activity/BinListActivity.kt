package com.example.sample_binance.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sample_binance.R
import com.example.sample_binance.model.api.Api24Hr
import com.example.sample_binance.model.api.ApiSymbol
import com.example.sample_binance.model.api.ApiSymbolWrapper
import com.example.sample_binance.repository.memory.GlobalCache
import com.example.sample_binance.repository.net.BinObserver
import com.example.sample_binance.repository.net.BinanceServiceWrapper
import com.example.sample_binance.ui.fragment.BinListFragment
import com.matt.libwrapper.ui.base.template.Template
import com.matt.libwrapper.ui.base.template.TemplateBarActivity
import com.matt.libwrapper.utils.RxUtils
import com.matt.libwrapper.widget.simple.SimpleCatchObserver
import com.matt.libwrapper.widget.simple.SimpleFragmentPagerAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.bin_activity_bin_list.*

class BinListActivity : TemplateBarActivity() {
    companion object {
        fun goIntent(context: Context) {
            val intent = Intent(context, BinListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun templateType(): Int {
        return Template.TEMPLATETYPE_DEFVIEW
    }

    override fun addChildrenView(): Any {
        return R.layout.bin_activity_bin_list
    }

    override fun renderTitle(): Any {
        return "币安产品列表"
    }

    override fun onCatchCreate(savedInstanceState: Bundle?) {
        super.onCatchCreate(savedInstanceState)
        //loadKLine()
        loadExchangeInfo()
    }

    private fun loadExchangeInfo() {
        val exchangeInfo = BinanceServiceWrapper.sBinanceService.exchangeInfo()
        val _24hr = BinanceServiceWrapper.sBinanceService._24hr()
        Observable.merge(exchangeInfo, _24hr)
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : BinObserver<Any>(this) {
                override fun onFinalSuccess(t: Any) {
                    if (t is ApiSymbolWrapper) {
                        GlobalCache.updateSymbol(t.symbols)
                    } else if (t is List<*>) {
                        GlobalCache.update24Hr(t as List<Api24Hr>)
                    }
                }

                override fun onCatchComplete() {
                    super.onCatchComplete()
                    renderTabLayout(GlobalCache.getSymbolMap().values.toList())
                }
            })
    }

    private fun renderTabLayout(symbols: List<ApiSymbol>) {
        val groupMap = symbols.groupBy {
            it.quoteAsset
        }.toSortedMap(Comparator<String> { item1, _ ->
            when (item1) {
                "USDT" -> {
                    -1
                }
                "BTC" -> {
                    -1
                }
                else -> {
                    1
                }
            }
        })
        val titles = groupMap.keys
        val fragments = titles.map {
            BinListFragment.newInstance(it)
        }
        babl_vp_viewPager.adapter =
            SimpleFragmentPagerAdapter(supportFragmentManager, fragments, titles.toList())
        babl_stl_tabLayout.setViewPager(babl_vp_viewPager)
    }

}