package com.example.sample_binance.repository.net.service

import com.example.sample_binance.model.api.ApiSymbolWrapper
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 3:15 PM
 * 描 述 ：
 * ============================================================
 **/
interface BinanceService {
    @GET("/api/v3/exchangeInfo")
    fun exchangeInfo(): Observable<ApiSymbolWrapper>

    @GET("/api/v3/klines")
    fun klines(@QueryMap params: MutableMap<String, Any>): Observable<Array<Array<Any>>>
}