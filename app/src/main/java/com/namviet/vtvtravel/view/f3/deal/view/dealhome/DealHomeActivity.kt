package com.namviet.vtvtravel.view.f3.deal.view.dealhome

import android.content.Context
import android.content.Intent
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
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
    override fun doAfterOnCreate() {
        setStatusBarColorDefault()
    }
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

    public fun setStatusBarColorDefault(){
        try {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.my_statusbar_color)
        } catch (e: Exception) {
        }
    }

    public fun setStatusBarColorMyGift(){
        try {
            val window: Window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        } catch (e: Exception) {
        }
    }
}