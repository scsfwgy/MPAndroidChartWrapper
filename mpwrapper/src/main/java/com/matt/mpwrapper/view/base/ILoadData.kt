package com.matt.mpwrapper.view.base

import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MasterViewType
import com.matt.mpwrapper.view.type.MinorIndicatorType

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/23 09:48
 * 描 述 ：
 * ============================================================
 */
interface ILoadData {

    /**
     * 一些基础配置信息，可以动态更新
     */
    fun updateConfig(
        yDataDigit: Int = 4,
        masterViewType: MasterViewType = MasterViewType.CANDLE,
        masterIndicatorType: MasterIndicatorType = MasterIndicatorType.MA,
        minorIndicatorType: MinorIndicatorType = MinorIndicatorType.MACD
    )

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
    fun reLoadData(priceList: List<Price>, volList: List<Float>? = null)

    /**
     * 加载更多数据
     */
    fun loadMoreData(priceList: List<Price>, volList: List<Float>? = null)

    /**
     * 实时数据
     */
    fun pushData(priceList: List<Price>, volList: List<Float>? = null)

    /**
     * 刷新最新数据，只会刷新不会追加
     */
    fun refreshData(latestTime: Long, latestPrice: Float, latestVol: Float? = null)
}