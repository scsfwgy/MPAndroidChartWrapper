package com.matt.mpwrapper.ktx

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * ============================================================
 * 作 者 :    matt
 * 更新时间 ：2020/03/07 11:31
 * 描 述 ：
 * ============================================================
 */

fun View.getColor(@ColorRes colorId: Int): Int {
    return ContextCompat.getColor(context, colorId)
}

fun View.getDrawable(@DrawableRes drawableId: Int): Drawable {
    return ContextCompat.getDrawable(context, drawableId)
        ?: throw IllegalArgumentException("getDrawable cannnot not null")
}


