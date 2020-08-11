package com.matt.mpwrapper.view.base

import com.matt.mpwrapper.bean.KViewData

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/23 09:48
 * 描 述 ：
 * ============================================================
 */
interface IChartLoadData {


    /**
     * 在加载前调用
     */
    fun onLoading(loadingMsg: String? = null)

    /**
     * 加载失败后调用
     */
    fun onLoadingFail(loadingFailMsg: String? = null)

    /**
     * 重新加载数据，也是第一次加载数据用
     */
    fun reLoadData(kViewDataList: List<KViewData>)

    /**
     * 加载更多数据
     */
    fun loadMoreData(kViewDataList: List<KViewData>)

    /**
     * 实时数据
     */
    fun pushData(kViewDataList: List<KViewData>)


    /**
     * 刷新最新数据，只会刷新不会追加
     */
    fun refreshData(kViewData: KViewData)
}