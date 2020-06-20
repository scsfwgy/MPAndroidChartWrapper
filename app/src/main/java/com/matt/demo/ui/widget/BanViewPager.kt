package com.matt.demo.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * ============================================================
 * 作 者 :    wgyscsf@163.com
 * 创建日期 ：2020/6/20 5:10 PM
 * 描 述 ：
 * ============================================================
 */
class BanViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(
        context,
        attrs
    ) {
    private var isBanScroll = true

    fun setNoScroll(noScroll: Boolean) {
        isBanScroll = noScroll
    }

    override fun scrollTo(x: Int, y: Int) {
        super.scrollTo(x, y)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return if (isBanScroll) {
            false
        } else {
            super.onTouchEvent(motionEvent)
        }
    }

    override fun onInterceptTouchEvent(motionEvent: MotionEvent): Boolean {
        return if (isBanScroll) {
            false
        } else {
            super.onInterceptTouchEvent(motionEvent)
        }
    }
}