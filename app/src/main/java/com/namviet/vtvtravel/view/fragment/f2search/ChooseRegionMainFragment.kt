package com.namviet.vtvtravel.view.fragment.f2search

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSearchRegionMainBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Location
import kotlinx.android.synthetic.main.f3_fragment_search_region_main.*

class ChooseRegionMainFragment : BaseFragment<F3FragmentSearchRegionMainBinding?>() {
    private var locationsMain: ArrayList<Location>? = null
    private var chooseRegion: ChooseRegion? = null
    private var searchAllLocationAdapter: SearchAllLocationAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_region_main
    }

    override fun initView() {}
    override fun initData() {
        searchAllLocationAdapter = SearchAllLocationAdapter(mActivity, locationsMain, SearchAllLocationAdapter.ClickItem { location ->
//            tvRegion.text = location?.name
//            this.location = location
//            if(edtSearch.text.isNotEmpty()) {
//                mActivity.onBackPressed()
//                searchSuggestionCallback?.onClickRegion(location, keyword)
//            }


        })
        rclLocation.adapter = searchAllLocationAdapter
    }
    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnClose.setOnClickListener { mActivity.onBackPressed() }
    }

    override fun setObserver() {}

    public fun setData(locationsMain: ArrayList<Location>?, chooseRegion: ChooseRegion?){
        this.locationsMain = locationsMain
        this.chooseRegion = chooseRegion
    }

    interface ChooseRegion{
        fun clickRegion(location: Location?);
    }
}