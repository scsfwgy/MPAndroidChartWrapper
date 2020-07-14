package com.matt.demo.net.base

import com.matt.demo.bean.ApiData
import com.matt.sample_base.ui.base.BaseActivity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/11 10:16
 * 描 述 ：
 * ============================================================
 */
abstract class SimpleTObserver<T>(baseActivity: BaseActivity) : Observer<ApiData<T>> {
    val mBaseActivity: BaseActivity = baseActivity

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
        mBaseActivity.add(d)
    }

    override fun onNext(t: ApiData<T>) {
        if (mBaseActivity.isActivityFinish()) {
            activityFinished()
            return
        }
        if (t.isSuccess) {
            onUnCheckSuccess(t.data)
        } else {
            mBaseActivity.show(t.message)
        }
    }

    override fun onError(e: Throwable) {
        mBaseActivity.show(e.message)
    }

    open fun onUnCheckSuccess(it: T?) {
        if (it == null) throw IllegalArgumentException("后端返回数据不允许为null")
        onSuccess(it)
    }

    abstract fun onSuccess(it: T)

    /**
     * 回调回来如果页面已经销毁调用该方法。如果收到该指令，不向下执行任何指令。你可以handler这个方法继续做处理
     */
    open fun activityFinished() {
        //do nothing
    }

}