package com.wgyscsf.demo.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.wgyscsf.demo.R;


/**
 * ============================================================
 * 作 者 :    freer-2
 * 更新时间 ：2019/08/09 11:25
 * 描 述 ：
 * ============================================================
 */
public class ApiData<T> {
    @Nullable
    public T data;
    private int code;
    private int bizCode;
    @Nullable
    private String message;

    public static <K> ApiData<K> newErrorIns(ApiData apiData, Class<K> kClass) {
        ApiData<K> kApiData = new ApiData<>();
        kApiData.message = apiData.message;
        kApiData.code = apiData.code;
        kApiData.bizCode = apiData.bizCode;
        return kApiData;
    }

    public static <K> ApiData<K> newSuccessIns(Class<K> kClass) {
        ApiData<K> kApiData = new ApiData<>();
        kApiData.code = 200;
        kApiData.bizCode = 0;
        return kApiData;
    }

    public boolean isSuccess() {
        return code == 200 && bizCode == 0;
    }

    @NonNull
    public String getMessage() {
        if (message != null) return message;
        return Utils.getApp().getString(R.string.wrapper_CODE_UNKNOWN);
    }

    public ApiData<T> setMessage(@Nullable String message) {
        this.message = message;
        return this;
    }

    public int getInnerCode() {
        if (bizCode != 0) return bizCode;
        return code;
    }

    public int getCode() {
        return code;
    }

    public ApiData<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public int getBizCode() {
        return bizCode;
    }

    public ApiData<T> setBizCode(int bizCode) {
        this.bizCode = bizCode;
        return this;
    }
}
