package com.matt.demo.net;


import com.matt.demo.bean.ApiData;
import com.matt.demo.bean.ApiQuote;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * ============================================================
 * 作 者 :    freer-2
 * 更新时间 ：2019/08/09 11:18
 * 描 述 ：
 * ============================================================
 */
public interface MarketService {
    @GET("kline/{productCode}/{type}")
    Observable<ApiData<List<ApiQuote>>> chartQuotes(@Path("productCode") String productCode, @Path("type") String type, @QueryMap Map<String, Object> params);
}
