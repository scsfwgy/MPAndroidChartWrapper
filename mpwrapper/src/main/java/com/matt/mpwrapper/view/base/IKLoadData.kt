package com.matt.mpwrapper.view.base

import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MasterViewType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import com.matt.mpwrapper.view.type.VolIndicatorType

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/23 09:48
 * 描 述 ：
 * ============================================================
 */
interface IKLoadData : IBaseLoadData {

    /**
     * 一些基础配置信息，可以动态更新
     */
    fun updateConfig(
        yDataDigit: Int = 4,
        masterViewType: MasterViewType = MasterViewType.CANDLE,
        masterIndicatorType: MasterIndicatorType = MasterIndicatorType.MA,
        minorIndicatorType: MinorIndicatorType = MinorIndicatorType.MACD,
        volIndicatorType: VolIndicatorType = VolIndicatorType.VOL_MA
    )

    /**
     * 数据加载
     * @param priceList 价格
     * @param volList 量
     * @param reload
     */
    fun loadData(
        priceList: List<Price>, volList: List<Float>? = null,
        reload: Boolean = true,
        append: Boolean = false,
        loadMore: Boolean = false
    )

    /**
     * 刷新最新数据，只会刷新不会追加
     */
    fun refreshData(latestTime: Long, latestPrice: Float, latestVol: Float? = null)
}