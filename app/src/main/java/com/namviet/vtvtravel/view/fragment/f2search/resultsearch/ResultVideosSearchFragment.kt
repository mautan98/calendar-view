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
class ResultVideosSearchFragment : BaseFragment<F2FragmentSearchDestinationResultBinding> {
    private var subTravelNewsAdapter: SubVideoAdapter? = null
    private var travels: ArrayList<Video> = ArrayList()
    private var moreLink: String? = null
    private var resultSearchFragment: ResultSearchFragment? = null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_destination_result
    }

    constructor()

    constructor(resultSearchFragment: ResultSearchFragment?){
        this.resultSearchFragment = resultSearchFragment
    }

    override fun initView() {
    }

    public fun setList(travels: ArrayList<Video>?, moreLink: String?,  count: String, keyword: String, isApproximately: Boolean, isLoadMore : Boolean) {
        travels?.let {
            if (isLoadMore) {
                this.travels?.addAll(it)
            } else {
                this.travels?.clear()
                this.travels?.addAll(it)
            }
        }
        this.moreLink = moreLink
        subTravelNewsAdapter?.notifyDataSetChanged()
        if(!isApproximately) {
            tvCountResult.text = "Có $count kết quả tìm kiếm video khớp với \"$keyword\""
            resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
        }else{
            tvCountResult.text = "Có $count kết quả tìm kiếm video gần đúng khớp với \"$keyword\""
            resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
        }
    }

    override fun initData() {
        subTravelNewsAdapter = SubVideoAdapter(mActivity, travels, null)
        rclContent.adapter = subTravelNewsAdapter

        resultSearchFragment?.searchAllVideo(SearchType.VIDEO, false)
    }

    override fun inject() {
    }

    override fun setClickListener() {
        rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    resultSearchFragment?.searchAllVideoWithLink(moreLink, SearchType.VIDEO, true)
                }
            }
        })
    }

    override fun setObserver() {
    }


}