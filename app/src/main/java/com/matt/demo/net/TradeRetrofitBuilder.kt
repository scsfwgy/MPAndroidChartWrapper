package com.matt.demo.net

import com.matt.demo.config.GlobalConfig
import com.matt.libwrapper.repository.net.AbstractRetrofitBuilder
import retrofit2.Retrofit


/**
 * ============================================================
 * 作 者 :    freer-2
 * 更新时间 ：2019/11/13 13:41
 * 描 述 ：一个Retrofit实例
 * ============================================================
 */
class TradeRetrofitBuilder : AbstractRetrofitBuilder() {

    override fun apiBaseUrl(): String {
        return GlobalConfig.getTradeUrl()
    }

    companion object {
        val mRetrofit: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            TradeRetrofitBuilder().mRetrofit
        }
    }


}
