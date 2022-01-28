package com.namviet.vtvtravel.view.fragment.f2search.resultsearch

import androidx.fragment.app.FragmentTransaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2search.CategorySearchInSlideAdapter
import com.namviet.vtvtravel.databinding.F3FragmentSlideSearchBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2search.Children
import com.namviet.vtvtravel.model.f2search.SortAndFilter
import com.namviet.vtvtravel.view.fragment.f2search.ChooseRegionFragment

class SlideMenuSearchFragment : BaseFragment<F3FragmentSlideSearchBinding?>() {

    private var categorySearchInSlideAdapter : CategorySearchInSlideAdapter? = null
    private var sortAndFilter : SortAndFilter? = null
    private var listChild : ArrayList<Children>? = null
    private var listener: Listener? = null

    private var listCategory = ArrayList<Children>()

    override fun getLayoutRes(): Int {
        return R.layout.f3_fragment_slide_search
    }

    override fun initView() {}
    override fun initData() {
        categorySearchInSlideAdapter = CategorySearchInSlideAdapter(mActivity, sortAndFilter!!.sortHeader[2].children, null)
        binding!!.rclCategory.adapter = categorySearchInSlideAdapter
    }
    override fun inject() {}
    override fun setClickListener() {
        binding!!.btnCloseFilter.setOnClickListener {
        }

        binding!!.btnChooseCity.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.add(R.id.chooseRegionFrame, ChooseRegionFragment())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)?.addToBackStack(null)!!.commit()
        }

        binding!!.btnApply.setOnClickListener{
            listener?.onApply(sortAndFilter)
        }
    }
    override fun setObserver() {}


    public fun setData(sortAndFilter : SortAndFilter?, listener: Listener){
        this.sortAndFilter = Gson().fromJson(Gson().toJson(sortAndFilter), SortAndFilter::class.java)
        this.listCategory.addAll(sortAndFilter!!.sortHeader[2].children)
        this.listener = listener
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