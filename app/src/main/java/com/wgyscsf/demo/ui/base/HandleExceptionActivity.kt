package com.wgyscsf.demo.ui.base

import android.os.Bundle
import android.util.Log


/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/05/08 15:35
 * 描 述 ：主动catch异常,在一定程度上更加确保应用的"安全"
 * ============================================================
 */
abstract class HandleExceptionActivity : BaseActivity() {

    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            //抽取的资源可能在getIntentExtras()中使用，所以这里没有任何业务逻辑，只有一些资源的获取
            extractRes()
            setContentView(getLayoutId())
            safeInitAll(savedInstanceState)
        } catch (e: Exception) {
            catchMainException(e)
        }

    }

    /**
     * 抽取资源文件：颜色、文字等
     */
    protected fun extractRes() {

    }

    override fun onDestroy() {
        try {
            super.onDestroy()
        } catch (e: Exception) {
            catchMainException(e)
        }

    }

    /**
     * 所有操作在这里处理，然后在activity层面全局捕获异常，再统一处理异常信息
     *
     * @param savedInstanceState
     */
    protected abstract fun safeInitAll(savedInstanceState: Bundle?)

    /**
     * 收到异常统一处理，Exception越精确越好(可以定义自己业务相关的Exception),根据收到的异常进行对应的处理。
     * 所以在在safeInitAll()中遇到错误应该主动抛出精确信息而不是catch操作，方便统一处理。
     * 可以在这里做排查、收集以及上报操作，可以在一定程度上降低业务上的错误导致crash异常。
     *
     * @param exception
     */
    fun catchMainException(exception: Exception) {
        exception.printStackTrace()
        Log.e(TAG, "catchMainException: ", exception)
        //通用处理
        //ToastUtil.showLongToast("出现了异常...");
        //这里单独设置一个异常页面或者直接结束？
        when (exception) {
            is IllegalArgumentException -> {
                showToast("参数异常")
            }
            else -> {
                showToast("发生点小意外，请稍后再试")
                Log.e(TAG, "catchMainException: ", exception)
                finish()
            }
        }
    }
}