package com.example.sample_binance.model.api;

import java.io.Serializable;

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
}
