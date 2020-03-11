package com.wgyscsf.demo.net

import com.bitconch.lib_wrapper.net.AppRetrofitBuilder


/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 更新时间 ：2019/06/20 16:28
 * 描 述 ：
 * ============================================================
 */
object ServiceWrapper {
    val productService: ProductService by lazy {
        AppRetrofitBuilder.mRetrofit.create(ProductService::class.java)
    }
}
