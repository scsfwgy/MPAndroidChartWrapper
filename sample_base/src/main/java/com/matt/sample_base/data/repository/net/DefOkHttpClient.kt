package com.lbk.lib_wrapper.repository.net.base

import android.util.Log
import com.matt.sample_base.data.repository.net.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/06/20 15:02
 * 描 述 ：
 * ============================================================
 */
open class DefOkHttpClient private constructor() {

    /**
     * common api
     *
     * @return
     */
    fun defOkHttpClient(): OkHttpClient {
        val builder = defOkHttpClientBuilder()
        return builder.build()
    }

    /**
     * 构造一个通用的builder
     *
     * @return
     */
    //给后端传递head参数
    fun defOkHttpClientBuilder(): OkHttpClient.Builder {
        /**
         * 全局日志控制
         */
        //系统默认的日志系统，打印不没关，但是便于复制。
        val defLoggingInterceptor =
                HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                    override fun log(message: String) {
                        Log.d("OkHttp", message)
                    }
                })
        defLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_CONN, TimeUnit.SECONDS)
                .readTimeout(READ_CONN, TimeUnit.SECONDS)
                .writeTimeout(WRITE_CONN, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(HeaderInterceptor())
                //日志系统二选一：是要格式化的还是要原始格式数据
                //.addInterceptor(jsonLoggingInterceptor)
                .addInterceptor(defLoggingInterceptor)
    }

    companion object {
        val TIMEOUT_CONN: Long = 15
        val READ_CONN: Long = 15
        val WRITE_CONN: Long = 15

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            DefOkHttpClient()
        }
    }
}
