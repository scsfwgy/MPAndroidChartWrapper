package com.wgyscsf.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import com.wgyscsf.mpwrapper.R
import com.wgyscsf.mpwrapper.ktx.getColor
import com.wgyscsf.mpwrapper.view.base.BaseKView

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 12:22
 * 描 述 ：
 * ============================================================
 */
class MasterView(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : BaseKView(context, attributeSet, defStyle) {
    //ma
    val mMasterViewMa5Color by lazy {
        getColor(R.color.mp_masterview_ma5)
    }
    val mMasterViewMa10Color by lazy {
        getColor(R.color.mp_masterview_ma10)
    }
    val mMasterViewMa20Color by lazy {
        getColor(R.color.mp_masterview_ma20)

    }
    //boll
    val mMasterViewBollUpColor by lazy {
        getColor(R.color.mp_masterview_bollup)

    }
    val mMasterViewBollMdColor by lazy {
        getColor(R.color.mp_masterview_bollmb)

    }
    val mMasterViewBollDnColor by lazy {
        getColor(R.color.mp_masterview_bolldn)

    }
}