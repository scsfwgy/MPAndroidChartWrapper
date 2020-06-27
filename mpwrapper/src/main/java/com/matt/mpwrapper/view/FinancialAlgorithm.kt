package com.matt.mpwrapper.view

import android.util.Log
import com.matt.mpwrapper.bean.Boll
import com.matt.mpwrapper.bean.Macd
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
        if (period <= 0 || calculateSize <= 0) throw IllegalArgumentException("参数不合法：period <= 0 || calculateSize <= 0")
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
        if (period <= 0 || calculateSize <= 0) throw IllegalArgumentException("参数不合法：period <= 0 || calculateSize <= 0")
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

    /**
     * MACD(x,y,z)，一般取MACD(12,26,9)。
     * MACD(x,y,z)，x、y为平滑指数。z暂时不知道用处（不影响算法）。
     * `EMAx=((x-1)/(x+1.0)*前一日EMA)+2.0/(x+1)*今日收盘价`;其中第一日的EMA是当日的收盘价。
     * `EMA12=(11/13.0)*前一日EMA12+2.0/13*今日收盘价`
     * `EMA26=(25/27.0)*前一日EMA26+2.0/27*今日收盘价`
     * DIF:`DIF=EMA12-EMA26`
     * DEA:`DEA=8/10.0*(前一日的DEA)+2/10.0*今日DIF`
     * MACD:`2*(DIF-DEA)`
     *
     * @param dataList 数据集合
     * @param d1         平滑指数，一般为12
     * @param d2         平滑指数，一般为26
     * @param z          暂时未知
     */
    fun calculateMACD(
        dataList: List<Float>,
        d1: Int = 12, d2: Int = 26, z: Int = 2,
        calculateSize: Int = dataList.size
    ): List<Macd> {
        if (d1 <= 0 || d2 <= 0 || calculateSize <= 0) throw IllegalArgumentException("d1 <= 0 || d2 <= 0 || calculateSize <= 0")
        val list = ArrayList<Macd>(calculateSize)
        //eam12、ema26、dea
        var triple: Triple<Float, Float, Float> = Triple(0f, 0f, 0f)
        dataList.forEachIndexed { index, it ->
            triple = if (index == 0) {
                Triple(it, it, 0f)
            } else {
                val ema12 = (d1 - 1f) / (d1 + 1f) * triple.first + 2f / (d1 + 1) * it
                val ema26 = (d2 - 1f) / (d2 + 1f) * triple.second + 2f / (d2 + 1) * it
                val dif = ema12 - ema26
                val dea = triple.third * 8f / 10f + dif * 2 / 10f
                Triple(ema12, ema26, dea)
            }
            val dif = triple.first - triple.second
            val dea = triple.third
            val macd = 2 * (dif - dea)
            val macdModel = Macd(dif, dea, macd)
            list.add(macdModel)
        }
        return list
    }

    private fun debug(msg: String) {
        if (isDebug) {
            Log.d(TAG, msg)
        }
    }
}
