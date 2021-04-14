package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import android.annotation.SuppressLint
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.travelnews.SubTravelNewsAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchNewsResultBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.view.f2.TravelNewsActivity
import com.namviet.vtvtravel.view.fragment.f2search.ResultSearchFragment
import kotlinx.android.synthetic.main.f2_fragment_search_destination.*
import java.util.*

@SuppressLint("ValidFragment")
class ResultNewsSearchFragment(private var resultSearchFragment: ResultSearchFragment?) : BaseFragment<F2FragmentSearchNewsResultBinding>() {

    constructor()

    private var subTravelNewsAdapter : SubTravelNewsAdapter? = null

    private var travels : ArrayList<Travel>?  = ArrayList();


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_news_result
    }

    override fun initView() {

    }

    public fun setList(travels : ArrayList<Travel>?, isLoadMore: Boolean ){
        if (isLoadMore) {
            travels?.let { this.travels?.addAll(it) }
        } else {
            this.travels?.clear()
            travels?.let { this.travels?.addAll(it) }
        }

        subTravelNewsAdapter?.notifyDataSetChanged()

    }

    override fun initData() {
        subTravelNewsAdapter = SubTravelNewsAdapter(mActivity, travels, SubTravelNewsAdapter.ClickItem {travel  ->
            try {
                TravelNewsActivity.openScreenDetail(context as Activity?, TravelNewsActivity.OpenType.DETAIL, travel?.detail_link)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        rclContent.adapter = subTravelNewsAdapter
    }

    override fun inject() {
    }

    override fun setClickListener() {
        rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    resultSearchFragment?.getMoreData()
                }
            }
        })

    }

    override fun setObserver() {
    }

}