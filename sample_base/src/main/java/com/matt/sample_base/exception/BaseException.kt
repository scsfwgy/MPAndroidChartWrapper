package com.matt.sample_base.exception

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/06/26 18:39
 * 描 述 ：
 * ============================================================
 */
open class BaseException(message: String? = null, cause: Throwable? = null)
    : Exception(message, cause)