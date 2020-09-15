package com.matt.mpwrapper.view

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.matt.mpwrapper.R
import com.matt.mpwrapper.bean.KViewData
import com.matt.mpwrapper.bean.Price
import com.matt.mpwrapper.view.base.BaseInit
import com.matt.mpwrapper.view.base.IKLoadData
import com.matt.mpwrapper.view.listener.LinkChartListener
import com.matt.mpwrapper.view.type.MasterIndicatorType
import com.matt.mpwrapper.view.type.MasterViewType
import com.matt.mpwrapper.view.type.MinorIndicatorType
import com.matt.mpwrapper.view.type.VolIndicatorType
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
) : LinearLayout(mContext, attrs, defStyleAttr), IKLoadData, BaseInit {
    val TAG = KView::class.simpleName

    init {
        initAttr()
    }

    private fun initAttr() {
        LayoutInflater.from(mContext).inflate(R.layout.mp_widget_kview, this)

        Handler().post {
            //chart初始化和关联在一起
            mChartArr.forEach { baseKView ->
                baseKView.initBaseK(this)
                //开始关联
                baseKView.onChartGestureListener =
                    LinkChartListener(
                        baseKView,
                        mChartArr.filter { baseKView != it }.toTypedArray()
                    )
            }
        }
    }

    fun getMasterView(): MasterView {
        return mwk_mv_master
    }

    fun getVolView(): VolView {
        return mwk_mv_vol
    }

    fun getMinorView(): MinorView {
        return mwk_mv_minor
    }

    /**
     * 全局单例数据集合，不允许重新指向、不允许设置为null。所有数据操作都会基于这个list.
     */
    val mKViewDataList: MutableList<KViewData> by lazy {
        ArrayList<KViewData>()
    }

    var mYDataDigit: Int = 0
    lateinit var mVolIndicatorType: VolIndicatorType


    val mChartArr by lazy {
        if (mVolIndicatorType == VolIndicatorType.GONE) {
            arrayOf(getMasterView(), getMinorView())
        } else {
            arrayOf(getMasterView(), getMinorView(), getVolView())
        }
    }

    override fun updateConfig(
        yDataDigit: Int,
        masterViewType: MasterViewType,
        masterIndicatorType: MasterIndicatorType,
        minorIndicatorType: MinorIndicatorType,
        volIndicatorType: VolIndicatorType
    ) {
        mYDataDigit = yDataDigit
        mVolIndicatorType = volIndicatorType
        //chart初始化和关联在一起
        mChartArr.forEach { baseKView ->
            if (baseKView is MasterView) {
                baseKView.mMasterViewDelegate.run {
                    mMasterViewType = masterViewType
                    mMasterIndicatorType = masterIndicatorType
                }
            }
            if (baseKView is MinorView) {
                baseKView.mMinorViewDelegate.run {
                    mMinorIndicatorType = minorIndicatorType
                }
            }
            if (baseKView is VolView) {
                baseKView.mVolViewDelegate.run {
                    mVolIndicatorType = volIndicatorType
                }
            }
        }

        if (!volEnable()) {
            getVolView().visibility = View.GONE
        }

    }

    override fun onLoading(loadingMsg: String?) {
        mChartArr.forEach { it.onLoading(loadingMsg) }

    }

    override fun onLoadingFail(loadingFailMsg: String?) {
        mChartArr.forEach { it.onLoadingFail(loadingFailMsg) }
    }

    override fun loadData(
        priceList: List<Price>,
        volList: List<Float>?,
        reload: Boolean,
        append: Boolean,
        loadMore: Boolean
    ) {
        if (reload) {
            mKViewDataList.clear()
        }
        val processNewData = processNewData(priceList, volList, reload, append, loadMore)
        mChartArr.forEach {
            it.loadData(processNewData, reload, append, loadMore)
        }
    }

    fun volEnable(): Boolean {
        return mVolIndicatorType != VolIndicatorType.GONE
    }

    /**
     * 最新价格不会影响指标，是闭市价影响。
     * todo:vol会影响量图的ma指标，暂时显先不处理。
     */
    override fun refreshData(latestTime: Long, latestPrice: Float, latestVol: Float?) {
        val lastOrNull = mKViewDataList.lastOrNull()
        if (lastOrNull == null) {
            Log.e(TAG, "refreshData:获取最后一个值为null,终止后续逻辑")
            return
        }
        lastOrNull.run {
            val p = price ?: throw IllegalArgumentException("获取到的最后一个模型的price为null,请检查")
            val newT = if (latestTime > p.t) latestTime else p.t
            val newO = p.o
            val newH = if (latestPrice > p.h) latestPrice else p.h
            val newL = if (latestPrice < p.l) latestPrice else p.l
            val newC = latestPrice
            val newPrice = Price(newT, newO, newH, newL, newC)

            //赋值回
            price = newPrice
            volData?.vol?.vol = latestVol ?: 0f
        }
        mChartArr.forEach {
            lastOrNull.run {
                it.refreshData(lastOrNull)
            }
        }
    }

    override fun kViewDataList(): MutableList<KViewData> {
        return mKViewDataList
    }

    override fun digit(): Int {
        return mYDataDigit
    }

    private fun processNewData(
        priceList: List<Price>,
        volList: List<Float>?,
        reload: Boolean,
        append: Boolean,
        loadMore: Boolean
    ): List<KViewData> {
        if (reload) {
            mKViewDataList.clear()
        }
        val simpleDataList2KViewDataList =
            FinancialAlgorithmConvert.simpleDataList2KViewDataList(priceList, volList)
        if (loadMore) {
            mKViewDataList.addAll(0, simpleDataList2KViewDataList)
        } else {
            mKViewDataList.addAll(simpleDataList2KViewDataList)
        }
        if (volList == null) {
            getVolView().visibility = View.GONE
        } else {
            getVolView().visibility = View.VISIBLE
        }
        return simpleDataList2KViewDataList
    }

}