package com.matt.sample_base.data.repository.net

import com.blankj.utilcode.util.GsonUtils
import com.lbk.lib_wrapper.repository.net.base.DefOkHttpClient
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/06/20 14:58
 * 描 述 ：抽象一个Retrofit对象,创建Retrofit直接继承这个抽象类即可
 * ============================================================
 */
abstract class AbstractRetrofitBuilder {

    val mRetrofit: Retrofit by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        createRetrofit()
    }

    open fun createRetrofit(): Retrofit {
        return createBuilder().build()
    }

    open fun createBuilder(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(apiBaseUrl())
            .addConverterFactory(GsonConverterFactory.create(GsonUtils.getGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(okHttpClient())

    }

    open fun okHttpClient(): OkHttpClient {
        return DefOkHttpClient.instance.defOkHttpClient()
    }

    protected abstract fun apiBaseUrl(): String
}
