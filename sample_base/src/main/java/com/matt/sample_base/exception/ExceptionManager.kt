package com.matt.sample_base.exception

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.matt.sample_base.ui.base.BaseActivity


/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/6/16 10:54
 * 描 述 ：
 * ============================================================
 **/
object ExceptionManager {
    val TAG = ExceptionManager.javaClass.simpleName

    /**
     * 收到异常统一处理，Exception越精确越好(可以定义自己业务相关的Exception),根据收到的异常进行对应的处理。
     * 所以在在safeInitAll()中遇到错误应该主动抛出精确信息而不是catch操作，方便统一处理。
     * 可以在这里做排查、收集以及上报操作，可以在一定程度上降低业务上的错误导致crash异常。
     *
     * @param throwable
     */
    fun handlerException(
        throwable: Throwable?, kBaseActivity: BaseActivity? = null,
        exceptionType: ExceptionType = ExceptionType.UI
    ) {
        if (throwable == null) {
            Log.d(TAG, "错误堆栈为空，默认什么也不处理")
            return
        }
        throwable.printStackTrace()
        //这里单独设置一个异常页面或者直接结束？
        val msg = when (throwable) {
            is NoLoginException -> {
                getMessageByException(throwable, "请先登录")
            }
            is ReleaseException -> {
                getMessageByException(throwable, null)
            }
            is ParamsException -> {
                getMessageByException(throwable, "缺少必要参数")
            }
            else -> {
                getMessageByException(throwable, "未知错误")
            }
        }
        Log.e(TAG, "catchMainException: $msg", throwable)
        if (exceptionType == ExceptionType.NET) {
            val netMsgByCode = getNetMsgByCode(null, throwable)
            ToastUtils.showShort(netMsgByCode)
        } else {
            if (msg != null) {
                kBaseActivity?.showToast(msg)
                kBaseActivity?.finish()
            }
        }
    }

    @JvmOverloads
    @JvmStatic
    fun getNetMsgByCode(code: Int? = null, e: Throwable? = null): String {
        return ""
    }

    private fun getMessageByException(throwable: Throwable, defMsg: String? = null): String? {
        return throwable.message ?: defMsg
    }
}