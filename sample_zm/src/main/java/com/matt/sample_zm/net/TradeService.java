package com.matt.sample_zm.net;



import com.matt.sample_zm.bean.ApiData;
import com.matt.sample_zm.bean.ApiProduct;

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
public interface TradeService {

    @GET("/product/details")
    Observable<ApiData<List<ApiProduct>>> getProductList(@QueryMap Map<String, Object> params);

    @GET("/product/detail/{productCode}")
    Observable<ApiData<ApiProduct>> getProductDetails(@Path("productCode") String productCode);
}
