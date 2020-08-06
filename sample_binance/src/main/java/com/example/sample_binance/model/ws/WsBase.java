package com.example.sample_binance.model.ws;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/6 10:59 AM
 * 描 述 ：
 * ============================================================
 **/
abstract public class WsBase {

    /**
     * {
     * "e": "kline",     // 事件类型
     * "E": 123456789,   // 事件时间
     * "s": "BNBBTC",    // 交易对
     * }
     */
    public String s;
    public String e;
    public long E;
}
