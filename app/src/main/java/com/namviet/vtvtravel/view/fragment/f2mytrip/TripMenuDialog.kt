package com.namviet.vtvtravel.view.fragment.f2mytrip

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentTripMenuBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_trip_menu.*

class TripMenuDialog : BaseFragment<FragmentTripMenuBinding?>() {
    private var click : Click? = null

    public fun setClickListener(click : Click?){
        this.click = click
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_trip_menu
    }

    override fun initView() {}
    override fun initData() {
    }
    override fun inject() {}
    override fun setClickListener() {
        btnClose.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnHelp.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickHelpCenter()
        }
        btnGoHome.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickGoHome()
        }

        viewClose.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}

    public interface Click{
        fun onClickHelpCenter()
        fun onClickGoHome()
    }
}