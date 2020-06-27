package com.matt.mpwrapper.view

import android.util.Log
import com.matt.mpwrapper.bean.Boll
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2017/12/18 11:24
 * 描 述 ：所有金融相关算法全部在这里。算法参考【资料】包中的：常用股票指标计算公式及简单应用.pdf
 * 更新日期：2020/06/26 18:08
 * 描述：在原来基础上改用kt实现，并且移除了无用的干扰参数（比如：传参只需要核心参数闭市价即可，不再需要整个模型），保证更加灵活。
 * ============================================================
 */
// TODO: 2017/12/18 这里的算法需要核实！包括异常情况的处理和边界的处理是否合适。
object FinancialAlgorithm {
    var isDebug: Boolean = true
    val TAG = FinancialAlgorithm::class.java.simpleName
    val invalidData = Float.MIN_VALUE


    /**
     * 【该算法已核实】计算公式：MA =(C1+C2+C3+C4+C5+...+Cn)/n,其中C为收盘价n为移动平均周期数。
     * 例如现货黄金的5日移动平均价格计算方法为：MA5=(前四天收盘价+前三天收盘价+前天收盘价+昨天收盘价+今天收盘价)/5。
     * 特殊的，假如数据集合中最开始的n个数据，是没法计算MAn的。这里的处理方式是不计算，绘制时直接不绘制对应MA即可。
     *
     * 第一个参数就是原始数据集合，第二个参数就是指标集合，这里一轮把集合指标全部计算出来，避免多次轮训。
     *
     * @param dataList 数据集合，对于价格集合，就是收盘价列表
     * @param periodList     MAn中的n列表,周期，一般是：5、10、20、30、60。
     * @param calculateSize 从后向前数，计算几个原始数据的指标，默认计算所有。需求：要是只计算最新推送的价格，其实只计算最后一个即可。
     */
    fun calculateMA(
        dataList: List<Float>,
        period: Int,
        calculateSize: Int = dataList.size
    ): List<Float> {
        if (period <= 0) throw IllegalArgumentException("period不允许小于等于0")
        val list = ArrayList<Float>(calculateSize)
        var sum = 0f
        dataList.forEachIndexed { index, it ->
            sum += it
            //相对于当前索引，只需要计算sum=index-period+1..index
            val dis = index - period
            if (dis >= 0) {
                //减去集合的最后面的值，因为已经不需要了
                val data = dataList[dis]
                sum -= data
            }
            if (index < period - 1) {
                list.add(invalidData)
            } else {
                list.add(sum / period)
            }
        }
        debug("ma列表：$list")
        return list
    }

    /**
     * 【该算法已核实】BOLL(n)计算公式：
     * MA=n日内的收盘价之和÷n。
     * MD=n日的平方根（C－MA）的两次方之和除以n
     * MB=（n－1）日的MA
     * UP=MB+k×MD
     * DN=MB－k×MD
     * K为参数，可根据股票的特性来做相应的调整，一般默认为2
     *
     * @param dataList 数据集合
     * @param period     周期，一般为26
     * @param k          参数，可根据股票的特性来做相应的调整，一般默认为2
     */
    fun calculateBOLL(
        dataList: List<Float>,
        period: Int = 26,
        k: Int = 2,
        calculateSize: Int = dataList.size
    ): List<Boll> {
        if (period <= 0) throw IllegalArgumentException("period不允许小于等于0")
        val list = ArrayList<Boll>(calculateSize)
        var sum1 = 0f
        var sum2 = 0f
        dataList.forEachIndexed { index, data ->
            sum1 += data
            sum2 += data
            val dis1 = index - period
            if (dis1 >= 0) {
                sum1 -= dataList[dis1]
            }
            //4,5,1=-1
            val dis2 = index - period + 1
            if (dis2 >= 0) {
                sum2 -= dataList[dis2]
            }
            val i = period - 1
            if (index < i) {
                list.add(Boll(invalidData, invalidData, invalidData))
            } else {
                val ma = sum1 / period
                val mb = sum2 / (period - 1)
                var md = 0f
                val begin = i + 1 - period
                for (m in begin..i) {
                    val item = dataList[m]
                    md += (item - ma).toDouble().pow(2.0).toFloat()
                }
                md = sqrt(md / period)
                val up = mb + k * md
                val dn = mb - k * md
                list.add(Boll(up, mb, dn))
            }
        }
        debug("boll:$list")
        return list
    }

    private fun debug(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }
}
