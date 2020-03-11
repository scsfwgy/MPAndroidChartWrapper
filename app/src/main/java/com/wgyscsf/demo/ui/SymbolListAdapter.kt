package com.wgyscsf.demo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wgyscsf.demo.R
import com.wgyscsf.demo.bean.ApiProduct
import kotlinx.android.synthetic.main.item_activity_symbol_list.view.*


/**
 * ============================================================
 * 作 者 :    wgyscsf
 * 更新时间 ：2020/03/11 09:54
 * 描 述 ：
 * ============================================================
 */
class SymbolListAdapter(baseActivity: BaseActivity, list: List<ApiProduct>) :
    RecyclerView.Adapter<SymbolListVH>() {


    val mDataList = list
    val mBaseActivity = baseActivity

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymbolListVH {
        val view: View =
            LayoutInflater.from(mBaseActivity)
                .inflate(R.layout.item_activity_symbol_list, parent, false)
        return SymbolListVH(view)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: SymbolListVH, position: Int) {
        val apiProduct = mDataList[position]
        holder.run {
            cnNameView.text = apiProduct.cnName
            enNameView.text = apiProduct.enName
            currPriceView.text = apiProduct.currentPriceFormat
        }

    }
}

class SymbolListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cnNameView by lazy {
        itemView.iasl_tv_cnName
    }
    val enNameView by lazy {
        itemView.iasl_tv_enName
    }
    val currPriceView by lazy {
        itemView.iasl_tv_currPrice
    }
}
