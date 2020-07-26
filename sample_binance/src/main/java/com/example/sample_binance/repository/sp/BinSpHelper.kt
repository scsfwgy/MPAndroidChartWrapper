package com.example.sample_binance.repository.sp

import com.blankj.utilcode.util.SPUtils
import com.example.sample_binance.model.kview.BinKType
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MinorIndicatorType

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/7/25 8:46 PM
 * 描 述 ：
 *
 **/
object BinSpHelper {
    const val SP_MAIN_INDICATOR = "SP_MAIN_INDICATOR"
    const val SP_MINOR_INDICATOR = "SP_MINOR_INDICATOR"
    const val SP_BIN_KTYPE = "SP_BIN_KTYPE"

    fun getMainIndicator(): MasterIndicatorType {
        val string =
            SPUtils.getInstance().getString(SP_MAIN_INDICATOR, null)
                ?: return MasterIndicatorType.NONE
        return MasterIndicatorType.valueOf(string)
    }

    fun updateMainIndicator(masterIndicatorType: MasterIndicatorType) {
        SPUtils.getInstance().put(SP_MAIN_INDICATOR, masterIndicatorType.name)
    }

    fun getMinorIndicator(): MinorIndicatorType {
        val string =
            SPUtils.getInstance().getString(SP_MINOR_INDICATOR, null)
                ?: return MinorIndicatorType.MACD
        return MinorIndicatorType.valueOf(string)
    }

    fun updateMinorIndicator(minorIndicatorType: MinorIndicatorType) {
        SPUtils.getInstance().put(SP_MINOR_INDICATOR, minorIndicatorType.name)
    }

    fun getBinKType(): BinKType {
        val string =
            SPUtils.getInstance().getString(SP_BIN_KTYPE, null) ?: return BinKType.K_1D
        return BinKType.valueOf(string)
    }

    fun updateBinKType(binKType: BinKType) {
        SPUtils.getInstance().put(SP_BIN_KTYPE, binKType.name)
    }
}