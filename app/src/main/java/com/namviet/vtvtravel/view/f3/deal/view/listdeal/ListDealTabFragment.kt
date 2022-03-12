package com.namviet.vtvtravel.view.f3.deal.view.listdeal

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentListDealTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealInDealHomeAdapter
import com.namviet.vtvtravel.view.f3.deal.constant.IsProcessingType
import com.namviet.vtvtravel.view.f3.deal.model.Block
import com.namviet.vtvtravel.view.f3.deal.model.deal.Content
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_deal_subcribe.*
import kotlinx.android.synthetic.main.fragment_list_deal_tab.*
import kotlinx.android.synthetic.main.fragment_list_deal_tab.layoutNoData
import kotlinx.android.synthetic.main.fragment_list_deal_tab.rclContent
import java.util.*
import kotlin.collections.ArrayList

class ListDealTabFragment : BaseFragment<FragmentListDealTabBinding?>, Observer {
    private var dealViewModel: DealViewModel? = null
    private var block: Block? = null
    private var isProcessing: String = "1"
    private var listDeal = ArrayList<Content>()
    private var adapter: GridDealInDealHomeAdapter? = null
    private var isLoadMore = false;

    private var page = 0;


    public fun setIsProcessing(isProcessing: String) {
        this.isProcessing = isProcessing
    }

    constructor()

    constructor(block: Block?) {
        this.block = block
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_deal_tab
    }

    override fun initView() {
        dealViewModel = DealViewModel();
        dealViewModel?.addObserver(this)

        isLoadMore = false;
        dealViewModel?.getDealWithReplaceParam(block?.link, isProcessing, page)

        check.isChecked = isProcessing != IsProcessingType.DANG_DIEN_RA_TYPE
    }

    override fun initData() {
        adapter = GridDealInDealHomeAdapter(listDeal)
        adapter?.setOnDataChangeListener(object : GridDealInDealHomeAdapter.OnDataChange {
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

        rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)) {
                    isLoadMore = true;
                    dealViewModel?.getDealWithReplaceParam(block?.link, isProcessing, page)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun inject() {}
    override fun setClickListener() {
        binding!!.check.setOnCheckedChangeListener { _, isChecked ->
            isProcessing = if (isChecked) {
                IsProcessingType.SAP_DIEN_RA_TYPE
            } else {
                IsProcessingType.DANG_DIEN_RA_TYPE
            }
            page = 0;
            isLoadMore = false;
            dealViewModel?.getDealWithReplaceParam(block?.link, isProcessing, page)
        }
    }

    override fun setObserver() {}

    override fun update(observable: Observable?, o: Any?) {
        binding?.shimmerViewContainer?.visibility = View.GONE
        if (observable is DealViewModel) {
            if (o is DealResponse) {
                if(isLoadMore){
                    listDeal.addAll(o.data.content)
                    adapter?.notifyDataSetChanged()
                }else {
                    listDeal.clear()
                    listDeal.addAll(o.data.content)
                    adapter?.notifyDataSetChanged()
                }
                page += 1
            }
        }
    }
}