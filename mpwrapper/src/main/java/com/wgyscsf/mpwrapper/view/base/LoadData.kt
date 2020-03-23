package com.wgyscsf.mpwrapper.view.base

import com.wgyscsf.mpwrapper.bean.Price

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/23 09:48
 * 描 述 ：
 * ============================================================
 */
interface LoadData {
    fun reLoadData(priceList: List<Price>)
    fun loadMoreData(priceList: List<Price>)
    fun pushData(price: Price)
}