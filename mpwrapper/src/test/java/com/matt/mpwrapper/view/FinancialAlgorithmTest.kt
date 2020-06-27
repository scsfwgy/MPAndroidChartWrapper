package com.matt.mpwrapper.view

import org.junit.Test

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/6/26 5:13 PM
 * 描 述 ：
 *
 **/
class FinancialAlgorithmTest {
    @Test
    fun calculateMATest() {
        val list = listOf(1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f)
        val calculateMA = FinancialAlgorithm.calculateMA(list, 5)
        println(calculateMA)

        val calculateBOLL = FinancialAlgorithm.calculateBOLL(list)
        println(calculateBOLL)
    }
}