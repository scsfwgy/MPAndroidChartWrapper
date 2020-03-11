package com.wgyscsf.demo.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wgyscsf.demo.R
import com.wgyscsf.demo.bean.ApiProduct
import com.wgyscsf.demo.net.ServiceWrapper
import com.wgyscsf.demo.net.base.SimpleTObserver
import com.wgyscsf.demo.utils.RxUtils
import kotlinx.android.synthetic.main.activity_symbol_list.*

class SymbolListActivity : BaseActivity() {

    companion object {
        fun goIntent(context: Context) {
            val intent = Intent(context, SymbolListActivity::class.java)
            context.startActivity(intent)
        }
    }

    val mDataList by lazy {
        ArrayList<ApiProduct>()
    }

    val mAdapter by lazy {
        SymbolListAdapter(mActivity, mDataList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symbol_list)
        initAdapter()
        initListener()
        asl_srl_refresh.isRefreshing = true
        loadData()
    }

    private fun loadData() {
        val map = HashMap<String, Any>()
        map["limit"] = 50
        map["offset"] = 0
        map["category"] = 1
        ServiceWrapper.productService.getProductList(map)
            .compose(RxUtils.rxObSchedulerHelper())
            .subscribe(object : SimpleTObserver<List<ApiProduct>>(mActivity) {
                override fun onSuccess(it: List<ApiProduct>) {
                    asl_srl_refresh.isRefreshing = false
                    mDataList.clear()
                    mDataList.addAll(it)
                    mAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun initAdapter() {
        asl_rv_recycle.layoutManager = LinearLayoutManager(mContext)
        asl_rv_recycle.adapter = mAdapter
    }

    private fun initListener() {
        asl_srl_refresh.setOnRefreshListener {
            loadData()
        }
    }
}
