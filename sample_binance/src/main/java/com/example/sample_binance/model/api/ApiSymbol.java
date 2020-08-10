package com.example.sample_binance.model.api;

import com.matt.mpwrapper.utils.MathUtil;
import com.matt.mpwrapper.utils.NumberUtils;

import java.io.Serializable;
import java.util.List;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 3:35 PM
 * 描 述 ：
 * ============================================================
 **/
public class ApiSymbol implements Serializable {

    /**
     * symbol : ETHBTC
     * quoteOrderQtyMarketAllowed : true
     * baseAsset : ETH
     * baseAssetPrecision : 8
     * isSpotTradingAllowed : true
     * quoteAssetPrecision : 8
     * quoteCommissionPrecision : 8
     * ocoAllowed : true
     * quotePrecision : 8
     * icebergAllowed : true
     * isMarginTradingAllowed : true
     * quoteAsset : BTC
     * baseCommissionPrecision : 8
     * status : TRADING
     */
    public String symbol;
    public boolean quoteOrderQtyMarketAllowed;
    public String baseAsset;
    public int baseAssetPrecision;
    public boolean isSpotTradingAllowed;
    public int quoteAssetPrecision;
    public int quoteCommissionPrecision;
    public boolean ocoAllowed;
    public int quotePrecision;
    public boolean icebergAllowed;
    public boolean isMarginTradingAllowed;
    public String quoteAsset;
    public int baseCommissionPrecision;
    public String status;
    public List<ApiPriceFilter> filters;

    //internal
    private ApiPriceFilter getApiPriceFilter() {
        if (filters == null) return null;
        for (ApiPriceFilter filter : filters) {
            if ("PRICE_FILTER".equals(filter.filterType)) {
                return filter;
            }
        }
        return null;
    }

    /**
     * 获取产品精度，获取方式很扯，但是没办法
     *
     * @return
     */
    public int getPriceDigit() {
        ApiPriceFilter apiPriceFilter = getApiPriceFilter();
        if (apiPriceFilter == null) return 2;
        double minPrice = apiPriceFilter.minPrice;
        double divide = MathUtil.divide(1.0, minPrice);
        int i = (int) divide;
        return String.valueOf(i).length();
    }
}
