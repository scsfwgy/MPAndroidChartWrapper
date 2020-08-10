package com.example.sample_binance.model.api;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/10 11:04 AM
 * 描 述 ：
 * ============================================================
 **/
public class ApiPriceFilter {
    /**
     * minPrice : 0.00000100
     * maxPrice : 100000.00000000
     * filterType : PRICE_FILTER
     * tickSize : 0.00000100
     */
    public double minPrice;
    public double maxPrice;
    public String filterType;
    public double tickSize;
}
