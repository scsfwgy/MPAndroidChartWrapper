package com.example.sample_binance.repository.net

import com.example.sample_binance.repository.net.service.BinanceService

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 3:53 PM
 * 描 述 ：
 * ============================================================
 **/
object BinanceServiceWrapper {

    val sBinanceService by lazy {
        BinanceRetrofitBuilder.mRetrofit.create(BinanceService::class.java)
    }
}