package com.example.sample_binance.repository.memory

import com.example.sample_binance.model.api.Api24Hr
import com.example.sample_binance.model.api.ApiSymbol

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/5 11:10 AM
 * 描 述 ：
 * ============================================================
 **/
object GlobalCache {
    const val COUNT_SYMBOL = 1024

    private val m24HrMap by lazy {
        HashMap<String, Api24Hr>(COUNT_SYMBOL)
    }

    private val mSymbolMap by lazy {
        HashMap<String, ApiSymbol>(COUNT_SYMBOL)
    }


    fun update24Hr(list: List<Api24Hr>) {
        list?.forEach {
            m24HrMap[it.symbol] = it
        }
    }

    fun get24HrBySymbol(symbol: String?): Api24Hr? {
        if (symbol == null) return null
        return m24HrMap[symbol]
    }

    fun updateSymbol(list: List<ApiSymbol>?) {
        list?.forEach {
            mSymbolMap[it.symbol] = it
        }
    }

    fun getSymbolBySymbol(symbol: String?): ApiSymbol? {
        if (symbol == null) return null
        return mSymbolMap[symbol]
    }

    fun getSymbolMap(): HashMap<String, ApiSymbol> {
        return mSymbolMap
    }

    fun get24HrMap(): HashMap<String, Api24Hr> {
        return m24HrMap
    }
}