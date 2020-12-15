package com.namviet.vtvtravel.view.fragment.f2call

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.F2FragmentReceiverCallGuestBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
//import com.namviet.vtvtravel.view.f2.OperatorWebRTCCallActivity
import kotlinx.android.synthetic.main.f2_fragment_receiver_call_guest.*

class ReceiverCallGuestFragment : BaseFragment<F2FragmentReceiverCallGuestBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_receiver_call_guest
    }

    override fun initView() {}
    override fun initData() {
        btnHangup.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnAccept.setOnClickListener {
//            OperatorWebRTCCallActivity.startScreen(mActivity, "", 1, "", false)
            mActivity.finish()
        }
    }
    override fun inject() {}
    override fun setClickListener() {}
    override fun setObserver() {}
}