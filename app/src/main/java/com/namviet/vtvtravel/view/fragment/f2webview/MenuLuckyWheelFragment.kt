package com.namviet.vtvtravel.view.fragment.f2webview

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentMenuLuckyWheelBinding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.view.f2.HistoryLuckyWheelActivity
import kotlinx.android.synthetic.main.f2_fragment_menu_lucky_wheel.*

class MenuLuckyWheelFragment : BaseFragment<F2FragmentMenuLuckyWheelBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_menu_lucky_wheel
    }

    override fun initView() {}
    override fun initData() {
        val account = MyApplication.getInstance().account
        if (null != account && account.isLogin) {
            if (account.imageProfile != null && account.imageProfile.isNotEmpty()) {
                Glide.with(mActivity).load(account.imageProfile).apply(RequestOptions().circleCrop()).error(R.drawable.f2_defaut_user).into(binding!!.imgAvatar)
            } else {
                binding!!.imgAvatar.setImageResource(R.drawable.f2_defaut_user)
            }
            binding!!.tvName.text = "Chào "+account.fullname

        } else {
        }


    }
    override fun inject() {}
    override fun setClickListener() {
        btnBack.setOnClickListener {
            mActivity.onBackPressed()
        }

        btnRule.setOnClickListener {
            addFragment(RuleLuckyWheelFragment())
        }


        btnPlayRule.setOnClickListener {
            addFragment(PlayRuleLuckyWheelFragment())
        }

        btnListGift.setOnClickListener {
            addFragment(LeaderBoardLuckyWheelFragment())
        }

        btnGift.setOnClickListener {
            HistoryLuckyWheelActivity.startScreen(mActivity)
        }



    }
    override fun setObserver() {}
}