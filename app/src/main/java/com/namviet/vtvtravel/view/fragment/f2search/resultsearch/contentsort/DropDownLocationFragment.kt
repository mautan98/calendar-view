package com.namviet.vtvtravel.view.fragment.f2search.resultsearch.contentsort

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.SortParamAdapter
import com.namviet.vtvtravel.databinding.F3FragmentDropDownLocationBinding
import com.namviet.vtvtravel.databinding.F3FragmentSortFollowBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionMainFragment
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.*
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.btnApply
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.btnChooseCity
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.btnClearFilter
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location.tvCity
import kotlinx.android.synthetic.main.f3_fragment_drop_down_location_in_video.*

class DropDownLocationFragment : BaseFragment<F3FragmentDropDownLocationBinding?>() {
    private var locationsMain: ArrayList<Location>? = null
    private var callback : Callback? = null
    private var content : Content? = null
    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_drop_down_location
    }

    override fun initView() {
        tvCity.text = content?.cityName
        tvDistrict.text = content?.districtName
        tvCommune.text = content?.communeName
    }
    override fun initData() {
    }
    override fun inject() {}
    override fun setClickListener() {
        btnChooseCity.setOnClickListener {
            var chooseRegionMainFragment = ChooseRegionMainFragment();
            chooseRegionMainFragment.setData(locationsMain, object : ChooseRegionMainFragment.ChooseRegion{
                override fun clickRegion(location: Location?) {
                    tvCity.text = location?.name
                    content?.cityId = location?.id
                    content?.cityName = location?.name
                }
            })
            addFragment(chooseRegionMainFragment)
        }

        btnApply.setOnClickListener {
            callback?.onApply(content)
        }

        btnClearFilter.setOnClickListener {
            var content = Content()
            callback?.onApply(content)
        }
    }
    override fun setObserver() {}

    public fun setData(content: Content, locationsMain: ArrayList<Location>?, callback: Callback?) {
        this.locationsMain = locationsMain
        this.callback = callback
        this.content = content
    }

    public interface Callback{
        fun onApply(content : Content?)
    }
}