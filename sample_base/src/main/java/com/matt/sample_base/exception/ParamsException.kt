package com.matt.sample_base.exception

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/04/28 15:17
 * 描 述 ：参数错误
 * ============================================================
 */
class ParamsException(message: String? = null, throwable: Throwable? = null)
    : BaseException(message, throwable)