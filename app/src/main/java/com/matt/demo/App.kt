package com.matt.demo

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.example.sample_binance.SampleBinanceInit

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 10:34
 * 描 述 ：
 * ============================================================
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        SampleBinanceInit.init(this)
    }
}