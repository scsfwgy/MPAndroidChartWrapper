package com.example.sample_binance.repository.ws

import android.util.Log
import com.example.sample_binance.SampleBinanceInit
import com.matt.libwrapper.repository.net.DefOkHttpClient
import okhttp3.*

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/30 7:59 PM
 * 描 述 ：
 * ============================================================
 **/
class BinWsImpl : BinWs {


    companion object {
        fun getBinWs(): BinWsImpl {
            return BinWsImpl()
        }
    }

    override fun conn() {
        val request = Request.Builder().url(SampleBinanceInit.BASE_URL_WS)
            .build()
        DefOkHttpClient.instance.defOkHttpClient()
            .newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    Log.d(SampleBinanceInit.TAG, "onOpen:$response")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    Log.d(SampleBinanceInit.TAG, "onFailure:$t")
                }
            })
    }

    override fun disConn() {
    }
}