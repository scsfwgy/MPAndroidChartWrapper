package com.matt.mpwrapper.view.base

import com.matt.mpwrapper.bean.Price

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/23 09:48
 * 描 述 ：
 * ============================================================
 */
interface LoadData {

    /**
     * 重新加载数据，也是第一次加载数据用
     */
    fun reLoadData(priceList: List<Price>)

    /**
     * 加载更多数据
     */
    fun loadMoreData(priceList: List<Price>)

    /**
     * 实时数据
     */
    fun pushData(price: Price)
}