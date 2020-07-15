package com.example.sample_binance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.sample_binance.R
import com.example.sample_binance.model.api.ApiSymbol
import com.matt.libwrapper.exception.ParamsException
import com.matt.libwrapper.ui.base.LazyLoadBaseFragment
import kotlinx.android.synthetic.main.bin_fragment_bin_list.view.*
import kotlinx.android.synthetic.main.bin_item_fragment_bin_list.view.*

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/7/15 5:26 PM
 * 描 述 ：
 * ============================================================
 **/
class BinListFragment : LazyLoadBaseFragment() {
    companion object {
        const val KEY_LIST = "KEY_LIST"

        @JvmStatic
        fun newInstance(list: ArrayList<ApiSymbol>) =
            BinListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LIST, list)
                }
            }
    }

    lateinit var mList: List<ApiSymbol>

    val mBaseQuickAdapter by lazy {
        object :
            BaseQuickAdapter<ApiSymbol, BaseViewHolder>(R.layout.bin_item_fragment_bin_list, null) {
            override fun convert(holder: BaseViewHolder, item: ApiSymbol) {
                holder.itemView.run {
                    bifbl_tv_symbol.text = item.symbol
                }
            }

        }
    }

    override fun getBundleExtras(bundle: Bundle?) {
        super.getBundleExtras(bundle)
        val list = bundle?.getSerializable(KEY_LIST) ?: throw ParamsException("参数异常")
        mList = list as List<ApiSymbol>
    }

    override fun layoutId(): Int {
        return R.layout.bin_fragment_bin_list
    }

    override fun safeInitAll(rootView: View) {
        initAdapter()
        loadData()
    }

    private fun initAdapter() {
        mRootView.run {
            bfbl_rv_recycle.adapter = mBaseQuickAdapter
            bfbl_rv_recycle.layoutManager = LinearLayoutManager(mContext)
        }
    }

    private fun loadData() {
        mBaseQuickAdapter.setNewInstance(mList.toMutableList())
    }
}