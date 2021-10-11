package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.graphics.Color
import androidx.viewpager.widget.ViewPager
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentPageDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.deal.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_page_deal_home.*

class PageDealHomeFragment : BaseFragment<FragmentPageDealHomeBinding?>() {
    private var tabAdapter: TabAdapter? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_page_deal_home
    }

    override fun initView() {
        tabAdapter = TabAdapter(childFragmentManager, mActivity)
        var myGiftFragment =
            MyGiftFragment()
        tabAdapter?.addFragment(myGiftFragment, "")

        var dealHomeFragment = DealHomeFragment()
        tabAdapter?.addFragment(dealHomeFragment, "")

        var dealSubcribeFragment =
            DealSubcribeFragment()
        tabAdapter?.addFragment(dealSubcribeFragment, "")

        vpContent.adapter = tabAdapter
        vpContent.offscreenPageLimit = 10
        vpContent.currentItem = 1

    }

    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnMyGift.setOnClickListener {
            vpContent.currentItem = 0
            imgMyGift.setImageResource(R.drawable.ic_my_gift_selected)
            tvMyGift.setTextColor(Color.parseColor("#F92418"))

            imgDealSubscribe.setImageResource(R.drawable.ic_my_subcribe_unselected)
            tvDealSubscribe.setTextColor(Color.parseColor("#707070"))
        }
        btnDealSubcribe.setOnClickListener {
            vpContent.currentItem = 2

            imgMyGift.setImageResource(R.drawable.ic_my_gift_unselected)
            tvMyGift.setTextColor(Color.parseColor("#707070"))

            imgDealSubscribe.setImageResource(R.drawable.ic_my_subcribe_selected)
            tvDealSubscribe.setTextColor(Color.parseColor("#F92418"))
        }
        btnHome.setOnClickListener {
            vpContent.currentItem = 1

            imgMyGift.setImageResource(R.drawable.ic_my_gift_unselected)
            tvMyGift.setTextColor(Color.parseColor("#707070"))

            imgDealSubscribe.setImageResource(R.drawable.ic_my_subcribe_unselected)
            tvDealSubscribe.setTextColor(Color.parseColor("#707070"))
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
                        btnMyGift.performClick()
                    }
                    1 -> {
                        btnHome.performClick()
                    }
                    else -> {
                        btnDealSubcribe.performClick()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun setObserver() {}
}