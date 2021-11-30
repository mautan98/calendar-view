package com.namviet.vtvtravel.view.f3.deal.view.listdeal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentListDealTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealInDealHomeAdapter
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType
import com.namviet.vtvtravel.view.f3.deal.model.Block
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_deal_subcribe.*
import kotlinx.android.synthetic.main.fragment_list_deal_tab.*
import kotlinx.android.synthetic.main.fragment_list_deal_tab.layoutNoData
import kotlinx.android.synthetic.main.fragment_list_deal_tab.rclContent
import java.util.*

class ListDealTabFragment : BaseFragment<FragmentListDealTabBinding?>,  Observer {
    private var dealViewModel : DealViewModel? = null
    private var block : Block? = null
    private var isProcessing : String = "1"


    public fun setIsProcessing(isProcessing : String){
        this.isProcessing = isProcessing
    }
    constructor()

    constructor(block: Block?){
        this.block = block
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_deal_tab
    }

    override fun initView() {
        dealViewModel = DealViewModel();
        dealViewModel?.addObserver(this)
        dealViewModel?.getDealWithReplaceParam(block?.link, isProcessing)

        check.isChecked = isProcessing != IsProcessingType.DANG_DIEN_RA_TYPE
    }
    override fun initData() {

    }
    override fun inject() {}
    override fun setClickListener() {
        binding!!.check.setOnCheckedChangeListener { _, isChecked ->
            isProcessing = if (isChecked) {
                IsProcessingType.SAP_DIEN_RA_TYPE
            } else {
                IsProcessingType.DANG_DIEN_RA_TYPE
            }
            dealViewModel?.getDealWithReplaceParam(block?.link, isProcessing)
        }
    }
    override fun setObserver() {}

    override fun update(observable: Observable?, o: Any?) {
        if (observable is DealViewModel) {
            if (o is DealResponse) {
                var adapter = GridDealInDealHomeAdapter(o)
                adapter.setOnDataChangeListener(object : GridDealInDealHomeAdapter.OnDataChange{
                    override fun onDataChange(isShow: Boolean) {
                        if (isShow) {
                            layoutNoData.visibility = View.VISIBLE
                        } else {
                            layoutNoData.visibility = View.GONE
                        }
                    }

                })
                rclContent.adapter = adapter
                val gridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                rclContent.layoutManager = gridLayoutManager
            }
        }
    }
}