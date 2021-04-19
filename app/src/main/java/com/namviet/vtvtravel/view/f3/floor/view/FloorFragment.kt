package com.namviet.vtvtravel.view.f3.floor.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F2FragmentFloorBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import kotlinx.android.synthetic.main.f2_fragment_floor.*

class FloorFragment : BaseFragment<F2FragmentFloorBinding>() {
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_floor
    }

    override fun initView() {}
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnMyOrder.setOnClickListener {

        }

        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}
}