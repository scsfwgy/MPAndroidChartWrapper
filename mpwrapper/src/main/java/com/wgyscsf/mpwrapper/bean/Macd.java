package com.wgyscsf.mpwrapper.bean;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/09/14 15:22
 * 描 述 ：
 * ============================================================
 */
public class Macd {
    //macd
    public double dif = KViewConstant.VALUE_DEF;
    public double dea = KViewConstant.VALUE_DEF;
    public double macd = KViewConstant.VALUE_DEF;

    public boolean isDifAvailable() {
        return dif != KViewConstant.VALUE_DEF;
    }

    public boolean isDeaAvailable() {
        return dea != KViewConstant.VALUE_DEF;
    }

    public boolean isMacdAvailable() {
        return macd != KViewConstant.VALUE_DEF;
    }
}
