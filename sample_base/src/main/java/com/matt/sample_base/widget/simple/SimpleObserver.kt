package com.matt.sample_base.widget.simple

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/8 5:43 PM
 * 描 述 ：
 * ============================================================
 */
abstract class SimpleObserver<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {}
    override fun onNext(t: T) {}
    override fun onError(e: Throwable) {}
    override fun onComplete() {}
}