package com.matt.mpwrapper.view.base

import com.matt.mpwrapper.bean.KViewData

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/23 09:48
 * 描 述 ：
 * ============================================================
 */
interface IChartLoadData : IBaseLoadData {

    fun loadData(
        kViewDataList: List<KViewData>,
        reload: Boolean = true,
        append: Boolean = false,
        loadMore: Boolean = false
    )

    /**
     * 刷新最新数据，只会刷新不会追加
     */
    fun refreshData(kViewData: KViewData)
}