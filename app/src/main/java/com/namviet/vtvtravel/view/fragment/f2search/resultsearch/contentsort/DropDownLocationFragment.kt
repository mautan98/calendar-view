package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SortParamAdapter
import com.namviet.vtvtravel.databinding.F3FragmentDropDownLocationBinding
import com.namviet.vtvtravel.databinding.F3FragmentSortFollowBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionMainFragment
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.*

class DropDownLocationFragment : BaseFragment<F3FragmentDropDownLocationBinding?>() {
    private var locationsMain: ArrayList<Location>? = null
    private var callback : Callback? = null
    private var city : Location? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_location
    }

    override fun initView() {}
    override fun initData() {
    }
    override fun inject() {}
    override fun setClickListener() {
        btnChooseCity.setOnClickListener {
            var chooseRegionMainFragment = ChooseRegionMainFragment();
            chooseRegionMainFragment.setData(locationsMain, object : ChooseRegionMainFragment.ChooseRegion{
                override fun clickRegion(location: Location?) {
                    tvCity.text = location?.name
                    this@DropDownLocationFragment.city = location
                }
            })
            addFragment(chooseRegionMainFragment)
        }
    }
    override fun setObserver() {}

    public fun setData(locationsMain: ArrayList<Location>?, callback: Callback?) {
        this.locationsMain = locationsMain
        this.callback = callback
    }

    public interface Callback{
        fun onApply()
    }
}