package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.CategorySearchInSlideAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSlideSearchBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.Content
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.model.travelnews.Location
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment
import kotlinx.android.synthetic.main.f3_fragment_drop_down_status.*
import kotlinx.android.synthetic.main.f3_fragment_slide_search.*
import kotlinx.android.synthetic.main.f3_fragment_slide_search.btnApply
import kotlinx.android.synthetic.main.f3_fragment_slide_search.switchStatus

class SlideMenuSearchFragment : BaseFragment<F3FragmentSlideSearchBinding?>() {

    private var categorySearchInSlideAdapter : CategorySearchInSlideAdapter? = null
    private var sortAndFilter : SortAndFilter? = null
    private var listChild : ArrayList<Children>? = null
    private var listener: Listener? = null

    private var listCategory = ArrayList<Children>()
    private var locations = ArrayList<Location>()

    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_slide_search
    }

    override fun initView() {
        tvCity.text = sortAndFilter!!.sortHeader[1].content.cityName
    }
    override fun initData() {
        categorySearchInSlideAdapter = CategorySearchInSlideAdapter(mActivity, sortAndFilter!!.sortHeader[2].children, null)
        binding!!.rclCategory.adapter = categorySearchInSlideAdapter


        if(sortAndFilter!!.sortHeader[3].content.isOpen == null){
            switchStatus.alpha = 0.5f
        }else{
            switchStatus.alpha = 1f
            switchStatus.isChecked = sortAndFilter!!.sortHeader[3].content.isOpen!!
        }


        switchStatus.setOnCheckedChangeListener { _, p1 ->
            switchStatus.isChecked = p1
            sortAndFilter!!.sortHeader[3].content.isOpen = p1
            switchStatus.alpha = 1f
        }

//        btnApply.setOnClickListener {
//            listener?.onApply(isOpen)
//        }
//
//        btnClearFilter.setOnClickListener {
//            isOpen = null
//            switchStatus.alpha = 0.5f
//            listener?.onApply(isOpen)
//        }

    }
    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnCloseFilter.setOnClickListener {
        }

        binding!!.btnChooseCity.setOnClickListener {
            var chooseRegionFragment = ChooseRegionFragment();
            chooseRegionFragment.setData(sortAndFilter!!.sortHeader[1].content, locations, object : ChooseRegionFragment.ChooseRegion{
                override fun clickRegion(content: Content?) {
                    sortAndFilter!!.sortHeader[1].content = content
                    tvCity.text = content?.cityName

                }
            })
            fragmentManager?.beginTransaction()
                ?.add(R.id.chooseRegionFrame, chooseRegionFragment)
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)!!.commit()
        }

        binding!!.btnApply.setOnClickListener{
            listener?.onApply(sortAndFilter)
        }
    }
    override fun setObserver() {}


    public fun setData(sortAndFilter : SortAndFilter?, listener: Listener, locations: ArrayList<Location>){
        this.sortAndFilter = Gson().fromJson(Gson().toJson(sortAndFilter), SortAndFilter::class.java)
        this.listCategory.addAll(sortAndFilter!!.sortHeader[2].children)
        this.listener = listener
        this.locations = locations
    }

    public interface Listener{
        fun onApply(sortAndFilter : SortAndFilter?)
    }

    public fun deleteFragment(){
        fragmentManager?.beginTransaction()
            ?.remove(this)
            ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)?.commit()
    }
}