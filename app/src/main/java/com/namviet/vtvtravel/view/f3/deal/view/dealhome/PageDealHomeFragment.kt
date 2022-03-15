package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.graphics.Color
import android.os.Bundle
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.FragmentPageDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f3.deal.adapter.TabAdapter
import com.namviet.vtvtravel.view.f3.deal.event.BackToDeal
import com.namviet.vtvtravel.view.f3.deal.event.ChangeToCenterTab
import com.namviet.vtvtravel.view.f3.deal.event.FinishDeal
import com.namviet.vtvtravel.view.f3.deal.view.mygift.MyGiftFragment
import kotlinx.android.synthetic.main.fragment_page_deal_home.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PageDealHomeFragment : BaseFragment<FragmentPageDealHomeBinding?>() {
    private var isLogin  = false;
    private var tabAdapter: TabAdapter? = null
    private var dealHomeFragment: DealHomeFragment? = null
    private var dealSubcribeFragment: DealSubcribeFragment? = null
    override fun getLayoutRes(): Int {
        return R.layout.fragment_page_deal_home
    }

    override fun initView() {
        tabAdapter = TabAdapter(childFragmentManager, mActivity)
        var myGiftFragment =
            MyGiftFragment()
        tabAdapter?.addFragment(myGiftFragment, "")

        dealHomeFragment = DealHomeFragment()
        tabAdapter?.addFragment(dealHomeFragment, "")

        dealSubcribeFragment = DealSubcribeFragment()
        dealSubcribeFragment?.setLocation(0)
        tabAdapter?.addFragment(dealSubcribeFragment, "")

        vpContent.adapter = tabAdapter
        vpContent.offscreenPageLimit = 10
        vpContent.currentItem = 1

    }

    override fun initData() {
        var account = MyApplication.getInstance().account;
        isLogin = account.isLogin
    }
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
            val account = MyApplication.getInstance().account
            if (null != account && account.isLogin) {
                vpContent.currentItem = 2

                imgMyGift.setImageResource(R.drawable.ic_my_gift_unselected)
                tvMyGift.setTextColor(Color.parseColor("#707070"))

                imgDealSubscribe.setImageResource(R.drawable.ic_my_subcribe_selected)
                tvDealSubscribe.setTextColor(Color.parseColor("#F92418"))

                if(!isLogin){
                    isLogin = true
                    dealSubcribeFragment?.getData(0)
                }
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false)
            }

        }
        btnHome.setOnClickListener {
            if (vpContent.currentItem == 1) {
                dealHomeFragment?.initView()
                dealHomeFragment?.resetTab()
            } else {
                vpContent.currentItem = 1

                imgMyGift.setImageResource(R.drawable.ic_my_gift_unselected)
                tvMyGift.setTextColor(Color.parseColor("#707070"))

                imgDealSubscribe.setImageResource(R.drawable.ic_my_subcribe_unselected)
                tvDealSubscribe.setTextColor(Color.parseColor("#707070"))
            }
        }

//        vpContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageSelected(position: Int) {
//                when (position) {
//                    0 -> {
//                        btnMyGift.performClick()
//                    }
//                    1 -> {
//                        btnHome.performClick()
//                    }
//                    else -> {
//                        btnDealSubcribe.performClick()
//                    }
//                }
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//        })
    }

    override fun setObserver() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe
    public fun onChangeToCenterTab(changeToCenterTab: ChangeToCenterTab) {
        if(changeToCenterTab.position == 1){
            btnHome.performClick();
        }else if(changeToCenterTab.position == 2){
            btnDealSubcribe.performClick()
        }
    }

    @Subscribe
    public fun onFinish(changeToCenterTab: FinishDeal) {
        mActivity.finish()
    }

    @Subscribe
    public fun onFinish(changeToCenterTab: BackToDeal) {
        vpContent.currentItem = 1
    }
}