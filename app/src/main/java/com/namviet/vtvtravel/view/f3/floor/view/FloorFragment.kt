package com.namviet.vtvtravel.view.f3.floor.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentFloorBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f2.WebviewActivity
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
             var account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                WebviewActivity.startScreen(context)
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        }

        btnMyBill.setOnClickListener {
            var account = MyApplication.getInstance().getAccount();
            if (null != account && account.isLogin()) {
                WebviewActivity.startScreen(context)
            } else {
                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
            }
        }

        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

    }
    override fun setObserver() {}
}