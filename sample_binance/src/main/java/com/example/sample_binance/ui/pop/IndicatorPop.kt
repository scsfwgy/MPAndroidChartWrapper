package com.example.sample_binance.ui.pop

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sample_binance.R
import com.matt.libwrapper.ui.base.BasePopWindow
import com.matt.mpwrapper.ktx.getColor
import com.matt.mpwrapper.view.type.BinKType
import kotlinx.android.synthetic.main.bin_item_pop_kview_type.view.*
import kotlinx.android.synthetic.main.bin_pop_kview_type.view.*

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/7/19 11:13 AM
 * 描 述 ：
 *
 **/
class IndicatorPop(context: Context) :
    BasePopWindow(context) {
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.bin_pop_indicator)
    }

    init {

        initListener()
    }

    private fun initListener() {

    }

}