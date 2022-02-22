package com.namviet.vtvtravel.view.fragment.f2search

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentTransaction
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2biglocation.SearchAllLocationAdapter
import com.namviet.vtvtravel.databinding.F3LayoutSearchDestinationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.viewmodel.f2biglocation.SearchBigLocationViewModel
import kotlinx.android.synthetic.main.f3_fragment_search_region_main.*
import kotlinx.android.synthetic.main.f3_layout_search_destination.*

class ChooseRegionFragment : BaseFragment<F3LayoutSearchDestinationBinding?>() {
    private var viewModel: SearchBigLocationViewModel? = null
    private var locationsMain: ArrayList<Location>? = null
    private val locations: ArrayList<Location> = ArrayList()
    private var chooseRegion: ChooseRegion? = null
    private var searchAllLocationAdapter: SearchAllLocationAdapter? = null
    private var mContext : Context?  =null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun getLayoutRes(): Int {
        return R.layout.f3_layout_search_destination
    }

    override fun initView() {}
    override fun initData() {
//        viewModel = SearchBigLocationViewModel()
//        viewModel!!.addObserver(this)
        locations.addAll(locationsMain!!)
        searchAllLocationAdapter = SearchAllLocationAdapter(
            mContext,
            locations,
            SearchAllLocationAdapter.ClickItem { location ->
                mActivity.onBackPressed()
                chooseRegion?.clickRegion(location)


            })
        rclCity.adapter = searchAllLocationAdapter
        handleSearch()
    }
    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnCloseFilter.setOnClickListener {
            assert(fragmentManager != null)
            fragmentManager!!.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .remove(this@ChooseRegionFragment).commit()
        }
    }

    public fun setData(locationsMain: ArrayList<Location>?, chooseRegion: ChooseRegion?) {
        this.locationsMain = locationsMain
        this.chooseRegion = chooseRegion
    }

    public interface ChooseRegion {
        fun clickRegion(location: Location?)
    }

    override fun setObserver() {}

    @SuppressLint("CheckResult")
    private fun handleSearch() {
//        RxTextView.afterTextChangeEvents(binding!!.edtSearch)
//            .skipInitialValue()
//            .debounce(790, TimeUnit.MILLISECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                try {
//                    binding!!.rclLocation.visibility = View.VISIBLE
//                    locations.clear()
//                    for (i in locationsMain!!.indices) {
//                        if (F2Util.removeAccent(locationsMain!![i].name.toLowerCase()).contains(
//                                F2Util.removeAccent(
//                                    binding!!.edtSearch.text.toString().toLowerCase()
//                                )
//                            )
//                        ) {
//                            locations.add(locationsMain!![i])
//                        }
//                    }
//                    searchAllLocationAdapter!!.notifyDataSetChanged()
//                } catch (e: Exception) {
//                }
//            }
    }

    override fun onDestroy() {
        super.onDestroy()
//        try {
//            KeyboardUtils.hideKeyboard(mActivity, binding!!.edtSearch)
//        } catch (e: Exception) {
//        }
    }
}