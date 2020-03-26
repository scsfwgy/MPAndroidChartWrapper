package com.wgyscsf.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.wgyscsf.mpwrapper.R
import com.wgyscsf.mpwrapper.bean.KViewData
import com.wgyscsf.mpwrapper.bean.MasterData
import com.wgyscsf.mpwrapper.bean.Price
import com.wgyscsf.mpwrapper.view.base.LoadData
import kotlinx.android.synthetic.main.mp_widget_kview.view.*

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/20 10:33
 * 描 述 ：
 * ============================================================
 */
class KView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(mContext, attrs, defStyleAttr), LoadData {

    init {
        initAttr()
    }

    private fun initAttr() {
        LayoutInflater.from(mContext).inflate(R.layout.mp_widget_kview, this)
    }

    fun getMasterView(): MasterView {
        return mwk_mv_master
    }

    fun getMinorView(): MinorView {
        return mwk_mv_minor
    }

    /**
     * 全局单例数据集合，不允许重新指向、不允许设置为null。所以数据操作都会基于这个list.
     */
    val mKViewDataList: MutableList<KViewData> by lazy {
        ArrayList<KViewData>()
    }

    override fun reLoadData(priceList: List<Price>) {
        mKViewDataList.clear()
        collectData(priceList)

    }

    override fun loadMoreData(priceList: List<Price>) {
        collectData(priceList)
    }

    override fun pushData(price: Price) {
        collectData(listOf(price))
    }

    private fun collectData(priceList: List<Price>) {
        val masterView = getMasterView()
        val masterDataList = masterView.mMasterDataList
        priceList.forEach {
            //val kViewData = KViewData()
            val masterData = MasterData()
            //kViewData.masterData = masterData
            masterData.price = it
            masterDataList.add(masterData)
        }
        masterView.renderView()
    }
}