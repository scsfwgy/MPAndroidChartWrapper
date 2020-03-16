package com.wgyscsf.demo.ui.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * 创建日期 : 2018/7/9
 * 描 述 :   handle错误
 */
abstract class HandleExceptionFragment : BaseFragment() {

    //内部获取rootView的一个中间变量
    var mInterRootView: View? = null

    /**
     * 子类使用这个rootView
     */
    val mRootView: View by lazy {
        val view =
            mInterRootView ?: throw IllegalArgumentException("mInterRootView不可以为null,请检查业务逻辑是否有问题？")
        view
    }

    abstract fun layoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            getBundleExtras(arguments)
        } catch (e: Exception) {
            catchMainException(e)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        try {
            if (mInterRootView == null) {
                Log.d(TAG, "onCreateView: ")
                val rootView = inflater.inflate(layoutId(), container, false)
                extractRes()
                mInterRootView = rootView
                initViews(rootView)
                initListener(rootView)
                safeInitAll(rootView)
            }
        } catch (e: Exception) {
            catchMainException(e)
        }

        return mInterRootView
    }

    /**
     * 抽取资源文件：颜色、文字等
     */
    protected fun extractRes() {

    }

    open fun initViews(rootView: View) {

    }

    open fun initListener(rootView: View) {

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
     * @param rootView
     */
    protected abstract fun safeInitAll(rootView: View)

    /**
     * 收到异常统一处理，Exception越精确越好(可以定义自己业务相关的Exception),根据收到的异常进行对应的处理。
     * 所以在在safeInitAll()中遇到错误应该主动抛出精确信息而不是catch操作，方便统一处理。
     * 可以在这里做排查、收集以及上报操作，可以在一定程度上降低业务上的错误导致crash异常。
     *
     * @param exception
     */
    protected fun catchMainException(exception: Exception) {
        if (mBaseActivity is HandleExceptionActivity) {
            (mBaseActivity as HandleExceptionActivity).catchMainException(exception)
        } else {
            throw IllegalArgumentException("mBaseActivity instanceof HandleExceptionActivity 判定失败")
        }
    }

    open fun getBundleExtras(bundle: Bundle?) {
        //xx
    }
}
