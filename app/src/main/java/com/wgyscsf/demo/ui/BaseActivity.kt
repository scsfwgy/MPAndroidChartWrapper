package com.wgyscsf.demo.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/07 10:32
 * 描 述 ：
 * ============================================================
 */
abstract class BaseActivity : AppCompatActivity() {

    val TAG: String by lazy {
        javaClass::class.java.simpleName
    }

    val mContext: Context by lazy {
        this
    }
    val mActivity: Context by lazy {
        this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}