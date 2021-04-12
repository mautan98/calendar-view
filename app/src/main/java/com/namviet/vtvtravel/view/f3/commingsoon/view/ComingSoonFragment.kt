package com.namviet.vtvtravel.view.f3.commingsoon.view

import com.bumptech.glide.Glide
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F2FragmentComingSoonBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f3.commingsoon.viewmodel.ComingSoonViewModel
import kotlinx.android.synthetic.main.f2_fragment_coming_soon.*

class ComingSoonFragment(private var type: String? = null) : BaseFragment<F2FragmentComingSoonBinding?>() {

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

        }
        btnHuntDeal.setOnClickListener {

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