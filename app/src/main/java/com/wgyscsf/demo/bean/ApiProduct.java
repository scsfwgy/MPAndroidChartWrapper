package com.wgyscsf.demo.bean;

import com.blankj.utilcode.util.TimeUtils;
import com.wgyscsf.mpwrapper.utils.FormatUtil;
import com.wgyscsf.mpwrapper.utils.MathUtil;

import java.io.Serializable;


/**
 * ============================================================
 * 作 者 :    freer-2
 * 更新时间 ：2019/08/09 17:34
 * 描 述 ：
 * ============================================================
 */
public class ApiProduct implements Serializable {

    /**
     * cnName : string
     * createTime : 0
     * currency : string
     * currentPrice : 0
     * enName : string
     * exchanger : 0
     * hintSpread : 0
     * leverage : 0
     * pc : 0
     * previousClose : 0
     * productCode : string
     * ratePercentage : 0
     * satoshi : 0
     * sequence : 0
     * updateTime : 0
     * zoneId : string
     */

    public String cnName;
    public long createTime;
    public String currency;
    public String enName;
    public int exchanger;
    public long extraId;
    public double hintSpread;
    public int leverage;
    public int pc;
    public String productCode;
    public int satoshi;
    public long sequence;
    public long updateTime;
    public String zoneId;
    public double previousClose;
    //万4=>返回4
    public double ratePercentage;
    public double askPrice;
    public double bidPrice;
    //lot
    public double lot;
    public String lotDesc;
    //产品详情中才会有
    //是否开市
    @Deprecated
    public boolean open;
    public boolean close;
    //下一次的开闭市时间
    public long openTimestamp;
    public long closeTimestamp;
    private double currentPrice;

    public String getOpenTimeFormat() {
        return TimeUtils.millis2String(openTimestamp, "MM-dd HH:mm");
    }

    public String getFormatAskPrice() {
        return FormatUtil.numFormat(askPrice, pc);
    }

    public String getFormatBidPrice() {
        return FormatUtil.numFormat(bidPrice, pc);
    }

    public String getFormatSpread() {
        double v = askPrice - bidPrice;
        int v1 = (int) (v * Math.pow(10, pc));
        return String.valueOf(v1);
    }

    public double getCurrentPrice() {
        if (askPrice != 0 && bidPrice != 0) {
            currentPrice = (askPrice + bidPrice) / 2.0;
        }
        return currentPrice;
    }

    public String getCurrentPriceFormat() {
        double currentPrice = getCurrentPrice();
        return FormatUtil.numFormat(currentPrice, pc);
    }

    public double getRate() {
        return MathUtil.divide(getCurrentPrice() - previousClose, previousClose) * 100;
    }

    public String getFormatRate() {
        return FormatUtil.percentageFormat(true, 2, getRate());
    }

    public String getFormatDis() {
        return FormatUtil.numFormat(true, pc, getCurrentPrice() - previousClose);
    }

    public boolean up() {
        return getCurrentPrice() > previousClose;
    }

    public int getQtyDigit() {
        if (satoshi <= 1) return 0;
        int len = String.valueOf(satoshi).length() - 1;
        return len <= 1 ? 1 : len;
    }

    @Override
    public String toString() {
        return "ApiProduct{" +
                "cnName='" + cnName + '\'' + "rate=" + getRate() +
                '}';
    }
}
