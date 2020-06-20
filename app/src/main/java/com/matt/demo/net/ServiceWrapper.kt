package com.matt.demo.net

import com.bitconch.lib_wrapper.net.MarketRetrofitBuilder
import com.bitconch.lib_wrapper.net.TradeRetrofitBuilder


/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2019/06/20 16:28
 * 描 述 ：
 * ============================================================
 */
object ServiceWrapper {
    val TRADE_SERVICE: TradeService by lazy {
        TradeRetrofitBuilder.mRetrofit.create(TradeService::class.java)
    }

    val MARKET_SERVICE: MarketService by lazy {
        MarketRetrofitBuilder.mRetrofit.create(MarketService::class.java)
    }
}
