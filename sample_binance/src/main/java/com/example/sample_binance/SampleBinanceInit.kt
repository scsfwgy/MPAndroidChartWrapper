package com.example.sample_binance

import android.app.Application
import com.example.sample_binance.repository.ws.BinWsImpl

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 3:11 PM
 * 描 述 ：
 * ============================================================
 **/
object SampleBinanceInit {
    val TAG = SampleBinanceInit.javaClass.simpleName

    //不需要翻墙
    const val BASE_URL = "https://api.binancezh.com/"
    const val BASE_URL_WS = "wss://stream.binancezh.com:9443/ws"
    //const val BASE_URL_WS = "ws://echo.websocket.org"

    fun init(application: Application) {
        BinWsImpl.binWs.conn()
    }
}