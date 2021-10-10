package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.content.Context
import android.content.Intent
import com.namviet.vtvtravel.R
import com.namviet.vtvtravel.databinding.ActivityDealHomeBinding
import com.namviet.vtvtravel.f2base.base.BaseActivityNew
import com.namviet.vtvtravel.f2base.base.BaseFragment

class DealHomeActivity : BaseActivityNew<ActivityDealHomeBinding?>() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_deal_home
    }

    override fun getFrame(): Int {
        return R.id.mainFrame
    }

    override fun getDataFromIntent() {}
    override fun doAfterOnCreate() {}
    override fun setClick() {}
    override fun initFragment(): BaseFragment<*> {
        return PageDealHomeFragment()
    }

    public companion object {
        public fun startScreen(context: Context) {
            var intent = Intent(context, DealHomeActivity::class.java)
            context.startActivity(Intent(intent))
        }

    }
}