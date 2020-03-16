package com.wgyscsf.demo.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        getIntentExtras(intent)
    }

    open fun getIntentExtras(intent: Intent) {}

    override fun onDestroy() {
        super.onDestroy()
        if (mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear()
        }
    }

    fun showToast(msg: String?, showLong: Boolean = false) {
        if (msg == null) return
        if (showLong) {
            ToastUtils.showLong(msg)
        } else {
            ToastUtils.showShort(msg)
        }
    }

    protected fun addFragment(fragment: BaseFragment?, @IdRes containerViewId: Int) {
        if (fragment == null) return
        supportFragmentManager.beginTransaction()
            .add(containerViewId, fragment, fragment::class.java.simpleName)
            .commitAllowingStateLoss()
    }

    open fun <T : ViewModel> getVM(modelClass: Class<T>): T {
        return ViewModelProvider(this).get(modelClass)
    }

}