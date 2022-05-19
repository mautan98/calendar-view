package com.namviet.vtvtravel.view.fragment.f2webview

import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.app.MyApplication
import com.namviet.vtvtravel.databinding.F2FragmentMenuLuckyWheel2Binding
import com.namviet.vtvtravel.f2base.base.BaseFragment
import com.namviet.vtvtravel.model.f2event.OnUpdateAccount
import com.namviet.vtvtravel.view.f2.UserInformationActivity
import kotlinx.android.synthetic.main.f2_fragment_menu_lucky_wheel_2.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MenuLuckyWheelFragment : BaseFragment<F2FragmentMenuLuckyWheel2Binding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.f2_fragment_menu_lucky_wheel_2
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
            binding!!.tvName.text = "Chào, "+account.fullname

            val cut = account.mobile.substring(3, 8)
            val mobile = account.mobile.replace(cut, "xxxxx")
            binding!!.tvPhone.text = mobile
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
//            HistoryLuckyWheelActivity.startScreen(mActivity)
            addFragment(LuckyWheelHistoriesFragment())
        }

        layoutUser.setOnClickListener {
            UserInformationActivity.openScreen(mActivity)
        }



    }
    override fun setObserver() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onUpdateAccount(onUpdateAccount: OnUpdateAccount?) {
        initData()
    }
}