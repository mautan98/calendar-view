package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2video.SubVideoAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchDestinationResultBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.Video
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.view.fragment.f2search.ResultSearchFragment
import kotlinx.android.synthetic.main.f2_fragment_search_destination_result.*


@SuppressLint("ValidFragment")
class ResultVideosSearchFragment(private var resultSearchFragment: ResultSearchFragment?) : BaseFragment<F2FragmentSearchDestinationResultBinding>() {
    private var subTravelNewsAdapter: SubVideoAdapter? = null
    private var travels: ArrayList<Video> = ArrayList()
    private var moreLink: String? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_destination_result
    }

    override fun initView() {
    }

    public fun setList(travels: ArrayList<Video>?, moreLink: String?,  count: String, keyword: String) {
        travels?.let { this.travels?.addAll(travels) }
        this.moreLink = moreLink
        subTravelNewsAdapter?.notifyDataSetChanged()
        tvCountResult.text = "Có $count kết quả tìm kiếm video khớp với \"$keyword\""
        resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
    }

    override fun initData() {
        subTravelNewsAdapter = SubVideoAdapter(mActivity, travels, null)
        rclContent.adapter = subTravelNewsAdapter

        resultSearchFragment?.searchAllVideo(SearchType.VIDEO)
    }

    override fun inject() {
    }

    override fun setClickListener() {
        rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    resultSearchFragment?.searchAllVideoWithLink(moreLink, SearchType.VIDEO)
                }
            }
        })
    }

    override fun setObserver() {
    }


}