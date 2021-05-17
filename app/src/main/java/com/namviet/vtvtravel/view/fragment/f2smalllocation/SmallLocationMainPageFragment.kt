package com.namviet.vtvtravel.view.fragment.f2smalllocation

import androidx.core.content.ContextCompat
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.adapter.f2offline.MainAdapter
import com.namviet.vtvtravel.databinding.F2FragmentMainPageSmallLocationBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.newhome.ItemHomeService
import kotlinx.android.synthetic.main.f2_fragment_main_page_small_location.*

class SmallLocationMainPageFragment(private var dataMenu: ArrayList<ItemHomeService<*>.Item>? = null) : BaseFragment<F2FragmentMainPageSmallLocationBinding?>() {
    private var mainAdapter : MainAdapter? = null


    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_main_page_small_location
    }


    override fun initView() {}
    override fun initData() {
        mainAdapter = MainAdapter(childFragmentManager)
        vpContent.offscreenPageLimit = 10

        for(i in 0 until dataMenu!!.size){
            var smallLocationFragment = SmallLocationFragment(dataMenu?.get(i)?.link, dataMenu?.get(i)?.code, "");
            mainAdapter?.addFragment(smallLocationFragment, "")
        }
        vpContent.adapter = mainAdapter
        tabLayout.setTabTextColors(ContextCompat.getColor(mActivity, R.color.md_black_1000), ContextCompat.getColor(mActivity, R.color.f2_color_package))
        tabLayout.setupWithViewPager(vpContent)

        for(i in 0 until dataMenu!!.size){
            tabLayout.getTabAt(i)?.text = dataMenu?.get(i)?.name
        }

    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}