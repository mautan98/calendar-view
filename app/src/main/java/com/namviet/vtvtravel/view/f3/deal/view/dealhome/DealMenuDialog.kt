package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.FragmentDealMenuBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_deal_menu.*

class DealMenuDialog : BaseFragment<FragmentDealMenuBinding?>() {
    private var click : Click? = null

    public fun setClickListener(click : Click?){
        this.click = click
    }
    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_menu
    }

    override fun initView() {}
    override fun initData() {}
    override fun inject() {}
    override fun setClickListener() {
        btnClose.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnRule.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickRule()
        }

        btnSubscribe.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickSubscribeDeal()
        }

        btnHelpCenter.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickHelpCenter()
        }
        btnGoHome.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickGoHome()
        }

        btnGoDealHome.setOnClickListener {
            mActivity.onBackPressed()
            click?.onClickGoDealHome()
        }

        viewClose.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}

    public interface Click{
        fun onClickRule();
        fun onClickSubscribeDeal()
        fun onClickHelpCenter()
        fun onClickGoDealHome()
        fun onClickGoHome()
    }
}