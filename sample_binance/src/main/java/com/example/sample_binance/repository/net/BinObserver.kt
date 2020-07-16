package com.example.sample_binance.repository.net

import com.blankj.utilcode.util.ToastUtils
import com.matt.libwrapper.widget.IDisposable
import com.matt.libwrapper.widget.ObserverWrapper
import java.net.SocketTimeoutException

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 5:17 PM
 * 描 述 ：
 * ============================================================
 **/
abstract class BinObserver<T>(iDisposable: IDisposable) : ObserverWrapper<T>(iDisposable) {

    override fun onCatchNext(t: T) {
        super.onCatchNext(t)
        onFinalSuccess(t)
    }

    override fun onCatchError(e: Throwable) {
        super.onCatchError(e)
        onHandlerException(e)
    }


    override fun onHandlerException(e: Throwable) {
        if (e is SocketTimeoutException) {
            ToastUtils.showLong("连接超时，币安api需要科学上网，请检查")
        } else {
            super.onHandlerException(e)
        }

    }

    abstract fun onFinalSuccess(t: T)

}