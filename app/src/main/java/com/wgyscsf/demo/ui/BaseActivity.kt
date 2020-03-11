package com.wgyscsf.demo.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 10:32
 * 描 述 ：
 * ============================================================
 */
abstract class BaseActivity : AppCompatActivity() {

    val TAG: String by lazy {
        javaClass::class.java.simpleName
    }

    val mContext: Context by lazy {
        this
    }
    val mActivity: BaseActivity by lazy {
        this
    }

    val mCompositeDisposable by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        CompositeDisposable()
    }

    fun add(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    fun show(msg: String?, short: Boolean = true) {
        if (msg == null) return
        if (short) {
            ToastUtils.showShort(msg)
        } else {
            ToastUtils.showLong(msg)
        }
    }

    open fun isActivityFinish(): Boolean {
        val activity: BaseActivity = this
        return activity.isFinishing || activity.isDestroyed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear()
        }
    }

}