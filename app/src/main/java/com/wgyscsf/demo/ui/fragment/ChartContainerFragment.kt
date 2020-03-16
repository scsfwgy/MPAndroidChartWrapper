package com.wgyscsf.demo.ui.fragment

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.wgyscsf.demo.R
import com.wgyscsf.demo.ui.base.LazyLoadBaseFragment
import com.wgyscsf.demo.vm.ChartViewModel
import com.wgyscsf.mpwrapper.view.type.KType
import kotlinx.android.synthetic.main.fragment_chart_container.view.*

/**
 * ============================================================
 * 作 者 :    wgyscsf
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
        return R.layout.fragment_chart_container
    }

    val mCharViewMode by lazy {
        getVM(ChartViewModel::class.java)
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
            fragments.add(ChartFragment.newInstance(it))
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