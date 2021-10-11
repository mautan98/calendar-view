package com.namviet.vtvtravel.view.f3.deal.view.listdeal

import android.content.Context
import android.content.Intent
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.ActivityListDealBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment

class ListDealActivity : BaseActivityNew<ActivityListDealBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_list_deal
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {}
    override fun doAfterOnCreate() {}
    override fun setClick() {}
    override fun initFragment(): BaseFragment<*> {
        return ListDealFragment()
    }

    companion object {
        fun startScreen(activity: Context) {
            val intent = Intent(activity, ListDealActivity::class.java)
            activity.startActivity(intent)
        }
    }
}