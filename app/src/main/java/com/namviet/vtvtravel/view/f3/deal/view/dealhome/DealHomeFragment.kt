package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.TabAdapter
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import kotlinx.android.synthetic.main.fragment_deal_home.*
import kotlinx.android.synthetic.main.fragment_page_deal_home.*
import kotlinx.android.synthetic.main.fragment_page_deal_home.vpContent
import java.util.*

class DealHomeFragment : BaseFragment<FragmentDealHomeBinding?>(), Observer {
    private var dealViewModel : DealViewModel? = null
    private var tabAdapter: TabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_home
    }

    override fun initView() {
        tabAdapter = TabAdapter(childFragmentManager, mActivity)
        var dealHomeChildFragment = DealHomeChildFragment()
        tabAdapter?.addFragment(dealHomeChildFragment, "")

        var dealHomeChildFragment1 = DealHomeChildFragment()
        tabAdapter?.addFragment(dealHomeChildFragment1, "")

        vpContent.adapter = tabAdapter
        vpContent.offscreenPageLimit = 10
    }
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        layoutTab1.setOnClickListener {
            vpContent.currentItem = 0
            viewBorder1.visibility = View.VISIBLE
            viewBorder2.visibility = View.GONE
        }

        layoutTab2.setOnClickListener {
            vpContent.currentItem = 1
            viewBorder1.visibility = View.GONE
            viewBorder2.visibility = View.VISIBLE
        }

        btnMenu.setOnClickListener {
            addFragment(DealMenuDialog())
        }

        vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        layoutTab1.performClick()
                    }
                    1 -> {
                        layoutTab2.performClick()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }
    override fun setObserver() {}
    override fun update(p0: Observable?, p1: Any?) {
        TODO("Not yet implemented")
    }
}