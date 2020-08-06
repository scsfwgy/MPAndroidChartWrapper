package com.example.sample_binance.repository.ws

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import com.example.sample_binance.model.ws.WsLatestKLinWrapper
import com.example.sample_binance.model.ws.WsSimpleTicker
import com.google.gson.JsonParser
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.greenrobot.eventbus.EventBus

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/6 11:54 AM
 * 描 述 ：
 * ============================================================
 **/
class BinWsListener(val binWs: BinWs) : WebSocketListener() {
    val TAG = BinWsImpl::class.java.simpleName

    companion object {
        const val COUNT_RETRY = 10
    }

    var retry = 0

    fun log(msg: String) {
        Log.d(TAG, msg)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        binWs.setWebSocket(webSocket)
        log("onOpen:$response,$webSocket")
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
        if (text.contains("result") || !text.contains("stream")) {
            log("收到了不包含事件类型的事件，终止解析：$text")
            return
        }
        val orginElement = JsonParser.parseString(text)
        if (!orginElement.isJsonObject) throw IllegalArgumentException("收到的信息：!json.isJsonObject，非法")
        val jsonObject = orginElement.asJsonObject
        val dataElement = jsonObject["data"]
        if (!dataElement.isJsonObject) throw IllegalArgumentException("收到的信息：!dataElement.isJsonObject，非法")
        val dataObject = dataElement.asJsonObject
        val type = dataObject["e"].asString ?: throw IllegalArgumentException("type不允许为null")
        val json = dataObject.toString()
        dispatchMsgByType(json, type)
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

    private fun onConnFail() {
        binWs.setWebSocket(null)
        if (retry >= COUNT_RETRY) {
            log("重试次数大于最大可重试次数：${COUNT_RETRY},停止重试")
            return
        }
        retry++
        log("断开连接，开始第${retry}次重试")
        binWs.conn()
    }

    fun reset() {
        retry = 0
    }
}