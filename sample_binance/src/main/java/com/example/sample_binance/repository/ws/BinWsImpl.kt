package com.example.sample_binance.repository.ws

import android.util.Log
import com.example.sample_binance.SampleBinanceInit
import com.matt.libwrapper.repository.net.DefOkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/30 7:59 PM
 * 描 述 ：
 * ============================================================
 **/
class BinWsImpl : BinWs {
    val TAG = BinWsImpl::class.java.simpleName

    companion object {
        val binWs: BinWs by lazy {
            BinWsImpl()
        }
    }

    var mWebSocket: WebSocket? = null

    override fun conn() {
        val request =
            Request
                .Builder()
                .url(SampleBinanceInit.BASE_URL_WS)
                .build()
        DefOkHttpClient.instance.defOkHttpClient().newWebSocket(request, BinWsListener(this))
    }

    override fun disConn() {
    }

    fun log(msg: String) {
        Log.d(TAG, msg)
    }

    override fun send(msg: String?): Boolean {
        log("send:$msg")
        val webSocket = getWebSocket()
        if (webSocket == null) {
            log("webSocket == null，取消发送消息")
            return false
        }
        if (msg == null) {
            log("msg == null，取消发送消息")
            return false
        }
        return webSocket.send(msg)

    }

    override fun getWebSocket(): WebSocket? {
        return mWebSocket
    }

    override fun setWebSocket(webSocket: WebSocket?) {
        mWebSocket = webSocket
    }
}