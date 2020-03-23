package com.wgyscsf.mpwrapper.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.wgyscsf.mpwrapper.R
import com.wgyscsf.mpwrapper.ktx.getColor
import com.wgyscsf.mpwrapper.ktx.getDrawable
import com.wgyscsf.mpwrapper.view.base.BaseKView
import com.wgyscsf.mpwrapper.view.delegate.MasterViewDelegate
import com.wgyscsf.mpwrapper.view.type.MasterIndicatrixType
import com.wgyscsf.mpwrapper.view.type.MasterViewType

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 12:22
 * 描 述 ：
 * ============================================================
 */

class MasterView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    BaseKView(context, attributeSet, defStyle) {
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

    val mMasterTimeSharingColor by lazy {
        getColor(R.color.mp_basekview_timesharing)
    }
    val mMasterTimeSharingFillDrawable: Drawable by lazy {
        getDrawable(R.drawable.shape_gradient_filled)
    }

    val mMasterViewDelegate by lazy {
        MasterViewDelegate(this)
    }

    var mMasterViewType: MasterViewType = MasterViewType.CANDLE
    var mMasterIndicatrixType: MasterIndicatrixType = MasterIndicatrixType.NONE


}