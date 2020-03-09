package com.wgyscsf.mpwrapper.bean;

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2018/09/14 15:28
 * 描 述 ：量图，对于量图有两条ma线，作用和主图的ma基本一致。
 * ============================================================
 */
public class Vol {
    //vol 量，可选
    public double vol = KViewConstant.VALUE_DEF;
    //vol
    public double volMa5 = KViewConstant.VALUE_DEF;
    public double volMa10 = KViewConstant.VALUE_DEF;

    @Override
    public String toString() {
        return "Vol{" +
                "vol=" + vol +
                ", volMa5=" + volMa5 +
                ", volMa10=" + volMa10 +
                '}';
    }
}
