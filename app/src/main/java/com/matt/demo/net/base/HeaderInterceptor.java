package com.matt.demo.net.base;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ============================================================
 * 作 者 :    matt@163.com
 * 更新时间 ：2018/05/10 11:47
 * 描 述 ：
 * ============================================================
 */
public class HeaderInterceptor implements Interceptor {
    public static final String TAG = HeaderInterceptor.class.getSimpleName();
    private final int mAppVersionCode;
    private final String mAppVersionName;
    private final String mAppPackageName;
    private final int mSdkVersionCode;
    private final String mSdkVersionName;
    private final String mAndroidID;
    //制造商
    private final String mManufacturer;
    //产品型号
    private final String mModel;


    public HeaderInterceptor() {
        mAppVersionCode = AppUtils.getAppVersionCode();
        mAppVersionName = AppUtils.getAppVersionName();
        mAppPackageName = AppUtils.getAppPackageName();
        mSdkVersionCode = DeviceUtils.getSDKVersionCode();
        mSdkVersionName = DeviceUtils.getSDKVersionName();
        mAndroidID = DeviceUtils.getAndroidID();
        mManufacturer = DeviceUtils.getManufacturer();
        mModel = DeviceUtils.getModel();
    }

    @NotNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
//        String authorization = GlobalConfig.getToken();
//        if (authorization != null) {
//            requestBuilder.addHeader("Authorization", "Bearer " + authorization);
//        }


        //requestBuilder.addHeader("X-XTX-AppVersionCode", String.valueOf(mAppVersionCode));
        //requestBuilder.addHeader("X-XTX-AppVersionName", mAppVersionName != null ? mAppVersionName : "none");
        requestBuilder.addHeader("X-XTX-AppPackageName", mAppPackageName != null ? mAppPackageName : "none");
        requestBuilder.addHeader("X-XTX-SdkVersionCode", String.valueOf(mSdkVersionCode));
        //requestBuilder.addHeader("X-XTX-SdkVersionName", mSdkVersionName != null ? mSdkVersionName : "none");
        //requestBuilder.addHeader("X-XTX-AndroidId", mAndroidID != null ? mAndroidID : "none");
        requestBuilder.addHeader("X-XTX-Manufacturer", mManufacturer != null ? mManufacturer : "none");
        //requestBuilder.addHeader("X-XTX-Model", mModel != null ? mModel : "none");

        /**
         * 后端需要的
         */
        requestBuilder.addHeader("X-SS-DEVICE", mAndroidID != null ? mAndroidID : "none");
        requestBuilder.addHeader("X-SS-PLATFORM-SOURCE", "ANDROID");
        requestBuilder.addHeader("X-SS-OS-VERSION", mSdkVersionName != null ? mSdkVersionName : "none");
        requestBuilder.addHeader("X-SS-DEVICE-NAME", mModel != null ? mModel : "none");
        requestBuilder.addHeader("X-SS-VERSION-CODE", String.valueOf(mAppVersionCode));
        requestBuilder.addHeader("X-SS-VERSION", mAppVersionName != null ? mAppVersionName : "none");
        request = requestBuilder.build();
        return chain.proceed(request);
    }

}
