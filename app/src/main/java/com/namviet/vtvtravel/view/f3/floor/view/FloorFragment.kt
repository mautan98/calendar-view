package com.namviet.vtvtravel.view.f3.floor.view

import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.config.Constants
import com.namviet.vtvtravel.databinding.F2FragmentFloorBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.CreateTripActivity
import com.namviet.vtvtravel.view.f2.LoginAndRegisterActivityNew
import com.namviet.vtvtravel.view.f2.WebviewActivity
import com.namviet.vtvtravel.view.f3.booking.view.BookingActivity.Companion.openActivity
import com.namviet.vtvtravel.view.fragment.f2offline.OneButtonTitleImageDialog
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
//            var account = MyApplication.getInstance().getAccount();
//            if (null != account && account.isLogin()) {
//                WebviewActivity.startScreen(context)
//            } else {
//                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//            }

            showComingSoon()
        }

        btnMyBill.setOnClickListener {
//            var account = MyApplication.getInstance().getAccount();
//            if (null != account && account.isLogin()) {
//                WebviewActivity.startScreen(context)
//            } else {
//                LoginAndRegisterActivityNew.startScreen(mActivity, 0, false);
//            }
            showComingSoon()
        }

        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        view1.setOnClickListener {
            showComingSoon()
        }

        view2.setOnClickListener {
            try {
                openActivity(mActivity)
            } catch (e: Exception) {
            }
        }

        view3.setOnClickListener {
            showComingSoon()
        }

        view4.setOnClickListener {
            showComingSoon()
        }

        view5.setOnClickListener {
            try {
                val account = MyApplication.getInstance().account
                if (null != account && account.isLogin) {
                    CreateTripActivity.startScreen(context)
                } else {
                    LoginAndRegisterActivityNew.startScreen(context, 0, false)
                }
            } catch (e: Exception) {
            }
        }

        view6.setOnClickListener {
            showComingSoon()
        }

        view7.setOnClickListener {
            showComingSoon()
        }
        view8.setOnClickListener {
            showComingSoon()
        }

        view9.setOnClickListener {
            showComingSoon()
        }

        view10.setOnClickListener {
            showComingSoon()
        }


    }

    override fun setObserver() {}

    private fun showComingSoon() {
        try {
            var oneButtonTitleImageDialog = OneButtonTitleImageDialog();
            oneButtonTitleImageDialog.show(mActivity.supportFragmentManager, Constants.TAG_DIALOG);
        } catch (e: Exception) {

        }
    }
}