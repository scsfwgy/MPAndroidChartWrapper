package com.wgyscsf.mpwrapper.bean

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 12:26
 * 描 述 ：
 * ============================================================
 */
class Config {
    var greenUp = false

    companion object {
        fun def(): Config {
            val config = Config()
            config.greenUp = false
            return config
        }
    }
}