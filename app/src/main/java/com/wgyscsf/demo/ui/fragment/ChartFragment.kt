package com.wgyscsf.demo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import com.wgyscsf.demo.R
import com.wgyscsf.demo.ui.base.LazyLoadBaseFragment
import com.wgyscsf.demo.vm.ChartViewModel
import com.wgyscsf.mpwrapper.view.type.KType

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/16 11:44
 * 描 述 ：
 * ============================================================
 */
class ChartFragment : LazyLoadBaseFragment() {
    companion object {
        const val KEY_KTYPE = "KEY_KTYPE"
        fun newInstance(kType: KType): ChartFragment {
            return ChartFragment().apply {
                Bundle().apply {
                    putSerializable(KEY_KTYPE, kType)
                }
            }
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_chart
    }

    val mCharViewMode by lazy {
        getVM(ChartViewModel::class.java)
    }


    override fun safeInitAll(rootView: View) {
        Log.d(TAG, "symbol:${mCharViewMode.mSymbol}")
    }
}