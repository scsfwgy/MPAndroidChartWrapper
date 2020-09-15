package com.matt.mpwrapper.view.base

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/9/15 6:56 PM
 * 描 述 ：
 * ============================================================
 **/
interface IBaseLoadData {
    /**
     * 在加载前调用
     */
    fun onLoading(loadingMsg: String? = null)

    /**
     * 加载失败后调用
     */
    fun onLoadingFail(loadingFailMsg: String? = null)

}