package com.matt.mpwrapper.bean

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 12:26
 * 描 述 ：
 * ============================================================
 */
class Config {
    var debug = true
    var greenUp = false

    companion object {
        fun def(): Config {
            val config = Config()
            config.debug = true
            config.greenUp = false
            return config
        }
    }
}