package com.example.sample_binance.model.ws;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/6 11:32 AM
 * 描 述 ：
 * ============================================================
 **/
public class WsSimpleTicker extends WsBase {

    /**
     * {
     * "e": "24hrMiniTicker",  // 事件类型
     * "E": 123456789,         // 事件时间
     * "s": "BNBBTC",          // 交易对
     * "c": "0.0025",          // 最新成交价格
     * "o": "0.0010",          // 24小时前开始第一笔成交价格
     * "h": "0.0025",          // 24小时内最高成交价
     * "l": "0.0010",          // 24小时内最低成交加
     * "v": "10000",           // 成交量
     * "q": "18"               // 成交额
     * }
     */
    public double q;
    public double c;
    public double v;
    public double h;
    public double l;
    public double o;
}
