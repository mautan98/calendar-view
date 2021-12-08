package com.namviet.vtvtravel.view.f3.deal.view.listhotdeal

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentListDealTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealInDealHomeAdapter
import com.namviet.vtvtravel.view.f3.deal.model.Block
import com.namviet.vtvtravel.view.f3.deal.model.deal.Content
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_list_deal_tab.*
import java.util.*
import kotlin.collections.ArrayList

class ListHotDealTabFragment : BaseFragment<FragmentListDealTabBinding?>, Observer {
    private var dealViewModel : DealViewModel? = null
    private var block : Block? = null
    private var listDeal = ArrayList<Content>()
    private var adapter: GridDealInDealHomeAdapter? = null
    private var isLoadMore = false;
    private var page = 0;
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

        isLoadMore = false;
        dealViewModel?.getHotDealWithReplaceParam(block?.link, page)
    }
    override fun initData() {
        layoutCheckbox.visibility = View.GONE



        adapter = GridDealInDealHomeAdapter(listDeal)
        adapter?.setOnDataChangeListener(object : GridDealInDealHomeAdapter.OnDataChange{
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
                    dealViewModel?.getHotDealWithReplaceParam(block?.link, page)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }
    override fun inject() {}
    override fun setClickListener() {
    }
    override fun setObserver() {}

    override fun update(observable: Observable?, o: Any?) {
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