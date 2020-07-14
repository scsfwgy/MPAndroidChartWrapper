package com.matt.sample_base.widget

import io.reactivex.disposables.Disposable

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/8 5:53 PM
 * 描 述 ：
 * ============================================================
 **/
interface IDisposable {
    fun addDisposable(disposable: Disposable)
}