package com.example.sample_binance.model.ws;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/8/6 8:13 PM
 * 描 述 ：
 * ============================================================
 **/
public class WsData<T> {
    public String stream;
    public T data;
}
