package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import android.app.Activity
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchDestinationResultBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.view.f2.SmallLocationActivity
import kotlinx.android.synthetic.main.f2_fragment_search_destination.*
import java.util.*

class ResultDestinationSearchFragment : BaseFragment<F2FragmentSearchDestinationResultBinding>() {
    private var subTravelNewsAdapter: NearByInTravelDetailAdapter? = null
    private var travels: List<Travel>? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_destination_result
    }

    override fun initView() {
    }

    public fun setList(travels : ArrayList<Travel>? ){
        this.travels = travels
        subTravelNewsAdapter = NearByInTravelDetailAdapter(mActivity, travels, NearByInTravelDetailAdapter.ClickItem {travel ->
            try {
                SmallLocationActivity.startScreenDetail(context as Activity?, SmallLocationActivity.OpenType.DETAIL, travel.detail_link)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        rclContent.adapter = subTravelNewsAdapter
    }

    override fun initData() {

    }

    override fun inject() {
    }

    override fun setClickListener() {
    }

    override fun setObserver() {
    }


}