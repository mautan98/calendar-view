package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import android.annotation.SuppressLint
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter
import com.namviet.vtvtravel.adapter.travelnews.NearByInTravelDetailAdapter.ClickItem
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentSearchDestinationResultBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f2.SmallLocationActivity
import com.namviet.vtvtravel.view.fragment.f2search.ResultSearchFragment
import kotlinx.android.synthetic.main.f2_fragment_search_destination.*
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class ResultDestinationSearchFragment(private var resultSearchFragment: ResultSearchFragment?) : BaseFragment<F2FragmentSearchDestinationResultBinding>() {
    private var subTravelNewsAdapter: NearByInTravelDetailAdapter? = null
    private var travels: ArrayList<Travel>? = ArrayList()
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_destination_result
    }

    override fun initView() {
    }

    public fun setList(travels: ArrayList<Travel>?, isLoadMore: Boolean) {
        if (isLoadMore) {
            travels?.let { this.travels?.addAll(it) }
        } else {
            this.travels?.clear()
            travels?.let { this.travels?.addAll(it) }
        }

        subTravelNewsAdapter?.notifyDataSetChanged()

    }

    override fun initData() {
        subTravelNewsAdapter = NearByInTravelDetailAdapter(mActivity, travels, object :ClickItem {
            override fun likeEvent(position: Int) {
            }

            override fun onClickItem(travel: Travel) {
                try {
                    SmallLocationActivity.startScreenDetail(context as Activity?, SmallLocationActivity.OpenType.DETAIL, travel.detail_link)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
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