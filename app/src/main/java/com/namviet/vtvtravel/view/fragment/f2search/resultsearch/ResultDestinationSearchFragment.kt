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
import com.namviet.vtvtravel.response.f2searchmain.result.SearchType
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f2.SmallLocationActivity
import com.namviet.vtvtravel.view.fragment.f2search.ResultSearchFragment
import kotlinx.android.synthetic.main.f2_fragment_search_destination_result.*
import kotlinx.android.synthetic.main.f2_fragment_search_destination_result.rclContent
import kotlinx.android.synthetic.main.f2_fragment_search_destination_result.tvCountResult
import kotlin.collections.ArrayList

@SuppressLint("ValidFragment")
class ResultDestinationSearchFragment : BaseFragment<F2FragmentSearchDestinationResultBinding> {
    private var subTravelNewsAdapter: NearByInTravelDetailAdapter? = null
    private var travels: ArrayList<Travel>? = ArrayList()
    private var moreLink: String? = null
    private var resultSearchFragment: ResultSearchFragment?  =null
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_search_destination_result
    }

    constructor()

    constructor(resultSearchFragment: ResultSearchFragment?){
        this.resultSearchFragment = resultSearchFragment
    }

    override fun initView() {
    }

    public fun setList(travels: ArrayList<Travel>?, moreLink: String?, count: String, keyword: String,isApproximately: Boolean, isLoadMore : Boolean) {
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
            tvCountResult.text = "C?? $count k???t qu??? t??m ki???m ??i???m ?????n kh???p v???i \"$keyword\""
            resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
        }else{
            tvCountResult.text = "C?? $count k???t qu??? t??m ki???m ??i???m ?????n g???n ????ng kh???p v???i \"$keyword\""
            resultSearchFragment?.setHighLightedText(tvCountResult, "\"$keyword\"")
        }
    }

    public fun clearData(){
        tvCountResult.text = "??ang t??m c??c k???t qu???..."
        this.travels?.clear()
        subTravelNewsAdapter?.notifyDataSetChanged()

    }

    override fun initData() {
        subTravelNewsAdapter = NearByInTravelDetailAdapter(mActivity, travels, false, object : ClickItem {
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

        resultSearchFragment?.searchAll(SearchType.DESTINATION, false)
    }

    override fun inject() {
    }

    override fun setClickListener() {
        rclContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    resultSearchFragment?.searchAllWithLink(moreLink, SearchType.DESTINATION, true)
                }
            }
        })

        btnScrollToTop.setOnClickListener {
            try {
                rclContent.smoothScrollToPosition(0)
            } catch (e: Exception) {
            }
        }
    }

    override fun setObserver() {
    }


}