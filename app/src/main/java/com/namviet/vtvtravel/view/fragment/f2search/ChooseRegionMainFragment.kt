package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSearchRegionMainBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.ultils.F2Util
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.f3_fragment_search_region_main.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class ChooseRegionMainFragment : BaseFragment<F3FragmentSearchRegionMainBinding?>(), Observer {
    private var viewModel: SearchBigLocationViewModel? = null
    private var locationsMain: ArrayList<Location>? = null
    private val locations: ArrayList<Location> = ArrayList()
    private var chooseRegion: ChooseRegion? = null
    private var searchAllLocationAdapter: SearchAllLocationAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_search_region_main
    }

    override fun initView() {}
    override fun initData() {
        viewModel = SearchBigLocationViewModel()
        viewModel!!.addObserver(this)
        locations.addAll(locationsMain!!)
        searchAllLocationAdapter = SearchAllLocationAdapter(mActivity, locations, SearchAllLocationAdapter.ClickItem { location ->
//            tvRegion.text = location?.name
//            this.location = location
//            if(edtSearch.text.isNotEmpty()) {
//                mActivity.onBackPressed()
//                searchSuggestionCallback?.onClickRegion(location, keyword)
//            }


        })
        rclLocation.adapter = searchAllLocationAdapter
        handleSearch()
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

    override fun update(o: Observable?, arg: Any?) {

    }


    @SuppressLint("CheckResult")
    private fun handleSearch() {
        RxTextView.afterTextChangeEvents(binding!!.edtSearch)
            .skipInitialValue()
            .debounce(790, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                try {
                    binding!!.rclLocation.visibility = View.VISIBLE
                    locations.clear()
                    for (i in locationsMain!!.indices) {
                        if (F2Util.removeAccent(locationsMain!![i].name.toLowerCase()).contains(
                                F2Util.removeAccent(
                                    binding!!.edtSearch.text.toString().toLowerCase()
                                )
                            )
                        ) {
                            locations.add(locationsMain!![i])
                        }
                    }
                    searchAllLocationAdapter!!.notifyDataSetChanged()
                } catch (e: Exception) {
                }
            }
    }
}