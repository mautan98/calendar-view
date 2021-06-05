package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import android.annotation.SuppressLint
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.travelnews.SubTravelNewsAdapter
import com.namviet.vtvtravel.databinding.F2FragmentSearchNewsResultBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Travel
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.view.f2.TravelNewsActivity
import com.namviet.vtvtravel.view.fragment.f2search.ResultSearchFragment
import kotlinx.android.synthetic.main.f2_fragment_search_news_result.*
import java.util.*

@SuppressLint("ValidFragment")
class ResultNewsSearchFragment(private var resultSearchFragment: ResultSearchFragment?) : BaseFragment<F2FragmentSearchNewsResultBinding>() {

    private var subTravelNewsAdapter: SubTravelNewsAdapter? = null

    private var travels: ArrayList<Travel>? = ArrayList();

    private var moreLink: String? = null


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_news_result
    }

    override fun initView() {

    }

    public fun setList(travels: ArrayList<Travel>?, moreLink: String, count: String, keyword: String, isApproximately: Boolean) {
        travels?.let { this.travels?.addAll(it) }
        this.moreLink = moreLink
        subTravelNewsAdapter?.notifyDataSetChanged()
        if(!isApproximately) {
            tvCountResult.text = "Có $count kết quả tìm kiếm tin tức khớp với \"$keyword\""
            resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
        }else{
            tvCountResult.text = "Có $count kết quả tìm kiếm tin tức gần đúng khớp với \"$keyword\""
            resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
        }

    }

    override fun initData() {
        subTravelNewsAdapter = SubTravelNewsAdapter(mActivity, travels, SubTravelNewsAdapter.ClickItem { travel ->
            try {
                TravelNewsActivity.openScreenDetail(context as Activity?, TravelNewsActivity.OpenType.DETAIL, travel?.detail_link)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        rclContent.adapter = subTravelNewsAdapter

        resultSearchFragment?.searchAll(SearchType.NEWS)
    }

    override fun inject() {
    }

    override fun setClickListener() {
        rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    resultSearchFragment?.searchAllWithLink(moreLink, SearchType.NEWS)
                }
            }
        })

    }

    override fun setObserver() {
    }

}