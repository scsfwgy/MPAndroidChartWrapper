package com.example.sample_binance.repository.ws

import com.example.sample_binance.model.ws.WsBase

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/4 11:07 AM
 * 描 述 ：
 * ============================================================
 **/
object BinWsApi {

    fun kline(symbol: String, interval: String, sub: Boolean): Boolean {
        val api = "${symbol.toLowerCase()}@kline_${interval}"
        val binWsApi = WsBase.binWsApi(api, sub)
        return send(binWsApi)
    }

    fun send(msg: String?): Boolean {
        return BinWsImpl.binWs.send(msg)
    }
}