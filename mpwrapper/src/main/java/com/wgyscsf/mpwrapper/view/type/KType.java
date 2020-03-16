package com.wgyscsf.mpwrapper.view.type;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 * 作 者 :    freer-2
 * 更新时间 ：2019/09/09 17:41
 * 描 述 ：
 * ============================================================
 */
public enum KType {
    K_TIMESHARE("分时", 1, "1m"),
    K_1MIN("1分", 2, "1m"),
    K_5MIN("5分", 3, "5m"),
    K_15MIN("15分", 4, "15m"),
    K_30MIN("30分", 5, "30m"),
    K_1HOUR("1小时", 6, "1h"),
    K_4HOUR("4小时", 7, "4h"),
    K_1D("1天", 8, "1d"),
    K_1W("1周", 9, "1w"),
    K_1M("1月", 10, "1mon");

    private String mLable;
    private int mId;
    private String mApiType;

    KType(String lable, int id, String apiType) {
        this.mLable = lable;
        this.mId = id;
        this.mApiType = apiType;
    }

    @NonNull
    public static List<KType> getKTypeList() {
        List<KType> kTypeList = new ArrayList<>();
        kTypeList.add(K_TIMESHARE);
        kTypeList.add(K_1MIN);
        kTypeList.add(K_5MIN);
        kTypeList.add(K_15MIN);
        kTypeList.add(K_30MIN);
        kTypeList.add(K_1HOUR);
        kTypeList.add(K_4HOUR);
        kTypeList.add(K_1D);
        kTypeList.add(K_1W);
        kTypeList.add(K_1M);
        return kTypeList;
    }

    @NonNull
    public static List<String> getTitleList() {
        List<KType> kTypeList = getKTypeList();
        List<String> titleList = new ArrayList<>();
        for (KType kType : kTypeList) {
            titleList.add(kType.mLable);
        }
        return titleList;
    }

    public String getmLable() {
        return mLable;
    }

    public int getmId() {
        return mId;
    }

    public String getmApiType() {
        return mApiType;
    }

    public boolean isTimeShare() {
        return mId == 1;
    }

    /**
     * 是否是大日期
     *
     * @return
     */
    public boolean isMaxLenDate() {
        return this == KType.K_1D || this == KType.K_1W || this == KType.K_1M;
    }
}