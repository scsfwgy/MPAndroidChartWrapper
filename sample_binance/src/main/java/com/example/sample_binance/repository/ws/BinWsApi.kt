package com.example.sample_binance.repository.ws

import com.example.sample_binance.model.ws.WsReqBase

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/4 11:07 AM
 * 描 述 ：
 * ============================================================
 **/
object BinWsApi {

    fun kline(symbols: Array<String>, interval: String, sub: Boolean): Boolean {
        val api = symbols.map { "${it.toLowerCase()}@kline_${interval}" }.toTypedArray()
        val binWsApi = WsReqBase.binWsApi(api, sub)
        return send(binWsApi)
    }

    fun simpleTicker(symbols: Array<String>, sub: Boolean): Boolean {
        val api = symbols.map { "${it.toLowerCase()}@miniTicker" }.toTypedArray()
        val binWsApi = WsReqBase.binWsApi(api, sub)
        return send(binWsApi)
    }

    fun send(msg: String?): Boolean {
        return BinWsImpl.binWs.send(msg)
    }
}