package com.example.sample_binance.model.ws;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 3:35 PM
 * 描 述 ：
 * ============================================================
 **/
public class WsLatestKLine {
    /**
     * {
     * "t": 123400000, // 这根K线的起始时间
     * "T": 123460000, // 这根K线的结束时间
     * "s": "BNBBTC",  // 交易对
     * "i": "1m",      // K线间隔
     * "f": 100,       // 这根K线期间第一笔成交ID
     * "L": 200,       // 这根K线期间末一笔成交ID
     * "o": "0.0010",  // 这根K线期间第一笔成交价
     * "c": "0.0020",  // 这根K线期间末一笔成交价
     * "h": "0.0025",  // 这根K线期间最高成交价
     * "l": "0.0015",  // 这根K线期间最低成交价
     * "v": "1000",    // 这根K线期间成交量
     * "n": 100,       // 这根K线期间成交数量
     * "x": false,     // 这根K线是否完结(是否已经开始下一根K线)
     * "q": "1.0000",  // 这根K线期间成交额
     * "V": "500",     // 主动买入的成交量
     * "Q": "0.500",   // 主动买入的成交额
     * "B": "123456"   // 忽略此参数
     * }
     */
    public float c;
    public int f;
    public float h;
    public String i;
    public int L;
    public float l;
    public int n;
    public float o;
    public float q;
    public float Q;
    public String s;
    public long t;
    public long T;
    public float v;
    public float V;
    public boolean x;
}
