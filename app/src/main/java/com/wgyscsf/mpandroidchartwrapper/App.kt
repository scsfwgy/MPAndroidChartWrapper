package com.wgyscsf.mpandroidchartwrapper

import android.app.Application
import com.blankj.utilcode.util.Utils

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 10:34
 * 描 述 ：
 * ============================================================
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}