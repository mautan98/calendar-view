package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.f2errorresponse.ErrorResponse
import com.namviet.vtvtravel.response.f2wheel.WheelAreasResponse
import com.namviet.vtvtravel.response.f2wheel.WheelResultResponse
import com.namviet.vtvtravel.response.f2wheel.WheelRotateResponse
import com.namviet.vtvtravel.view.f3.deal.adapter.TabAdapter
import com.namviet.vtvtravel.view.f3.deal.model.BlockResponse
import com.namviet.vtvtravel.view.f3.deal.viewmodel.DealViewModel
import com.namviet.vtvtravel.view.fragment.f2webview.LuckyWheelDialog
import com.namviet.vtvtravel.viewmodel.f2luckywheel.LuckyWheelViewModel
import kotlinx.android.synthetic.main.f2_fragment_detail_deal_webview.*
import kotlinx.android.synthetic.main.fragment_deal_home.*
import kotlinx.android.synthetic.main.fragment_deal_home.btnMenu
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
        dealViewModel = DealViewModel()
        dealViewModel?.addObserver(this)
        dealViewModel?.getDealBlock()
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
    override fun update(observable: Observable?, o: Any?) {
        if (observable is DealViewModel && null != o) {
            when (o) {
                is BlockResponse -> {
                    val response = o as BlockResponse

                    tabAdapter = TabAdapter(childFragmentManager, mActivity)
                    for (i in 0 until response.data.menu.listMenuCTKM.size){
                        var dealHomeChildFragment = DealHomeChildFragment()
                        dealHomeChildFragment.setData(response.data.menu.listMenuCTKM[i].listChildBlock[0].listChildBlock, response.data.menu.listMenuCTKM[i].listChildBlock[1].listChildBlock)
                        tabAdapter?.addFragment(dealHomeChildFragment, "")
                    }

                    vpContent.adapter = tabAdapter
                    vpContent.offscreenPageLimit = 10
                }
                is ErrorResponse -> {
                    try {
                        val responseError = o as ErrorResponse
                    } catch (e: Exception) {
                    }
                }
            }
        }
    }
}