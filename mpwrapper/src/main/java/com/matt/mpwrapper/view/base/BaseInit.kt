package com.matt.mpwrapper.view.base

import com.matt.mpwrapper.bean.KViewData

/**
 *
 * Author : wgyscsf@163.com
 * Github : https://github.com/scsfwgy
 * Date   : 2020/6/21 11:28 AM
 * 描 述 ：
 *
 **/
interface BaseInit {
    fun kViewData(): MutableList<KViewData>
    fun digit(): Int
}