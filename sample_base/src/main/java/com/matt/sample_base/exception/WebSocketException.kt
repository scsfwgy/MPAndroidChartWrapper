package com.matt.sample_base.exception

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/04/16 11:15
 * 描 述 ：websocket异常
 * ============================================================
 */
class WebSocketException(message: String? = null, throwable: Throwable? = null)
    : BaseException(message, throwable)