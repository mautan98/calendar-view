package com.namviet.vtvtravel.view.f3.deal.view.listdeal

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentListDealTabBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealAdapter
import com.namviet.vtvtravel.view.f3.deal.adapter.GridDealInDealHomeAdapter
import com.namviet.vtvtravel.view.f3.deal.model.Block
import com.namviet.vtvtravel.view.f3.deal.model.deal.DealResponse
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_list_deal_tab.*
import java.util.*

class ListDealTabFragment : BaseFragment<FragmentListDealTabBinding?>,  Observer {
    private var dealViewModel : DealViewModel? = null
    private var block : Block? = null
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
        dealViewModel?.getDeal(block?.link)
    }
    override fun initData() {

    }
    override fun inject() {}
    override fun setClickListener() {
    }
    override fun setObserver() {}

    override fun update(observable: Observable?, o: Any?) {
        if (observable is DealViewModel) {
            if (o is DealResponse) {
                rclContent.adapter = GridDealInDealHomeAdapter(o)
                val gridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                rclContent.layoutManager = gridLayoutManager
            }
        }
    }
}