package com.example.sample_binance.repository.ws

import android.util.Log
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.GsonUtils
import com.example.sample_binance.SampleBinanceInit
import com.example.sample_binance.model.ws.WsLatestKLinWrapper
import com.example.sample_binance.model.ws.WsSimpleTicker
import com.google.gson.JsonParser
import com.matt.libwrapper.repository.net.DefOkHttpClient
import okhttp3.*
import org.greenrobot.eventbus.EventBus

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
        const val COUNT_RETRY = 10
        val binWs: BinWs by lazy {
            BinWsImpl()
        }
    }

    var mWebSocket: WebSocket? = null
    var retry = 0

    override fun conn() {
        val request =
            Request
                .Builder()
                .url(SampleBinanceInit.BASE_URL_WS)
                .build()
        DefOkHttpClient.instance.defOkHttpClient()
            .newWebSocket(request, object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    super.onOpen(webSocket, response)
                    mWebSocket = webSocket
                    log("onOpen:$response,$mWebSocket")
                    reset()
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    super.onMessage(webSocket, text)
                    onMsg(text)
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    super.onFailure(webSocket, t, response)
                    log("onFailure:$t")
                    onConnFail()
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    super.onClosed(webSocket, code, reason)
                    log("onClosed:$code,$reason")
                    onConnFail()
                }

                fun onMsg(text: String) {
                    try {
                        onSafeMsg(text)
                    } catch (e: Exception) {
                        log("解析出错：${e.localizedMessage}")
                        e.printStackTrace()
                    }
                }

                fun onSafeMsg(text: String) {
                    log("onMessage:$text")
                    if (text.contains("result")) {
                        log("收到了不包含事件类型的事件，终止解析：$text")
                        return
                    }
                    val json = JsonParser.parseString(text)
                    if (json.isJsonObject) {
                        val jsonObject = json.asJsonObject
                        val type = jsonObject["e"].asString
                            ?: throw IllegalArgumentException("不存在字段e,数据非法")
                        dispatchMsgByType(text, type)
                    } else {
                        throw IllegalArgumentException("收到的信息：!json.isJsonObject，非法")
                    }
                }

                fun dispatchMsgByType(json: String, type: String) {
                    if (type == "kline") {
                        val fromJson =
                            GsonUtils.fromJson<WsLatestKLinWrapper>(
                                json,
                                WsLatestKLinWrapper::class.java
                            )
                        postMsg(fromJson)
                    } else if (type == "24hrMiniTicker") {
                        val fromJson =
                            GsonUtils.fromJson<WsSimpleTicker>(
                                json,
                                WsSimpleTicker::class.java
                            )
                        postMsg(fromJson)
                    } else {
                        log("dispatchMsgByType:未知消息类型：$type,数据：$json")
                    }
                }

                fun postMsg(any: Any) {
                    EventBus.getDefault().post(any)
                }
            })
    }

    private fun onConnFail() {
        mWebSocket = null
        if (retry >= COUNT_RETRY) {
            log("重试次数大于最大可重试次数：$COUNT_RETRY,停止重试")
            return
        }
        retry++
        log("断开连接，开始第${retry}次重试")
        conn()
    }

    fun reset() {
        retry = 0
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
}