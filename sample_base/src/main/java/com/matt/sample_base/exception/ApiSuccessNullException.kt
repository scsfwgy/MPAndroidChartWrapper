package com.matt.sample_base.exception

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/05/08 15:42
 * 描 述 ：api过来走的success,出现一个不允许为null的情况
 * ============================================================
 */
class ApiSuccessNullException(message: String? = null, throwable: Throwable? = null)
    : BaseException(message, throwable)