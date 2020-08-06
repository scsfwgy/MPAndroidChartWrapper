package com.example.sample_binance.model.ws

import com.blankj.utilcode.util.GsonUtils

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/4 11:31 AM
 * 描 述 ：
 * ============================================================
 */
class WsReqBase(
    val method: String,
    val params: Array<String>,
    val id: Int
) {
    companion object {
        fun binWsApi(params: Array<String>, subscribe: Boolean, id: Int = 10): String {
            val wsBase =
                WsReqBase(if (subscribe) "SUBSCRIBE" else "UNSUBSCRIBE", params, id)
            return GsonUtils.toJson(wsBase)
        }

        fun binWsApi(params: String, subscribe: Boolean): String {
            return binWsApi(arrayOf(params), subscribe)
        }
    }
}