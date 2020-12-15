package com.namviet.vtvtravel.view.fragment.f2opeator

import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentOperatorBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.MainActivity
import kotlinx.android.synthetic.main.f2_fragment_operator.*

class OperatorFragment : BaseFragment<F2FragmentOperatorBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_operator
    }

    override fun initView() {}
    override fun initData() {
        try {
            val account = MyApplication.getInstance().account
            if (null != account && account.isLogin) {
                if (account.imageProfile != null && account.imageProfile.isNotEmpty()) {
                    Glide.with(mActivity).load(account.imageProfile).apply(RequestOptions().circleCrop()).error(R.drawable.f2_defaut_user).into(imgAvatar)
                }
                tvName.text = account.fullname
                tvPhone.text = account.mobile.toString()
            } else {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun inject() {}
    override fun setClickListener() {
        layoutInfo.setOnClickListener {
            addFragment(OperatorInformationFragment())
        }

        layoutBackToHome.setOnClickListener {
            var intent = Intent(mActivity, MainActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }
    override fun setObserver() {}
}