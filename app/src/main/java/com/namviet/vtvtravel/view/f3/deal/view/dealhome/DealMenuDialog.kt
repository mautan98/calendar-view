package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.view.View
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.FragmentDealMenuBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.CreateTripActivity
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import kotlinx.android.synthetic.main.fragment_deal_menu.*

class DealMenuDialog : BaseFragment<FragmentDealMenuBinding?>() {
    private var click : Click? = null
    private var isFullScreen = false

    public fun setClickListener(click : Click?){
        this.click = click
    }

    public fun setFullScreen(isFullScreen : Boolean){
        this.isFullScreen = isFullScreen
    }


    override fun getLayoutRes(): Int {
        return R.layout.fragment_deal_menu
    }

    override fun initView() {}
    override fun initData() {
        if(isFullScreen){
            viewFullScreen.visibility = View.VISIBLE
        }else{
            viewFullScreen.visibility = View.GONE
        }
    }
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
            val account = MyApplication.getInstance().account
            if (null != account && account.isLogin) {
                mActivity.onBackPressed()
                click?.onClickSubscribeDeal()
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false)
            }
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