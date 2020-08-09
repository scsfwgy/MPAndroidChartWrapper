package com.matt.mpwrapper.view.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.github.mikephil.charting.data.CombinedData
import com.matt.mpwrapper.bean.KViewData
import com.matt.mpwrapper.view.charts.BaseCombinedChart
import com.matt.mpwrapper.view.delegate.IChartViewDelegate

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 11:39
 * 描 述 ：
 * ============================================================
 */
abstract class BaseKView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) :
    BaseCombinedChart(context, attributeSet, defStyle), IChartLoadData, IChartViewDelegate {

    protected val mDefMinCount = 40
    protected val mDefMaxCount = 100
    protected val mDefShowCount = 70

    lateinit var mBaseInit: BaseInit

    fun initBaseK(baseInit: BaseInit) {
        this.mBaseInit = baseInit
    }

    /**
     * 设置X轴放大系数
     */
    open fun showDefCount(size: Int) {
        var scale: Float = size / mDefShowCount.toFloat()
        if (scale < 1) {
            scale = 1f
        }
        //设置右边距离
        mXAxis.spaceMax = size * 0.15f / scale
        Log.d("BaseChart", "postScaleX: $size  scale= $scale")
        mViewPortHandler.zoom(scale, 1f, mViewPortHandler.matrixTouch)
    }


    /**
     * 设置最终数据
     */
    fun setKViewData(
        data: CombinedData,
        reload: Boolean = true,
        loadMore: Boolean = false
    ) {
        val allDataSize = mBaseInit.kViewDataList().size
        showDefCount(allDataSize)
        //调用系统方法
        setData(data)
        //设置最大和最小值
        setVisibleXRange(mDefMaxCount.toFloat(), mDefMinCount.toFloat())
        if (reload) {
            //移动到尾部
            moveViewToX(getData().entryCount.toFloat())
        }
    }

    /**
     * 重置数据
     */
    open fun resetALl() {
        getChartViewDelegate().mCombinedDataControl.resetAll()
        lineData?.dataSets?.clear()
        candleData?.dataSets?.clear()
        barData?.dataSets?.clear()
        scatterData?.dataSets?.clear()
        bubbleData?.dataSets?.clear()
        combinedData?.dataSets?.clear()
        updateAll()
    }

    override fun onLoading(loadingMsg: String?) {
        //一定要先清空数据
        resetALl()
        //只有mData=null,setNoDataText才会绘制
        mData = null
        setNoDataText(loadingMsg ?: "加载中...")
        invalidate()
    }

    override fun onLoadingFail(loadingFailMsg: String?) {
        setNoDataText(loadingFailMsg ?: "加载失败")
        invalidate()
    }

    override fun reLoadData(kViewDataList: List<KViewData>) {
        renderView(kViewDataList, reload = true, loadMore = false, pushData = false)
    }

    override fun loadMoreData(kViewDataList: List<KViewData>) {
        renderView(kViewDataList, reload = false, loadMore = true, pushData = false)
    }

    override fun pushData(kViewDataList: List<KViewData>) {
        renderView(kViewDataList, reload = false, loadMore = false, pushData = true)
    }

    /**
     * 真正的渲染逻辑
     */
    open fun renderView(
        kViewDataList: List<KViewData>,
        reload: Boolean,
        loadMore: Boolean,
        pushData: Boolean
    ) {
        kViewDataList.forEachIndexed { index, kViewData ->
            renderTemplateItemView(index, index, kViewData, reload, loadMore, pushData)
        }

        if (!pushData) {
            renderTemplateFinal(kViewDataList, reload, loadMore, pushData)

            //设置数据
            val combinedData = getChartViewDelegate().mCombinedData
            setKViewData(combinedData)
            //触发值未选择，进而触发未选择值对应的Legend
            mSelectionListener.onNothingSelected()
        } else {
            data?.notifyDataChanged()
            notifyDataSetChanged()
            moveViewToX(data?.entryCount?.toFloat() ?: 0f)
        }

    }

    /**
     * 渲染最后的动作
     */
    abstract fun renderTemplateFinal(
        kViewDataList: List<KViewData>,
        reload: Boolean,
        loadMore: Boolean,
        pushData: Boolean
    )

    /**
     * 具体渲染单项，子类去实现
     *
     * @param realIndex 真实的渲染item的下标
     * @param newDataIndex 当前数据集合的item的下标
     * @param it 当前item
     * @param reload 重新加载
     * @param loadMore 加载更多
     */
    abstract fun renderTemplateItemView(
        realIndex: Int,
        newDataIndex: Int,
        it: KViewData,
        reload: Boolean,
        loadMore: Boolean,
        pushData: Boolean
    )
}