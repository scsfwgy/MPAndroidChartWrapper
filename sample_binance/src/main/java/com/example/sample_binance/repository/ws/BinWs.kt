package com.example.sample_binance.repository.ws

import okhttp3.WebSocket

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/30 8:00 PM
 * 描 述 ：
 * ============================================================
 **/
interface BinWs {
    fun conn()
    fun disConn()
    fun send(msg: String?):Boolean
    fun getWebSocket(): WebSocket?
}