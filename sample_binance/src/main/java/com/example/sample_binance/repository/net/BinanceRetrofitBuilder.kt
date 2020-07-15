package com.example.sample_binance.repository.net

import com.example.sample_binance.SampleBinanceInit
import com.matt.libwrapper.repository.net.AbstractRetrofitBuilder
import com.matt.libwrapper.repository.net.DefOkHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 3:08 PM
 * 描 述 ：
 * ============================================================
 **/
class BinanceRetrofitBuilder : AbstractRetrofitBuilder() {

    override fun apiBaseUrl(): String {
        return SampleBinanceInit.BASE_URL
    }

    companion object {
        val mRetrofit: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            BinanceRetrofitBuilder().mRetrofit
        }
    }
}
