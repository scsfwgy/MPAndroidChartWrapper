package com.wgyscsf.demo.vm

import com.wgyscsf.demo.bean.ApiData
import com.wgyscsf.demo.net.ServiceWrapper
import com.wgyscsf.mpwrapper.bean.Price
import com.wgyscsf.mpwrapper.view.type.KType
import io.reactivex.Observable

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/16 12:48
 * 描 述 ：
 * ============================================================
 */
class ChartViewModel : BaseViewModel() {
    lateinit var mSymbol: String

    /**
     * 从网络加载数据
     */
    fun getQuoteListByNet(
        symbol: String,
        kType: KType
    ): Observable<ApiData<List<Price>>> {
        val params = HashMap<String, Any>()
        params["count"] = getQueryLimit(kType)
        return ServiceWrapper.MARKET_SERVICE.chartQuotes(symbol, kType.getmApiType(), params)
            .flatMap {
                if (!it.isSuccess) {
                    Observable.just(
                        ApiData.newErrorIns(
                            it,
                            List::class.java
                        )
                    ) as Observable<ApiData<List<Price>>>
                } else {
                    val data = it.data ?: throw IllegalArgumentException("it.data不允许为null")
                    val list = ArrayList<Price>()
                    data.forEach { item ->
                        val price = Price(item.s, item.o, item.h, item.l, item.c)
                        list.add(price)
                    }
                    val apiData = ApiData.newSuccessIns(List::class.java)
                    apiData.data = list
                    Observable.just(apiData) as Observable<ApiData<List<Price>>>
                }
            }
    }

    private fun getQueryLimit(type: KType): Int {
        return if (type == KType.K_TIMESHARE || type == KType.K_1MIN) {
            500
        } else if (type == KType.K_5MIN) {
            288
        } else
            200
    }
}