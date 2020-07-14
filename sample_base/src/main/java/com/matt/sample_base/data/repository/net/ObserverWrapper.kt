package com.matt.sample_base.data.repository.net

import android.util.Log
import com.matt.sample_base.SampleBaseInit
import com.matt.sample_base.widget.IDisposable
import com.matt.sample_base.widget.simple.SimpleObserver
import io.reactivex.disposables.Disposable

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/8 5:47 PM
 * 描 述 ：
 * ============================================================
 **/
open class ObserverWrapper<T>(private val mIDisposable: IDisposable? = null) : SimpleObserver<T>() {

    val TAG = SampleBaseInit.TAG

    override fun onSubscribe(d: Disposable) {
        super.onSubscribe(d)
        val iDisposable = mIDisposable
        if (iDisposable != null) {
            iDisposable.addDisposable(d)
        } else {
            Log.w(TAG, "没有传递mIDisposable,需要自己手动回收Disposable，不然会内存泄露！！！")
        }
    }

    override fun onNext(t: T) {
        super.onNext(t)
    }

    override fun onError(e: Throwable) {
        super.onError(e)
    }
}