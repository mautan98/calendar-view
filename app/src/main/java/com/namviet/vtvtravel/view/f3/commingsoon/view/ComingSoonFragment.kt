package com.namviet.vtvtravel.view.f3.commingsoon.view

import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentComingSoonBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.tracking.TrackingAnalytic
import com.namviet.vtvtravel.view.f2.DetailDealWebviewActivity
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f3.commingsoon.viewmodel.ComingSoonViewModel
import com.namviet.vtvtravel.view.fragment.f2service.ServiceActivity
import com.namviet.vtvtravel.view.fragment.f2webview.VQMMWebviewFragment
import kotlinx.android.synthetic.main.f2_fragment_coming_soon.*

class ComingSoonFragment(private var type: String? = null,var url : String?) : BaseFragment<F2FragmentComingSoonBinding?>() {

    private var comingSoonViewModel  = ComingSoonViewModel()
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_coming_soon
    }

    override fun initView() {}
    override fun initData() {
        binding?.comingSoonViewModel = comingSoonViewModel
        comingSoonViewModel.setTextForTextView(type)
        setImage()

    }
    override fun inject() {}
    override fun setClickListener() {
        btnRollNow.setOnClickListener {
            try {
                val account = MyApplication.getInstance().account
                if (null != account && account.isLogin) {
                    val vqmmWebviewFragment = VQMMWebviewFragment()
                    addFragment(vqmmWebviewFragment)
                } else {
                    LoginAndRegisterActivityNew.startScreen(context, 0, false)
                }
            } catch (e: Exception) {
            }
        }
        btnHuntDeal.setOnClickListener {
            try {

                DetailDealWebviewActivity.startScreen(context,url)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}

    private fun setImage(){
        try {
            Glide.with(mActivity).load(R.drawable.test_btn_banner).into(imgBanner)
            Glide.with(mActivity).load(R.drawable.test_btn_roll_now).into(btnRollNow)
            Glide.with(mActivity).load(R.drawable.test_btn_hunt_deal).into(btnHuntDeal)
        } catch (e: Exception) {
        }
    }
}