package com.matt.mpwrapper.view

import com.matt.mpwrapper.bean.Config

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 11:40
 * 描 述 ：
 * ============================================================
 */
object MpWrapperConfig {
    var mConfig: Config = Config.def()
    fun init(config: Config) {
        mConfig = config
    }
}