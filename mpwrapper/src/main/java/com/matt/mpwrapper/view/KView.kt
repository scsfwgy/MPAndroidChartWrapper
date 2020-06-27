package com.matt.mpwrapper.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.KViewData
import com.matt.mpwrapper.bean.Ma
import com.matt.mpwrapper.bean.MasterData
import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.base.BaseInit
import com.matt.mpwrapper.view.base.LoadData
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MasterViewType
import kotlinx.android.synthetic.main.mp_widget_kview.view.*

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/20 10:33
 * 描 述 ：
 * ============================================================
 */
class KView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(mContext, attrs, defStyleAttr), LoadData, BaseInit {

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

    var mYDataDigit: Int = 0

    fun initKView(
        yDataDigit: Int = 4,
        masterViewType: MasterViewType = MasterViewType.CANDLE,
        masterIndicatorType: MasterIndicatorType = MasterIndicatorType.MA
    ) {
        mYDataDigit = yDataDigit
        val masterView = getMasterView()
        val minorView = getMinorView()
        masterView.initBaseK(this)
        minorView.initBaseK(this)
        val masterViewDelegate = masterView.mMasterViewDelegate
        masterViewDelegate.mMasterViewType = masterViewType
        masterViewDelegate.mMasterIndicatorType = masterIndicatorType
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
        val map = priceList.map { it.c }
        val calculateMA5 = FinancialAlgorithm.calculateMA(map, 5)
        val calculateMA10 = FinancialAlgorithm.calculateMA(map, 10)
        val calculateMA20 = FinancialAlgorithm.calculateMA(map, 20)
        val calculateBOLL = FinancialAlgorithm.calculateBOLL(map)
        val kViewDataList = mKViewDataList
        priceList.forEachIndexed { index, it ->
            val ma5 = calculateMA5[index]
            val ma10 = calculateMA10[index]
            val ma20 = calculateMA20[index]
            val boll = calculateBOLL[index]
            val kViewData = KViewData()
            kViewData.price = it
            val masterData = MasterData()
            masterData.ma = Ma(ma5, ma10, ma20)
            masterData.boll = boll
            kViewData.masterData = masterData
            kViewDataList.add(kViewData)
        }
        val masterView = getMasterView()
        masterView.renderView()
    }

    override fun kViewData(): MutableList<KViewData> {
        return mKViewDataList
    }

    override fun digit(): Int {
        return mYDataDigit
    }
}