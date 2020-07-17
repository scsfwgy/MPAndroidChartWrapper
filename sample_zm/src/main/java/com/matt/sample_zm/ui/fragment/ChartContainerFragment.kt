package com.matt.sample_zm.ui.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.matt.sample_zm.vm.ChartViewModel
import com.matt.libwrapper.ui.base.LazyLoadBaseFragment
import com.matt.mpwrapper.view.type.KType
import com.matt.sample_zm.R
import kotlinx.android.synthetic.main.zm_fragment_chart_container.view.*

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/16 11:44
 * 描 述 ：
 * ============================================================
 */
class ChartContainerFragment : LazyLoadBaseFragment() {
    companion object {
        fun newInstance(): ChartContainerFragment {
            return ChartContainerFragment()
        }
    }

    override fun layoutId(): Int {
        return R.layout.zm_fragment_chart_container
    }

    val mCharViewMode by lazy {
        getVMByActivity(ChartViewModel::class.java)
    }

    override fun safeInitAll(rootView: View) {
        Log.d(TAG, "symbol:${mCharViewMode.mSymbol}")
        initView()
    }

    private fun initView() {
        val kTypeList = KType.getKTypeList()
        val titles = Array<String>(kTypeList.size) {
            kTypeList[it].getmLable()
        }
        val fragments = ArrayList<Fragment>()
        kTypeList.forEach {
            fragments.add(ChartFragment.newInstance(it, mCharViewMode.mSymbol))
        }
        mRootView.run {
            fcc_stl_tab.setViewPager(
                fcc_fl_viewPager,
                titles,
                mBaseActivity,
                fragments
            )
        }
    }
}